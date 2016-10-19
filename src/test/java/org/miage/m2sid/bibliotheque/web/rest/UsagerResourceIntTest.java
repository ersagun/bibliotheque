package org.miage.m2sid.bibliotheque.web.rest;

import org.miage.m2sid.bibliotheque.BibliothequeApp;

import org.miage.m2sid.bibliotheque.domain.Usager;
import org.miage.m2sid.bibliotheque.repository.UsagerRepository;
import org.miage.m2sid.bibliotheque.service.dto.UsagerDTO;
import org.miage.m2sid.bibliotheque.service.mapper.UsagerMapper;

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
 * Test class for the UsagerResource REST controller.
 *
 * @see UsagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliothequeApp.class)
public class UsagerResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAA";
    private static final String UPDATED_ADRESSE = "BBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    @Inject
    private UsagerRepository usagerRepository;

    @Inject
    private UsagerMapper usagerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUsagerMockMvc;

    private Usager usager;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsagerResource usagerResource = new UsagerResource();
        ReflectionTestUtils.setField(usagerResource, "usagerRepository", usagerRepository);
        ReflectionTestUtils.setField(usagerResource, "usagerMapper", usagerMapper);
        this.restUsagerMockMvc = MockMvcBuilders.standaloneSetup(usagerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usager createEntity(EntityManager em) {
        Usager usager = new Usager()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .adresse(DEFAULT_ADRESSE)
                .dateNaissance(DEFAULT_DATE_NAISSANCE)
                .telephone(DEFAULT_TELEPHONE);
        return usager;
    }

    @Before
    public void initTest() {
        usager = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsager() throws Exception {
        int databaseSizeBeforeCreate = usagerRepository.findAll().size();

        // Create the Usager
        UsagerDTO usagerDTO = usagerMapper.usagerToUsagerDTO(usager);

        restUsagerMockMvc.perform(post("/api/usagers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usagerDTO)))
                .andExpect(status().isCreated());

        // Validate the Usager in the database
        List<Usager> usagers = usagerRepository.findAll();
        assertThat(usagers).hasSize(databaseSizeBeforeCreate + 1);
        Usager testUsager = usagers.get(usagers.size() - 1);
        assertThat(testUsager.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testUsager.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testUsager.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testUsager.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testUsager.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllUsagers() throws Exception {
        // Initialize the database
        usagerRepository.saveAndFlush(usager);

        // Get all the usagers
        restUsagerMockMvc.perform(get("/api/usagers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(usager.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
                .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
                .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    @Transactional
    public void getUsager() throws Exception {
        // Initialize the database
        usagerRepository.saveAndFlush(usager);

        // Get the usager
        restUsagerMockMvc.perform(get("/api/usagers/{id}", usager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usager.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }

    @Test
    @Transactional
    public void getNonExistingUsager() throws Exception {
        // Get the usager
        restUsagerMockMvc.perform(get("/api/usagers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsager() throws Exception {
        // Initialize the database
        usagerRepository.saveAndFlush(usager);
        int databaseSizeBeforeUpdate = usagerRepository.findAll().size();

        // Update the usager
        Usager updatedUsager = usagerRepository.findOne(usager.getId());
        updatedUsager
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .adresse(UPDATED_ADRESSE)
                .dateNaissance(UPDATED_DATE_NAISSANCE)
                .telephone(UPDATED_TELEPHONE);
        UsagerDTO usagerDTO = usagerMapper.usagerToUsagerDTO(updatedUsager);

        restUsagerMockMvc.perform(put("/api/usagers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usagerDTO)))
                .andExpect(status().isOk());

        // Validate the Usager in the database
        List<Usager> usagers = usagerRepository.findAll();
        assertThat(usagers).hasSize(databaseSizeBeforeUpdate);
        Usager testUsager = usagers.get(usagers.size() - 1);
        assertThat(testUsager.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testUsager.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testUsager.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUsager.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testUsager.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void deleteUsager() throws Exception {
        // Initialize the database
        usagerRepository.saveAndFlush(usager);
        int databaseSizeBeforeDelete = usagerRepository.findAll().size();

        // Get the usager
        restUsagerMockMvc.perform(delete("/api/usagers/{id}", usager.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Usager> usagers = usagerRepository.findAll();
        assertThat(usagers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
