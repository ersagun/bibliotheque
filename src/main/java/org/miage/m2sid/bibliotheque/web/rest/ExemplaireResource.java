package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Exemplaire;

import org.miage.m2sid.bibliotheque.repository.ExemplaireRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.ExemplaireDTO;
import org.miage.m2sid.bibliotheque.service.mapper.ExemplaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Exemplaire.
 */
@RestController
@RequestMapping("/api")
public class ExemplaireResource {

    private final Logger log = LoggerFactory.getLogger(ExemplaireResource.class);
        
    @Inject
    private ExemplaireRepository exemplaireRepository;

    @Inject
    private ExemplaireMapper exemplaireMapper;

    /**
     * POST  /exemplaires : Create a new exemplaire.
     *
     * @param exemplaireDTO the exemplaireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exemplaireDTO, or with status 400 (Bad Request) if the exemplaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exemplaires",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExemplaireDTO> createExemplaire(@RequestBody ExemplaireDTO exemplaireDTO) throws URISyntaxException {
        log.debug("REST request to save Exemplaire : {}", exemplaireDTO);
        if (exemplaireDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("exemplaire", "idexists", "A new exemplaire cannot already have an ID")).body(null);
        }
        Exemplaire exemplaire = exemplaireMapper.exemplaireDTOToExemplaire(exemplaireDTO);
        exemplaire = exemplaireRepository.save(exemplaire);
        ExemplaireDTO result = exemplaireMapper.exemplaireToExemplaireDTO(exemplaire);
        return ResponseEntity.created(new URI("/api/exemplaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("exemplaire", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exemplaires : Updates an existing exemplaire.
     *
     * @param exemplaireDTO the exemplaireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exemplaireDTO,
     * or with status 400 (Bad Request) if the exemplaireDTO is not valid,
     * or with status 500 (Internal Server Error) if the exemplaireDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exemplaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExemplaireDTO> updateExemplaire(@RequestBody ExemplaireDTO exemplaireDTO) throws URISyntaxException {
        log.debug("REST request to update Exemplaire : {}", exemplaireDTO);
        if (exemplaireDTO.getId() == null) {
            return createExemplaire(exemplaireDTO);
        }
        Exemplaire exemplaire = exemplaireMapper.exemplaireDTOToExemplaire(exemplaireDTO);
        exemplaire = exemplaireRepository.save(exemplaire);
        ExemplaireDTO result = exemplaireMapper.exemplaireToExemplaireDTO(exemplaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("exemplaire", exemplaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exemplaires : get all the exemplaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of exemplaires in body
     */
    @RequestMapping(value = "/exemplaires",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ExemplaireDTO> getAllExemplaires() {
        log.debug("REST request to get all Exemplaires");
        List<Exemplaire> exemplaires = exemplaireRepository.findAll();
        return exemplaireMapper.exemplairesToExemplaireDTOs(exemplaires);
    }

    /**
     * GET  /exemplaires/:id : get the "id" exemplaire.
     *
     * @param id the id of the exemplaireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exemplaireDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/exemplaires/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExemplaireDTO> getExemplaire(@PathVariable Long id) {
        log.debug("REST request to get Exemplaire : {}", id);
        Exemplaire exemplaire = exemplaireRepository.findOne(id);
        ExemplaireDTO exemplaireDTO = exemplaireMapper.exemplaireToExemplaireDTO(exemplaire);
        return Optional.ofNullable(exemplaireDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /exemplaires/:id : delete the "id" exemplaire.
     *
     * @param id the id of the exemplaireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/exemplaires/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExemplaire(@PathVariable Long id) {
        log.debug("REST request to delete Exemplaire : {}", id);
        exemplaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("exemplaire", id.toString())).build();
    }

}
