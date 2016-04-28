package com.luuncher.web.rest;

import com.luuncher.LuuncherApp;
import com.luuncher.domain.LunchGroup;
import com.luuncher.repository.LunchGroupRepository;
import com.luuncher.service.LunchGroupService;
import com.luuncher.web.rest.dto.LunchGroupDTO;
import com.luuncher.web.rest.mapper.LunchGroupMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LunchGroupResource REST controller.
 *
 * @see LunchGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LuuncherApp.class)
@WebAppConfiguration
@IntegrationTest
public class LunchGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LunchGroupRepository lunchGroupRepository;

    @Inject
    private LunchGroupMapper lunchGroupMapper;

    @Inject
    private LunchGroupService lunchGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLunchGroupMockMvc;

    private LunchGroup lunchGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LunchGroupResource lunchGroupResource = new LunchGroupResource();
        ReflectionTestUtils.setField(lunchGroupResource, "lunchGroupService", lunchGroupService);
        ReflectionTestUtils.setField(lunchGroupResource, "lunchGroupMapper", lunchGroupMapper);
        this.restLunchGroupMockMvc = MockMvcBuilders.standaloneSetup(lunchGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lunchGroup = new LunchGroup();
        lunchGroup.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLunchGroup() throws Exception {
        int databaseSizeBeforeCreate = lunchGroupRepository.findAll().size();

        // Create the LunchGroup
        LunchGroupDTO lunchGroupDTO = lunchGroupMapper.lunchGroupToLunchGroupDTO(lunchGroup);

        restLunchGroupMockMvc.perform(post("/api/lunch-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchGroupDTO)))
                .andExpect(status().isCreated());

        // Validate the LunchGroup in the database
        List<LunchGroup> lunchGroups = lunchGroupRepository.findAll();
        assertThat(lunchGroups).hasSize(databaseSizeBeforeCreate + 1);
        LunchGroup testLunchGroup = lunchGroups.get(lunchGroups.size() - 1);
        assertThat(testLunchGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllLunchGroups() throws Exception {
        // Initialize the database
        lunchGroupRepository.saveAndFlush(lunchGroup);

        // Get all the lunchGroups
        restLunchGroupMockMvc.perform(get("/api/lunch-groups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lunchGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLunchGroup() throws Exception {
        // Initialize the database
        lunchGroupRepository.saveAndFlush(lunchGroup);

        // Get the lunchGroup
        restLunchGroupMockMvc.perform(get("/api/lunch-groups/{id}", lunchGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lunchGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLunchGroup() throws Exception {
        // Get the lunchGroup
        restLunchGroupMockMvc.perform(get("/api/lunch-groups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLunchGroup() throws Exception {
        // Initialize the database
        lunchGroupRepository.saveAndFlush(lunchGroup);
        int databaseSizeBeforeUpdate = lunchGroupRepository.findAll().size();

        // Update the lunchGroup
        LunchGroup updatedLunchGroup = new LunchGroup();
        updatedLunchGroup.setId(lunchGroup.getId());
        updatedLunchGroup.setName(UPDATED_NAME);
        LunchGroupDTO lunchGroupDTO = lunchGroupMapper.lunchGroupToLunchGroupDTO(updatedLunchGroup);

        restLunchGroupMockMvc.perform(put("/api/lunch-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchGroupDTO)))
                .andExpect(status().isOk());

        // Validate the LunchGroup in the database
        List<LunchGroup> lunchGroups = lunchGroupRepository.findAll();
        assertThat(lunchGroups).hasSize(databaseSizeBeforeUpdate);
        LunchGroup testLunchGroup = lunchGroups.get(lunchGroups.size() - 1);
        assertThat(testLunchGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLunchGroup() throws Exception {
        // Initialize the database
        lunchGroupRepository.saveAndFlush(lunchGroup);
        int databaseSizeBeforeDelete = lunchGroupRepository.findAll().size();

        // Get the lunchGroup
        restLunchGroupMockMvc.perform(delete("/api/lunch-groups/{id}", lunchGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LunchGroup> lunchGroups = lunchGroupRepository.findAll();
        assertThat(lunchGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
