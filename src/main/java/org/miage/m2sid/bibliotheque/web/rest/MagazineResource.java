package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Magazine;

import org.miage.m2sid.bibliotheque.repository.MagazineRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.MagazineDTO;
import org.miage.m2sid.bibliotheque.service.mapper.MagazineMapper;
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
 * REST controller for managing Magazine.
 */
@RestController
@RequestMapping("/api")
public class MagazineResource {

    private final Logger log = LoggerFactory.getLogger(MagazineResource.class);
        
    @Inject
    private MagazineRepository magazineRepository;

    @Inject
    private MagazineMapper magazineMapper;

    /**
     * POST  /magazines : Create a new magazine.
     *
     * @param magazineDTO the magazineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new magazineDTO, or with status 400 (Bad Request) if the magazine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/magazines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MagazineDTO> createMagazine(@RequestBody MagazineDTO magazineDTO) throws URISyntaxException {
        log.debug("REST request to save Magazine : {}", magazineDTO);
        if (magazineDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("magazine", "idexists", "A new magazine cannot already have an ID")).body(null);
        }
        Magazine magazine = magazineMapper.magazineDTOToMagazine(magazineDTO);
        magazine = magazineRepository.save(magazine);
        MagazineDTO result = magazineMapper.magazineToMagazineDTO(magazine);
        return ResponseEntity.created(new URI("/api/magazines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("magazine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /magazines : Updates an existing magazine.
     *
     * @param magazineDTO the magazineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated magazineDTO,
     * or with status 400 (Bad Request) if the magazineDTO is not valid,
     * or with status 500 (Internal Server Error) if the magazineDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/magazines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MagazineDTO> updateMagazine(@RequestBody MagazineDTO magazineDTO) throws URISyntaxException {
        log.debug("REST request to update Magazine : {}", magazineDTO);
        if (magazineDTO.getId() == null) {
            return createMagazine(magazineDTO);
        }
        Magazine magazine = magazineMapper.magazineDTOToMagazine(magazineDTO);
        magazine = magazineRepository.save(magazine);
        MagazineDTO result = magazineMapper.magazineToMagazineDTO(magazine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("magazine", magazineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /magazines : get all the magazines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of magazines in body
     */
    @RequestMapping(value = "/magazines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MagazineDTO> getAllMagazines() {
        log.debug("REST request to get all Magazines");
        List<Magazine> magazines = magazineRepository.findAll();
        return magazineMapper.magazinesToMagazineDTOs(magazines);
    }

    /**
     * GET  /magazines/:id : get the "id" magazine.
     *
     * @param id the id of the magazineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the magazineDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/magazines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MagazineDTO> getMagazine(@PathVariable Long id) {
        log.debug("REST request to get Magazine : {}", id);
        Magazine magazine = magazineRepository.findOne(id);
        MagazineDTO magazineDTO = magazineMapper.magazineToMagazineDTO(magazine);
        return Optional.ofNullable(magazineDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /magazines/:id : delete the "id" magazine.
     *
     * @param id the id of the magazineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/magazines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMagazine(@PathVariable Long id) {
        log.debug("REST request to delete Magazine : {}", id);
        magazineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("magazine", id.toString())).build();
    }

}
