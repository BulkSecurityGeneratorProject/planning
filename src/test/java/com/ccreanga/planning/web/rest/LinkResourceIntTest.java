package com.ccreanga.planning.web.rest;

import com.ccreanga.planning.PlanningApplication;
import com.ccreanga.planning.domain.Link;
import com.ccreanga.planning.repository.LinkRepository;
import com.ccreanga.planning.service.LinkService;

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
 * Test class for the LinkResource REST controller.
 *
 * @see LinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlanningApplication.class)
@WebAppConfiguration
@IntegrationTest
public class LinkResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";
    private static final String DEFAULT_LINK = "AAAAA";
    private static final String UPDATED_LINK = "BBBBB";

    @Inject
    private LinkRepository linkRepository;

    @Inject
    private LinkService linkService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLinkMockMvc;

    private Link link;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LinkResource linkResource = new LinkResource();
        ReflectionTestUtils.setField(linkResource, "linkService", linkService);
        this.restLinkMockMvc = MockMvcBuilders.standaloneSetup(linkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        link = new Link();
        link.setText(DEFAULT_TEXT);
        link.setLink(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void createLink() throws Exception {
        int databaseSizeBeforeCreate = linkRepository.findAll().size();

        // Create the Link

        restLinkMockMvc.perform(post("/api/links")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(link)))
                .andExpect(status().isCreated());

        // Validate the Link in the database
        List<Link> links = linkRepository.findAll();
        assertThat(links).hasSize(databaseSizeBeforeCreate + 1);
        Link testLink = links.get(links.size() - 1);
        assertThat(testLink.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testLink.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setText(null);

        // Create the Link, which fails.

        restLinkMockMvc.perform(post("/api/links")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(link)))
                .andExpect(status().isBadRequest());

        List<Link> links = linkRepository.findAll();
        assertThat(links).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setLink(null);

        // Create the Link, which fails.

        restLinkMockMvc.perform(post("/api/links")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(link)))
                .andExpect(status().isBadRequest());

        List<Link> links = linkRepository.findAll();
        assertThat(links).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLinks() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get all the links
        restLinkMockMvc.perform(get("/api/links?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(link.getId().intValue())))
                .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
                .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())));
    }

    @Test
    @Transactional
    public void getLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get the link
        restLinkMockMvc.perform(get("/api/links/{id}", link.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(link.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLink() throws Exception {
        // Get the link
        restLinkMockMvc.perform(get("/api/links/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

		int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link
        link.setText(UPDATED_TEXT);
        link.setLink(UPDATED_LINK);

        restLinkMockMvc.perform(put("/api/links")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(link)))
                .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> links = linkRepository.findAll();
        assertThat(links).hasSize(databaseSizeBeforeUpdate);
        Link testLink = links.get(links.size() - 1);
        assertThat(testLink.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testLink.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    @Transactional
    public void deleteLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

		int databaseSizeBeforeDelete = linkRepository.findAll().size();

        // Get the link
        restLinkMockMvc.perform(delete("/api/links/{id}", link.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Link> links = linkRepository.findAll();
        assertThat(links).hasSize(databaseSizeBeforeDelete - 1);
    }
}
