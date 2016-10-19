package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Emprunt;

import org.miage.m2sid.bibliotheque.repository.EmpruntRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.EmpruntDTO;
import org.miage.m2sid.bibliotheque.service.mapper.EmpruntMapper;
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
 * REST controller for managing Emprunt.
 */
@RestController
@RequestMapping("/api")
public class EmpruntResource {

    private final Logger log = LoggerFactory.getLogger(EmpruntResource.class);
        
    @Inject
    private EmpruntRepository empruntRepository;

    @Inject
    private EmpruntMapper empruntMapper;

    /**
     * POST  /emprunts : Create a new emprunt.
     *
     * @param empruntDTO the empruntDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empruntDTO, or with status 400 (Bad Request) if the emprunt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/emprunts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmpruntDTO> createEmprunt(@RequestBody EmpruntDTO empruntDTO) throws URISyntaxException {
        log.debug("REST request to save Emprunt : {}", empruntDTO);
        if (empruntDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("emprunt", "idexists", "A new emprunt cannot already have an ID")).body(null);
        }
        Emprunt emprunt = empruntMapper.empruntDTOToEmprunt(empruntDTO);
        emprunt = empruntRepository.save(emprunt);
        EmpruntDTO result = empruntMapper.empruntToEmpruntDTO(emprunt);
        return ResponseEntity.created(new URI("/api/emprunts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("emprunt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emprunts : Updates an existing emprunt.
     *
     * @param empruntDTO the empruntDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empruntDTO,
     * or with status 400 (Bad Request) if the empruntDTO is not valid,
     * or with status 500 (Internal Server Error) if the empruntDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/emprunts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmpruntDTO> updateEmprunt(@RequestBody EmpruntDTO empruntDTO) throws URISyntaxException {
        log.debug("REST request to update Emprunt : {}", empruntDTO);
        if (empruntDTO.getId() == null) {
            return createEmprunt(empruntDTO);
        }
        Emprunt emprunt = empruntMapper.empruntDTOToEmprunt(empruntDTO);
        emprunt = empruntRepository.save(emprunt);
        EmpruntDTO result = empruntMapper.empruntToEmpruntDTO(emprunt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("emprunt", empruntDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emprunts : get all the emprunts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emprunts in body
     */
    @RequestMapping(value = "/emprunts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmpruntDTO> getAllEmprunts() {
        log.debug("REST request to get all Emprunts");
        List<Emprunt> emprunts = empruntRepository.findAll();
        return empruntMapper.empruntsToEmpruntDTOs(emprunts);
    }

    /**
     * GET  /emprunts/:id : get the "id" emprunt.
     *
     * @param id the id of the empruntDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empruntDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/emprunts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmpruntDTO> getEmprunt(@PathVariable Long id) {
        log.debug("REST request to get Emprunt : {}", id);
        Emprunt emprunt = empruntRepository.findOne(id);
        EmpruntDTO empruntDTO = empruntMapper.empruntToEmpruntDTO(emprunt);
        return Optional.ofNullable(empruntDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /emprunts/:id : delete the "id" emprunt.
     *
     * @param id the id of the empruntDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/emprunts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmprunt(@PathVariable Long id) {
        log.debug("REST request to delete Emprunt : {}", id);
        empruntRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("emprunt", id.toString())).build();
    }

}
