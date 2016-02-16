package com.ccreanga.planning.web.rest;

import com.ccreanga.planning.PlanningApplication;
import com.ccreanga.planning.domain.Tc;
import com.ccreanga.planning.repository.TcRepository;
import com.ccreanga.planning.service.TcService;

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

import com.ccreanga.planning.domain.enumeration.TcStatus;

/**
 * Test class for the TcResource REST controller.
 *
 * @see TcResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlanningApplication.class)
@WebAppConfiguration
@IntegrationTest
public class TcResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_PLANNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final TcStatus DEFAULT_STATUS = TcStatus.PLANNED;
    private static final TcStatus UPDATED_STATUS = TcStatus.STARTED;
    private static final String DEFAULT_DUMMY = "AAAAA";
    private static final String UPDATED_DUMMY = "BBBBB";

    @Inject
    private TcRepository tcRepository;

    @Inject
    private TcService tcService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTcMockMvc;

    private Tc tc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TcResource tcResource = new TcResource();
        ReflectionTestUtils.setField(tcResource, "tcService", tcService);
        this.restTcMockMvc = MockMvcBuilders.standaloneSetup(tcResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tc = new Tc();
        tc.setName(DEFAULT_NAME);
        tc.setPlannedDate(DEFAULT_PLANNED_DATE);
        tc.setStartDate(DEFAULT_START_DATE);
        tc.setEndDate(DEFAULT_END_DATE);
        tc.setStatus(DEFAULT_STATUS);
        tc.setDummy(DEFAULT_DUMMY);
    }

    @Test
    @Transactional
    public void createTc() throws Exception {
        int databaseSizeBeforeCreate = tcRepository.findAll().size();

        // Create the Tc

        restTcMockMvc.perform(post("/api/tcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tc)))
                .andExpect(status().isCreated());

        // Validate the Tc in the database
        List<Tc> tcs = tcRepository.findAll();
        assertThat(tcs).hasSize(databaseSizeBeforeCreate + 1);
        Tc testTc = tcs.get(tcs.size() - 1);
        assertThat(testTc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTc.getPlannedDate()).isEqualTo(DEFAULT_PLANNED_DATE);
        assertThat(testTc.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTc.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTc.getDummy()).isEqualTo(DEFAULT_DUMMY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcRepository.findAll().size();
        // set the field null
        tc.setName(null);

        // Create the Tc, which fails.

        restTcMockMvc.perform(post("/api/tcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tc)))
                .andExpect(status().isBadRequest());

        List<Tc> tcs = tcRepository.findAll();
        assertThat(tcs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcs() throws Exception {
        // Initialize the database
        tcRepository.saveAndFlush(tc);

        // Get all the tcs
        restTcMockMvc.perform(get("/api/tcs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].plannedDate").value(hasItem(DEFAULT_PLANNED_DATE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].dummy").value(hasItem(DEFAULT_DUMMY.toString())));
    }

    @Test
    @Transactional
    public void getTc() throws Exception {
        // Initialize the database
        tcRepository.saveAndFlush(tc);

        // Get the tc
        restTcMockMvc.perform(get("/api/tcs/{id}", tc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.plannedDate").value(DEFAULT_PLANNED_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dummy").value(DEFAULT_DUMMY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTc() throws Exception {
        // Get the tc
        restTcMockMvc.perform(get("/api/tcs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTc() throws Exception {
        // Initialize the database
        tcRepository.saveAndFlush(tc);

		int databaseSizeBeforeUpdate = tcRepository.findAll().size();

        // Update the tc
        tc.setName(UPDATED_NAME);
        tc.setPlannedDate(UPDATED_PLANNED_DATE);
        tc.setStartDate(UPDATED_START_DATE);
        tc.setEndDate(UPDATED_END_DATE);
        tc.setStatus(UPDATED_STATUS);
        tc.setDummy(UPDATED_DUMMY);

        restTcMockMvc.perform(put("/api/tcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tc)))
                .andExpect(status().isOk());

        // Validate the Tc in the database
        List<Tc> tcs = tcRepository.findAll();
        assertThat(tcs).hasSize(databaseSizeBeforeUpdate);
        Tc testTc = tcs.get(tcs.size() - 1);
        assertThat(testTc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTc.getPlannedDate()).isEqualTo(UPDATED_PLANNED_DATE);
        assertThat(testTc.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTc.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTc.getDummy()).isEqualTo(UPDATED_DUMMY);
    }

    @Test
    @Transactional
    public void deleteTc() throws Exception {
        // Initialize the database
        tcRepository.saveAndFlush(tc);

		int databaseSizeBeforeDelete = tcRepository.findAll().size();

        // Get the tc
        restTcMockMvc.perform(delete("/api/tcs/{id}", tc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tc> tcs = tcRepository.findAll();
        assertThat(tcs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
