package com.luuncher.web.rest;

import com.luuncher.LuuncherApp;
import com.luuncher.domain.LunchEvent;
import com.luuncher.repository.LunchEventRepository;
import com.luuncher.service.LunchEventService;
import com.luuncher.web.rest.dto.LunchEventDTO;
import com.luuncher.web.rest.mapper.LunchEventMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LunchEventResource REST controller.
 *
 * @see LunchEventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LuuncherApp.class)
@WebAppConfiguration
@IntegrationTest
public class LunchEventResourceIntTest {


    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private LunchEventRepository lunchEventRepository;

    @Inject
    private LunchEventMapper lunchEventMapper;

    @Inject
    private LunchEventService lunchEventService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLunchEventMockMvc;

    private LunchEvent lunchEvent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LunchEventResource lunchEventResource = new LunchEventResource();
        ReflectionTestUtils.setField(lunchEventResource, "lunchEventService", lunchEventService);
        ReflectionTestUtils.setField(lunchEventResource, "lunchEventMapper", lunchEventMapper);
        this.restLunchEventMockMvc = MockMvcBuilders.standaloneSetup(lunchEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lunchEvent = new LunchEvent();
        lunchEvent.setStartDate(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createLunchEvent() throws Exception {
        int databaseSizeBeforeCreate = lunchEventRepository.findAll().size();

        // Create the LunchEvent
        LunchEventDTO lunchEventDTO = lunchEventMapper.lunchEventToLunchEventDTO(lunchEvent);

        restLunchEventMockMvc.perform(post("/api/lunch-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchEventDTO)))
                .andExpect(status().isCreated());

        // Validate the LunchEvent in the database
        List<LunchEvent> lunchEvents = lunchEventRepository.findAll();
        assertThat(lunchEvents).hasSize(databaseSizeBeforeCreate + 1);
        LunchEvent testLunchEvent = lunchEvents.get(lunchEvents.size() - 1);
        assertThat(testLunchEvent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllLunchEvents() throws Exception {
        // Initialize the database
        lunchEventRepository.saveAndFlush(lunchEvent);

        // Get all the lunchEvents
        restLunchEventMockMvc.perform(get("/api/lunch-events?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lunchEvent.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getLunchEvent() throws Exception {
        // Initialize the database
        lunchEventRepository.saveAndFlush(lunchEvent);

        // Get the lunchEvent
        restLunchEventMockMvc.perform(get("/api/lunch-events/{id}", lunchEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lunchEvent.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLunchEvent() throws Exception {
        // Get the lunchEvent
        restLunchEventMockMvc.perform(get("/api/lunch-events/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLunchEvent() throws Exception {
        // Initialize the database
        lunchEventRepository.saveAndFlush(lunchEvent);
        int databaseSizeBeforeUpdate = lunchEventRepository.findAll().size();

        // Update the lunchEvent
        LunchEvent updatedLunchEvent = new LunchEvent();
        updatedLunchEvent.setId(lunchEvent.getId());
        updatedLunchEvent.setStartDate(UPDATED_START_DATE);
        LunchEventDTO lunchEventDTO = lunchEventMapper.lunchEventToLunchEventDTO(updatedLunchEvent);

        restLunchEventMockMvc.perform(put("/api/lunch-events")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchEventDTO)))
                .andExpect(status().isOk());

        // Validate the LunchEvent in the database
        List<LunchEvent> lunchEvents = lunchEventRepository.findAll();
        assertThat(lunchEvents).hasSize(databaseSizeBeforeUpdate);
        LunchEvent testLunchEvent = lunchEvents.get(lunchEvents.size() - 1);
        assertThat(testLunchEvent.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void deleteLunchEvent() throws Exception {
        // Initialize the database
        lunchEventRepository.saveAndFlush(lunchEvent);
        int databaseSizeBeforeDelete = lunchEventRepository.findAll().size();

        // Get the lunchEvent
        restLunchEventMockMvc.perform(delete("/api/lunch-events/{id}", lunchEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LunchEvent> lunchEvents = lunchEventRepository.findAll();
        assertThat(lunchEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
