package com.luuncher.web.rest;

import com.luuncher.LuuncherApp;
import com.luuncher.domain.Lunch;
import com.luuncher.repository.LunchRepository;
import com.luuncher.service.LunchService;
import com.luuncher.web.rest.dto.LunchDTO;
import com.luuncher.web.rest.mapper.LunchMapper;

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
 * Test class for the LunchResource REST controller.
 *
 * @see LunchResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LuuncherApp.class)
@WebAppConfiguration
@IntegrationTest
public class LunchResourceIntTest {


    private static final Long DEFAULT_LUNCH_ID = 1L;
    private static final Long UPDATED_LUNCH_ID = 2L;

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private LunchRepository lunchRepository;

    @Inject
    private LunchMapper lunchMapper;

    @Inject
    private LunchService lunchService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLunchMockMvc;

    private Lunch lunch;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LunchResource lunchResource = new LunchResource();
        ReflectionTestUtils.setField(lunchResource, "lunchService", lunchService);
        ReflectionTestUtils.setField(lunchResource, "lunchMapper", lunchMapper);
        this.restLunchMockMvc = MockMvcBuilders.standaloneSetup(lunchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lunch = new Lunch();
        lunch.setLunchId(DEFAULT_LUNCH_ID);
        lunch.setStart(DEFAULT_START);
        lunch.setEnd(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createLunch() throws Exception {
        int databaseSizeBeforeCreate = lunchRepository.findAll().size();

        // Create the Lunch
        LunchDTO lunchDTO = lunchMapper.lunchToLunchDTO(lunch);

        restLunchMockMvc.perform(post("/api/lunches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchDTO)))
                .andExpect(status().isCreated());

        // Validate the Lunch in the database
        List<Lunch> lunches = lunchRepository.findAll();
        assertThat(lunches).hasSize(databaseSizeBeforeCreate + 1);
        Lunch testLunch = lunches.get(lunches.size() - 1);
        assertThat(testLunch.getLunchId()).isEqualTo(DEFAULT_LUNCH_ID);
        assertThat(testLunch.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testLunch.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void getAllLunches() throws Exception {
        // Initialize the database
        lunchRepository.saveAndFlush(lunch);

        // Get all the lunches
        restLunchMockMvc.perform(get("/api/lunches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lunch.getId().intValue())))
                .andExpect(jsonPath("$.[*].lunchId").value(hasItem(DEFAULT_LUNCH_ID.intValue())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
                .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    public void getLunch() throws Exception {
        // Initialize the database
        lunchRepository.saveAndFlush(lunch);

        // Get the lunch
        restLunchMockMvc.perform(get("/api/lunches/{id}", lunch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lunch.getId().intValue()))
            .andExpect(jsonPath("$.lunchId").value(DEFAULT_LUNCH_ID.intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLunch() throws Exception {
        // Get the lunch
        restLunchMockMvc.perform(get("/api/lunches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLunch() throws Exception {
        // Initialize the database
        lunchRepository.saveAndFlush(lunch);
        int databaseSizeBeforeUpdate = lunchRepository.findAll().size();

        // Update the lunch
        Lunch updatedLunch = new Lunch();
        updatedLunch.setId(lunch.getId());
        updatedLunch.setLunchId(UPDATED_LUNCH_ID);
        updatedLunch.setStart(UPDATED_START);
        updatedLunch.setEnd(UPDATED_END);
        LunchDTO lunchDTO = lunchMapper.lunchToLunchDTO(updatedLunch);

        restLunchMockMvc.perform(put("/api/lunches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchDTO)))
                .andExpect(status().isOk());

        // Validate the Lunch in the database
        List<Lunch> lunches = lunchRepository.findAll();
        assertThat(lunches).hasSize(databaseSizeBeforeUpdate);
        Lunch testLunch = lunches.get(lunches.size() - 1);
        assertThat(testLunch.getLunchId()).isEqualTo(UPDATED_LUNCH_ID);
        assertThat(testLunch.getStart()).isEqualTo(UPDATED_START);
        assertThat(testLunch.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void deleteLunch() throws Exception {
        // Initialize the database
        lunchRepository.saveAndFlush(lunch);
        int databaseSizeBeforeDelete = lunchRepository.findAll().size();

        // Get the lunch
        restLunchMockMvc.perform(delete("/api/lunches/{id}", lunch.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lunch> lunches = lunchRepository.findAll();
        assertThat(lunches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
