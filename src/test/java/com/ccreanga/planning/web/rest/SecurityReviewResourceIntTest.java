package com.ccreanga.planning.web.rest;

import com.ccreanga.planning.PlanningApplication;
import com.ccreanga.planning.domain.SecurityReview;
import com.ccreanga.planning.repository.SecurityReviewRepository;
import com.ccreanga.planning.service.SecurityReviewService;

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
 * Test class for the SecurityReviewResource REST controller.
 *
 * @see SecurityReviewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlanningApplication.class)
@WebAppConfiguration
@IntegrationTest
public class SecurityReviewResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SecurityReviewRepository securityReviewRepository;

    @Inject
    private SecurityReviewService securityReviewService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSecurityReviewMockMvc;

    private SecurityReview securityReview;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecurityReviewResource securityReviewResource = new SecurityReviewResource();
        ReflectionTestUtils.setField(securityReviewResource, "securityReviewService", securityReviewService);
        this.restSecurityReviewMockMvc = MockMvcBuilders.standaloneSetup(securityReviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        securityReview = new SecurityReview();
        securityReview.setName(DEFAULT_NAME);
        securityReview.setReviewDate(DEFAULT_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void createSecurityReview() throws Exception {
        int databaseSizeBeforeCreate = securityReviewRepository.findAll().size();

        // Create the SecurityReview

        restSecurityReviewMockMvc.perform(post("/api/securityReviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(securityReview)))
                .andExpect(status().isCreated());

        // Validate the SecurityReview in the database
        List<SecurityReview> securityReviews = securityReviewRepository.findAll();
        assertThat(securityReviews).hasSize(databaseSizeBeforeCreate + 1);
        SecurityReview testSecurityReview = securityReviews.get(securityReviews.size() - 1);
        assertThat(testSecurityReview.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSecurityReview.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = securityReviewRepository.findAll().size();
        // set the field null
        securityReview.setName(null);

        // Create the SecurityReview, which fails.

        restSecurityReviewMockMvc.perform(post("/api/securityReviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(securityReview)))
                .andExpect(status().isBadRequest());

        List<SecurityReview> securityReviews = securityReviewRepository.findAll();
        assertThat(securityReviews).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurityReviews() throws Exception {
        // Initialize the database
        securityReviewRepository.saveAndFlush(securityReview);

        // Get all the securityReviews
        restSecurityReviewMockMvc.perform(get("/api/securityReviews?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(securityReview.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSecurityReview() throws Exception {
        // Initialize the database
        securityReviewRepository.saveAndFlush(securityReview);

        // Get the securityReview
        restSecurityReviewMockMvc.perform(get("/api/securityReviews/{id}", securityReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(securityReview.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecurityReview() throws Exception {
        // Get the securityReview
        restSecurityReviewMockMvc.perform(get("/api/securityReviews/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurityReview() throws Exception {
        // Initialize the database
        securityReviewRepository.saveAndFlush(securityReview);

		int databaseSizeBeforeUpdate = securityReviewRepository.findAll().size();

        // Update the securityReview
        securityReview.setName(UPDATED_NAME);
        securityReview.setReviewDate(UPDATED_REVIEW_DATE);

        restSecurityReviewMockMvc.perform(put("/api/securityReviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(securityReview)))
                .andExpect(status().isOk());

        // Validate the SecurityReview in the database
        List<SecurityReview> securityReviews = securityReviewRepository.findAll();
        assertThat(securityReviews).hasSize(databaseSizeBeforeUpdate);
        SecurityReview testSecurityReview = securityReviews.get(securityReviews.size() - 1);
        assertThat(testSecurityReview.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSecurityReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void deleteSecurityReview() throws Exception {
        // Initialize the database
        securityReviewRepository.saveAndFlush(securityReview);

		int databaseSizeBeforeDelete = securityReviewRepository.findAll().size();

        // Get the securityReview
        restSecurityReviewMockMvc.perform(delete("/api/securityReviews/{id}", securityReview.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SecurityReview> securityReviews = securityReviewRepository.findAll();
        assertThat(securityReviews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
