package org.miage.m2sid.bibliotheque.web.rest;

import org.miage.m2sid.bibliotheque.BibliothequeApp;

import org.miage.m2sid.bibliotheque.domain.Livre;
import org.miage.m2sid.bibliotheque.repository.LivreRepository;
import org.miage.m2sid.bibliotheque.service.dto.LivreDTO;
import org.miage.m2sid.bibliotheque.service.mapper.LivreMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LivreResource REST controller.
 *
 * @see LivreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliothequeApp.class)
public class LivreResourceIntTest {

    private static final LocalDate DEFAULT_DATE_EDITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EDITION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RESUME = "AAAAA";
    private static final String UPDATED_RESUME = "BBBBB";

    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    @Inject
    private LivreRepository livreRepository;

    @Inject
    private LivreMapper livreMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLivreMockMvc;

    private Livre livre;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LivreResource livreResource = new LivreResource();
        ReflectionTestUtils.setField(livreResource, "livreRepository", livreRepository);
        ReflectionTestUtils.setField(livreResource, "livreMapper", livreMapper);
        this.restLivreMockMvc = MockMvcBuilders.standaloneSetup(livreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livre createEntity(EntityManager em) {
        Livre livre = new Livre()
                .dateEdition(DEFAULT_DATE_EDITION)
                .resume(DEFAULT_RESUME)
                .url(DEFAULT_URL);
        return livre;
    }

    @Before
    public void initTest() {
        livre = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivre() throws Exception {
        int databaseSizeBeforeCreate = livreRepository.findAll().size();

        // Create the Livre
        LivreDTO livreDTO = livreMapper.livreToLivreDTO(livre);

        restLivreMockMvc.perform(post("/api/livres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
                .andExpect(status().isCreated());

        // Validate the Livre in the database
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeCreate + 1);
        Livre testLivre = livres.get(livres.size() - 1);
        assertThat(testLivre.getDateEdition()).isEqualTo(DEFAULT_DATE_EDITION);
        assertThat(testLivre.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testLivre.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void getAllLivres() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livres
        restLivreMockMvc.perform(get("/api/livres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(livre.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateEdition").value(hasItem(DEFAULT_DATE_EDITION.toString())))
                .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", livre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livre.getId().intValue()))
            .andExpect(jsonPath("$.dateEdition").value(DEFAULT_DATE_EDITION.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLivre() throws Exception {
        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);
        int databaseSizeBeforeUpdate = livreRepository.findAll().size();

        // Update the livre
        Livre updatedLivre = livreRepository.findOne(livre.getId());
        updatedLivre
                .dateEdition(UPDATED_DATE_EDITION)
                .resume(UPDATED_RESUME)
                .url(UPDATED_URL);
        LivreDTO livreDTO = livreMapper.livreToLivreDTO(updatedLivre);

        restLivreMockMvc.perform(put("/api/livres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDTO)))
                .andExpect(status().isOk());

        // Validate the Livre in the database
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeUpdate);
        Livre testLivre = livres.get(livres.size() - 1);
        assertThat(testLivre.getDateEdition()).isEqualTo(UPDATED_DATE_EDITION);
        assertThat(testLivre.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testLivre.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void deleteLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);
        int databaseSizeBeforeDelete = livreRepository.findAll().size();

        // Get the livre
        restLivreMockMvc.perform(delete("/api/livres/{id}", livre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
