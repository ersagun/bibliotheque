package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Auteur;

import org.miage.m2sid.bibliotheque.repository.AuteurRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.AuteurDTO;
import org.miage.m2sid.bibliotheque.service.mapper.AuteurMapper;
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
 * REST controller for managing Auteur.
 */
@RestController
@RequestMapping("/api")
public class AuteurResource {

    private final Logger log = LoggerFactory.getLogger(AuteurResource.class);
        
    @Inject
    private AuteurRepository auteurRepository;

    @Inject
    private AuteurMapper auteurMapper;

    /**
     * POST  /auteurs : Create a new auteur.
     *
     * @param auteurDTO the auteurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auteurDTO, or with status 400 (Bad Request) if the auteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/auteurs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuteurDTO> createAuteur(@RequestBody AuteurDTO auteurDTO) throws URISyntaxException {
        log.debug("REST request to save Auteur : {}", auteurDTO);
        if (auteurDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("auteur", "idexists", "A new auteur cannot already have an ID")).body(null);
        }
        Auteur auteur = auteurMapper.auteurDTOToAuteur(auteurDTO);
        auteur = auteurRepository.save(auteur);
        AuteurDTO result = auteurMapper.auteurToAuteurDTO(auteur);
        return ResponseEntity.created(new URI("/api/auteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("auteur", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auteurs : Updates an existing auteur.
     *
     * @param auteurDTO the auteurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auteurDTO,
     * or with status 400 (Bad Request) if the auteurDTO is not valid,
     * or with status 500 (Internal Server Error) if the auteurDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/auteurs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuteurDTO> updateAuteur(@RequestBody AuteurDTO auteurDTO) throws URISyntaxException {
        log.debug("REST request to update Auteur : {}", auteurDTO);
        if (auteurDTO.getId() == null) {
            return createAuteur(auteurDTO);
        }
        Auteur auteur = auteurMapper.auteurDTOToAuteur(auteurDTO);
        auteur = auteurRepository.save(auteur);
        AuteurDTO result = auteurMapper.auteurToAuteurDTO(auteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("auteur", auteurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auteurs : get all the auteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auteurs in body
     */
    @RequestMapping(value = "/auteurs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuteurDTO> getAllAuteurs() {
        log.debug("REST request to get all Auteurs");
        List<Auteur> auteurs = auteurRepository.findAllWithEagerRelationships();
        return auteurMapper.auteursToAuteurDTOs(auteurs);
    }

    /**
     * GET  /auteurs/:id : get the "id" auteur.
     *
     * @param id the id of the auteurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auteurDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/auteurs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuteurDTO> getAuteur(@PathVariable Long id) {
        log.debug("REST request to get Auteur : {}", id);
        Auteur auteur = auteurRepository.findOneWithEagerRelationships(id);
        AuteurDTO auteurDTO = auteurMapper.auteurToAuteurDTO(auteur);
        return Optional.ofNullable(auteurDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /auteurs/:id : delete the "id" auteur.
     *
     * @param id the id of the auteurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/auteurs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        log.debug("REST request to delete Auteur : {}", id);
        auteurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("auteur", id.toString())).build();
    }

}
