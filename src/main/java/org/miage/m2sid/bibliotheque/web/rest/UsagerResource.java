package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Usager;

import org.miage.m2sid.bibliotheque.repository.UsagerRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.UsagerDTO;
import org.miage.m2sid.bibliotheque.service.mapper.UsagerMapper;
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
 * REST controller for managing Usager.
 */
@RestController
@RequestMapping("/api")
public class UsagerResource {

    private final Logger log = LoggerFactory.getLogger(UsagerResource.class);
        
    @Inject
    private UsagerRepository usagerRepository;

    @Inject
    private UsagerMapper usagerMapper;

    /**
     * POST  /usagers : Create a new usager.
     *
     * @param usagerDTO the usagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usagerDTO, or with status 400 (Bad Request) if the usager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usagers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UsagerDTO> createUsager(@RequestBody UsagerDTO usagerDTO) throws URISyntaxException {
        log.debug("REST request to save Usager : {}", usagerDTO);
        if (usagerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usager", "idexists", "A new usager cannot already have an ID")).body(null);
        }
        Usager usager = usagerMapper.usagerDTOToUsager(usagerDTO);
        usager = usagerRepository.save(usager);
        UsagerDTO result = usagerMapper.usagerToUsagerDTO(usager);
        return ResponseEntity.created(new URI("/api/usagers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("usager", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usagers : Updates an existing usager.
     *
     * @param usagerDTO the usagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usagerDTO,
     * or with status 400 (Bad Request) if the usagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the usagerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/usagers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UsagerDTO> updateUsager(@RequestBody UsagerDTO usagerDTO) throws URISyntaxException {
        log.debug("REST request to update Usager : {}", usagerDTO);
        if (usagerDTO.getId() == null) {
            return createUsager(usagerDTO);
        }
        Usager usager = usagerMapper.usagerDTOToUsager(usagerDTO);
        usager = usagerRepository.save(usager);
        UsagerDTO result = usagerMapper.usagerToUsagerDTO(usager);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("usager", usagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usagers : get all the usagers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of usagers in body
     */
    @RequestMapping(value = "/usagers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UsagerDTO> getAllUsagers() {
        log.debug("REST request to get all Usagers");
        List<Usager> usagers = usagerRepository.findAll();
        return usagerMapper.usagersToUsagerDTOs(usagers);
    }

    /**
     * GET  /usagers/:id : get the "id" usager.
     *
     * @param id the id of the usagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usagerDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/usagers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UsagerDTO> getUsager(@PathVariable Long id) {
        log.debug("REST request to get Usager : {}", id);
        Usager usager = usagerRepository.findOne(id);
        UsagerDTO usagerDTO = usagerMapper.usagerToUsagerDTO(usager);
        return Optional.ofNullable(usagerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /usagers/:id : delete the "id" usager.
     *
     * @param id the id of the usagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/usagers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUsager(@PathVariable Long id) {
        log.debug("REST request to delete Usager : {}", id);
        usagerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usager", id.toString())).build();
    }

}
