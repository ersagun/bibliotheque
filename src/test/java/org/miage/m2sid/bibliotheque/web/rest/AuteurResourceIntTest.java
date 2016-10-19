package org.miage.m2sid.bibliotheque.web.rest;

import org.miage.m2sid.bibliotheque.BibliothequeApp;

import org.miage.m2sid.bibliotheque.domain.Auteur;
import org.miage.m2sid.bibliotheque.repository.AuteurRepository;
import org.miage.m2sid.bibliotheque.service.dto.AuteurDTO;
import org.miage.m2sid.bibliotheque.service.mapper.AuteurMapper;

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
 * Test class for the AuteurResource REST controller.
 *
 * @see AuteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliothequeApp.class)
public class AuteurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    @Inject
    private AuteurRepository auteurRepository;

    @Inject
    private AuteurMapper auteurMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAuteurMockMvc;

    private Auteur auteur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuteurResource auteurResource = new AuteurResource();
        ReflectionTestUtils.setField(auteurResource, "auteurRepository", auteurRepository);
        ReflectionTestUtils.setField(auteurResource, "auteurMapper", auteurMapper);
        this.restAuteurMockMvc = MockMvcBuilders.standaloneSetup(auteurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auteur createEntity(EntityManager em) {
        Auteur auteur = new Auteur()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM);
        return auteur;
    }

    @Before
    public void initTest() {
        auteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuteur() throws Exception {
        int databaseSizeBeforeCreate = auteurRepository.findAll().size();

        // Create the Auteur
        AuteurDTO auteurDTO = auteurMapper.auteurToAuteurDTO(auteur);

        restAuteurMockMvc.perform(post("/api/auteurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auteurDTO)))
                .andExpect(status().isCreated());

        // Validate the Auteur in the database
        List<Auteur> auteurs = auteurRepository.findAll();
        assertThat(auteurs).hasSize(databaseSizeBeforeCreate + 1);
        Auteur testAuteur = auteurs.get(auteurs.size() - 1);
        assertThat(testAuteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAuteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void getAllAuteurs() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        // Get all the auteurs
        restAuteurMockMvc.perform(get("/api/auteurs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(auteur.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())));
    }

    @Test
    @Transactional
    public void getAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);

        // Get the auteur
        restAuteurMockMvc.perform(get("/api/auteurs/{id}", auteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auteur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuteur() throws Exception {
        // Get the auteur
        restAuteurMockMvc.perform(get("/api/auteurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);
        int databaseSizeBeforeUpdate = auteurRepository.findAll().size();

        // Update the auteur
        Auteur updatedAuteur = auteurRepository.findOne(auteur.getId());
        updatedAuteur
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM);
        AuteurDTO auteurDTO = auteurMapper.auteurToAuteurDTO(updatedAuteur);

        restAuteurMockMvc.perform(put("/api/auteurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(auteurDTO)))
                .andExpect(status().isOk());

        // Validate the Auteur in the database
        List<Auteur> auteurs = auteurRepository.findAll();
        assertThat(auteurs).hasSize(databaseSizeBeforeUpdate);
        Auteur testAuteur = auteurs.get(auteurs.size() - 1);
        assertThat(testAuteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAuteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void deleteAuteur() throws Exception {
        // Initialize the database
        auteurRepository.saveAndFlush(auteur);
        int databaseSizeBeforeDelete = auteurRepository.findAll().size();

        // Get the auteur
        restAuteurMockMvc.perform(delete("/api/auteurs/{id}", auteur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Auteur> auteurs = auteurRepository.findAll();
        assertThat(auteurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
