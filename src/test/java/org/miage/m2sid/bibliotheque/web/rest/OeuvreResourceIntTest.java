package org.miage.m2sid.bibliotheque.web.rest;

import org.miage.m2sid.bibliotheque.BibliothequeApp;

import org.miage.m2sid.bibliotheque.domain.Oeuvre;
import org.miage.m2sid.bibliotheque.repository.OeuvreRepository;
import org.miage.m2sid.bibliotheque.service.dto.OeuvreDTO;
import org.miage.m2sid.bibliotheque.service.mapper.OeuvreMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OeuvreResource REST controller.
 *
 * @see OeuvreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliothequeApp.class)
public class OeuvreResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAA";
    private static final String UPDATED_TITRE = "BBBBB";

    private static final String DEFAULT_EDITEUR = "AAAAA";
    private static final String UPDATED_EDITEUR = "BBBBB";

    @Inject
    private OeuvreRepository oeuvreRepository;

    @Inject
    private OeuvreMapper oeuvreMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOeuvreMockMvc;

    private Oeuvre oeuvre;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OeuvreResource oeuvreResource = new OeuvreResource();
        ReflectionTestUtils.setField(oeuvreResource, "oeuvreRepository", oeuvreRepository);
        ReflectionTestUtils.setField(oeuvreResource, "oeuvreMapper", oeuvreMapper);
        this.restOeuvreMockMvc = MockMvcBuilders.standaloneSetup(oeuvreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oeuvre createEntity(EntityManager em) {
        Oeuvre oeuvre = new Oeuvre()
                .titre(DEFAULT_TITRE)
                .editeur(DEFAULT_EDITEUR);
        return oeuvre;
    }

    @Before
    public void initTest() {
        oeuvre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOeuvre() throws Exception {
        int databaseSizeBeforeCreate = oeuvreRepository.findAll().size();

        // Create the Oeuvre
        OeuvreDTO oeuvreDTO = oeuvreMapper.oeuvreToOeuvreDTO(oeuvre);

        restOeuvreMockMvc.perform(post("/api/oeuvres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oeuvreDTO)))
                .andExpect(status().isCreated());

        // Validate the Oeuvre in the database
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        assertThat(oeuvres).hasSize(databaseSizeBeforeCreate + 1);
        Oeuvre testOeuvre = oeuvres.get(oeuvres.size() - 1);
        assertThat(testOeuvre.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testOeuvre.getEditeur()).isEqualTo(DEFAULT_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllOeuvres() throws Exception {
        // Initialize the database
        oeuvreRepository.saveAndFlush(oeuvre);

        // Get all the oeuvres
        restOeuvreMockMvc.perform(get("/api/oeuvres?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oeuvre.getId().intValue())))
                .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
                .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR.toString())));
    }

    @Test
    @Transactional
    public void getOeuvre() throws Exception {
        // Initialize the database
        oeuvreRepository.saveAndFlush(oeuvre);

        // Get the oeuvre
        restOeuvreMockMvc.perform(get("/api/oeuvres/{id}", oeuvre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oeuvre.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.editeur").value(DEFAULT_EDITEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOeuvre() throws Exception {
        // Get the oeuvre
        restOeuvreMockMvc.perform(get("/api/oeuvres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOeuvre() throws Exception {
        // Initialize the database
        oeuvreRepository.saveAndFlush(oeuvre);
        int databaseSizeBeforeUpdate = oeuvreRepository.findAll().size();

        // Update the oeuvre
        Oeuvre updatedOeuvre = oeuvreRepository.findOne(oeuvre.getId());
        updatedOeuvre
                .titre(UPDATED_TITRE)
                .editeur(UPDATED_EDITEUR);
        OeuvreDTO oeuvreDTO = oeuvreMapper.oeuvreToOeuvreDTO(updatedOeuvre);

        restOeuvreMockMvc.perform(put("/api/oeuvres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oeuvreDTO)))
                .andExpect(status().isOk());

        // Validate the Oeuvre in the database
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        assertThat(oeuvres).hasSize(databaseSizeBeforeUpdate);
        Oeuvre testOeuvre = oeuvres.get(oeuvres.size() - 1);
        assertThat(testOeuvre.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testOeuvre.getEditeur()).isEqualTo(UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void deleteOeuvre() throws Exception {
        // Initialize the database
        oeuvreRepository.saveAndFlush(oeuvre);
        int databaseSizeBeforeDelete = oeuvreRepository.findAll().size();

        // Get the oeuvre
        restOeuvreMockMvc.perform(delete("/api/oeuvres/{id}", oeuvre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        assertThat(oeuvres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
