package org.miage.m2sid.bibliotheque.web.rest;

import org.miage.m2sid.bibliotheque.BibliothequeApp;

import org.miage.m2sid.bibliotheque.domain.Emprunt;
import org.miage.m2sid.bibliotheque.repository.EmpruntRepository;
import org.miage.m2sid.bibliotheque.service.dto.EmpruntDTO;
import org.miage.m2sid.bibliotheque.service.mapper.EmpruntMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmpruntResource REST controller.
 *
 * @see EmpruntResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliothequeApp.class)
public class EmpruntResourceIntTest {

    private static final ZonedDateTime DEFAULT_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DEBUT_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DEBUT);

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    @Inject
    private EmpruntRepository empruntRepository;

    @Inject
    private EmpruntMapper empruntMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEmpruntMockMvc;

    private Emprunt emprunt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpruntResource empruntResource = new EmpruntResource();
        ReflectionTestUtils.setField(empruntResource, "empruntRepository", empruntRepository);
        ReflectionTestUtils.setField(empruntResource, "empruntMapper", empruntMapper);
        this.restEmpruntMockMvc = MockMvcBuilders.standaloneSetup(empruntResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emprunt createEntity(EntityManager em) {
        Emprunt emprunt = new Emprunt()
                .debut(DEFAULT_DEBUT)
                .duree(DEFAULT_DUREE);
        return emprunt;
    }

    @Before
    public void initTest() {
        emprunt = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmprunt() throws Exception {
        int databaseSizeBeforeCreate = empruntRepository.findAll().size();

        // Create the Emprunt
        EmpruntDTO empruntDTO = empruntMapper.empruntToEmpruntDTO(emprunt);

        restEmpruntMockMvc.perform(post("/api/emprunts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empruntDTO)))
                .andExpect(status().isCreated());

        // Validate the Emprunt in the database
        List<Emprunt> emprunts = empruntRepository.findAll();
        assertThat(emprunts).hasSize(databaseSizeBeforeCreate + 1);
        Emprunt testEmprunt = emprunts.get(emprunts.size() - 1);
        assertThat(testEmprunt.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testEmprunt.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    public void getAllEmprunts() throws Exception {
        // Initialize the database
        empruntRepository.saveAndFlush(emprunt);

        // Get all the emprunts
        restEmpruntMockMvc.perform(get("/api/emprunts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(emprunt.getId().intValue())))
                .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT_STR)))
                .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }

    @Test
    @Transactional
    public void getEmprunt() throws Exception {
        // Initialize the database
        empruntRepository.saveAndFlush(emprunt);

        // Get the emprunt
        restEmpruntMockMvc.perform(get("/api/emprunts/{id}", emprunt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emprunt.getId().intValue()))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT_STR))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE));
    }

    @Test
    @Transactional
    public void getNonExistingEmprunt() throws Exception {
        // Get the emprunt
        restEmpruntMockMvc.perform(get("/api/emprunts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmprunt() throws Exception {
        // Initialize the database
        empruntRepository.saveAndFlush(emprunt);
        int databaseSizeBeforeUpdate = empruntRepository.findAll().size();

        // Update the emprunt
        Emprunt updatedEmprunt = empruntRepository.findOne(emprunt.getId());
        updatedEmprunt
                .debut(UPDATED_DEBUT)
                .duree(UPDATED_DUREE);
        EmpruntDTO empruntDTO = empruntMapper.empruntToEmpruntDTO(updatedEmprunt);

        restEmpruntMockMvc.perform(put("/api/emprunts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empruntDTO)))
                .andExpect(status().isOk());

        // Validate the Emprunt in the database
        List<Emprunt> emprunts = empruntRepository.findAll();
        assertThat(emprunts).hasSize(databaseSizeBeforeUpdate);
        Emprunt testEmprunt = emprunts.get(emprunts.size() - 1);
        assertThat(testEmprunt.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testEmprunt.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void deleteEmprunt() throws Exception {
        // Initialize the database
        empruntRepository.saveAndFlush(emprunt);
        int databaseSizeBeforeDelete = empruntRepository.findAll().size();

        // Get the emprunt
        restEmpruntMockMvc.perform(delete("/api/emprunts/{id}", emprunt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Emprunt> emprunts = empruntRepository.findAll();
        assertThat(emprunts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
