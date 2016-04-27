package com.luuncher.web.rest;

import com.luuncher.LuuncherApp;
import com.luuncher.domain.LunchLocation;
import com.luuncher.repository.LunchLocationRepository;
import com.luuncher.service.LunchLocationService;
import com.luuncher.web.rest.dto.LunchLocationDTO;
import com.luuncher.web.rest.mapper.LunchLocationMapper;

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
 * Test class for the LunchLocationResource REST controller.
 *
 * @see LunchLocationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LuuncherApp.class)
@WebAppConfiguration
@IntegrationTest
public class LunchLocationResourceIntTest {


    private static final Long DEFAULT_LUNCH_LOCATION_ID = 1L;
    private static final Long UPDATED_LUNCH_LOCATION_ID = 2L;
    private static final String DEFAULT_LUNCH_LOCATION_NAME = "AAAAA";
    private static final String UPDATED_LUNCH_LOCATION_NAME = "BBBBB";
    private static final String DEFAULT_STREET_ADDRESS = "AAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBB";
    private static final String DEFAULT_POSTAL_CODE = "AAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    @Inject
    private LunchLocationRepository lunchLocationRepository;

    @Inject
    private LunchLocationMapper lunchLocationMapper;

    @Inject
    private LunchLocationService lunchLocationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLunchLocationMockMvc;

    private LunchLocation lunchLocation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LunchLocationResource lunchLocationResource = new LunchLocationResource();
        ReflectionTestUtils.setField(lunchLocationResource, "lunchLocationService", lunchLocationService);
        ReflectionTestUtils.setField(lunchLocationResource, "lunchLocationMapper", lunchLocationMapper);
        this.restLunchLocationMockMvc = MockMvcBuilders.standaloneSetup(lunchLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lunchLocation = new LunchLocation();
        lunchLocation.setLunchLocationId(DEFAULT_LUNCH_LOCATION_ID);
        lunchLocation.setLunchLocationName(DEFAULT_LUNCH_LOCATION_NAME);
        lunchLocation.setStreetAddress(DEFAULT_STREET_ADDRESS);
        lunchLocation.setPostalCode(DEFAULT_POSTAL_CODE);
        lunchLocation.setCity(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createLunchLocation() throws Exception {
        int databaseSizeBeforeCreate = lunchLocationRepository.findAll().size();

        // Create the LunchLocation
        LunchLocationDTO lunchLocationDTO = lunchLocationMapper.lunchLocationToLunchLocationDTO(lunchLocation);

        restLunchLocationMockMvc.perform(post("/api/lunch-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchLocationDTO)))
                .andExpect(status().isCreated());

        // Validate the LunchLocation in the database
        List<LunchLocation> lunchLocations = lunchLocationRepository.findAll();
        assertThat(lunchLocations).hasSize(databaseSizeBeforeCreate + 1);
        LunchLocation testLunchLocation = lunchLocations.get(lunchLocations.size() - 1);
        assertThat(testLunchLocation.getLunchLocationId()).isEqualTo(DEFAULT_LUNCH_LOCATION_ID);
        assertThat(testLunchLocation.getLunchLocationName()).isEqualTo(DEFAULT_LUNCH_LOCATION_NAME);
        assertThat(testLunchLocation.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testLunchLocation.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testLunchLocation.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void getAllLunchLocations() throws Exception {
        // Initialize the database
        lunchLocationRepository.saveAndFlush(lunchLocation);

        // Get all the lunchLocations
        restLunchLocationMockMvc.perform(get("/api/lunch-locations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lunchLocation.getId().intValue())))
                .andExpect(jsonPath("$.[*].lunchLocationId").value(hasItem(DEFAULT_LUNCH_LOCATION_ID.intValue())))
                .andExpect(jsonPath("$.[*].lunchLocationName").value(hasItem(DEFAULT_LUNCH_LOCATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }

    @Test
    @Transactional
    public void getLunchLocation() throws Exception {
        // Initialize the database
        lunchLocationRepository.saveAndFlush(lunchLocation);

        // Get the lunchLocation
        restLunchLocationMockMvc.perform(get("/api/lunch-locations/{id}", lunchLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lunchLocation.getId().intValue()))
            .andExpect(jsonPath("$.lunchLocationId").value(DEFAULT_LUNCH_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.lunchLocationName").value(DEFAULT_LUNCH_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLunchLocation() throws Exception {
        // Get the lunchLocation
        restLunchLocationMockMvc.perform(get("/api/lunch-locations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLunchLocation() throws Exception {
        // Initialize the database
        lunchLocationRepository.saveAndFlush(lunchLocation);
        int databaseSizeBeforeUpdate = lunchLocationRepository.findAll().size();

        // Update the lunchLocation
        LunchLocation updatedLunchLocation = new LunchLocation();
        updatedLunchLocation.setId(lunchLocation.getId());
        updatedLunchLocation.setLunchLocationId(UPDATED_LUNCH_LOCATION_ID);
        updatedLunchLocation.setLunchLocationName(UPDATED_LUNCH_LOCATION_NAME);
        updatedLunchLocation.setStreetAddress(UPDATED_STREET_ADDRESS);
        updatedLunchLocation.setPostalCode(UPDATED_POSTAL_CODE);
        updatedLunchLocation.setCity(UPDATED_CITY);
        LunchLocationDTO lunchLocationDTO = lunchLocationMapper.lunchLocationToLunchLocationDTO(updatedLunchLocation);

        restLunchLocationMockMvc.perform(put("/api/lunch-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lunchLocationDTO)))
                .andExpect(status().isOk());

        // Validate the LunchLocation in the database
        List<LunchLocation> lunchLocations = lunchLocationRepository.findAll();
        assertThat(lunchLocations).hasSize(databaseSizeBeforeUpdate);
        LunchLocation testLunchLocation = lunchLocations.get(lunchLocations.size() - 1);
        assertThat(testLunchLocation.getLunchLocationId()).isEqualTo(UPDATED_LUNCH_LOCATION_ID);
        assertThat(testLunchLocation.getLunchLocationName()).isEqualTo(UPDATED_LUNCH_LOCATION_NAME);
        assertThat(testLunchLocation.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testLunchLocation.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testLunchLocation.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void deleteLunchLocation() throws Exception {
        // Initialize the database
        lunchLocationRepository.saveAndFlush(lunchLocation);
        int databaseSizeBeforeDelete = lunchLocationRepository.findAll().size();

        // Get the lunchLocation
        restLunchLocationMockMvc.perform(delete("/api/lunch-locations/{id}", lunchLocation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LunchLocation> lunchLocations = lunchLocationRepository.findAll();
        assertThat(lunchLocations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
