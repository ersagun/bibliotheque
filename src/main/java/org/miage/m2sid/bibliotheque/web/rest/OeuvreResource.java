package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Oeuvre;

import org.miage.m2sid.bibliotheque.repository.ExemplaireRepository;
import org.miage.m2sid.bibliotheque.repository.OeuvreRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.OeuvreDTO;
import org.miage.m2sid.bibliotheque.service.mapper.OeuvreMapper;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Oeuvre.
 */
@RestController
@RequestMapping("/api")
public class OeuvreResource {

    private final Logger log = LoggerFactory.getLogger(OeuvreResource.class);

    @Inject
    private OeuvreRepository oeuvreRepository;

    @Inject
    private OeuvreMapper oeuvreMapper;

    @Inject
    private ExemplaireRepository exemplaireRepository;

    /**
     * POST  /oeuvres : Create a new oeuvre.
     *
     * @param oeuvreDTO the oeuvreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oeuvreDTO, or with status 400 (Bad Request) if the oeuvre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/oeuvres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OeuvreDTO> createOeuvre(@RequestBody OeuvreDTO oeuvreDTO) throws URISyntaxException {
        log.debug("REST request to save Oeuvre : {}", oeuvreDTO);
        if (oeuvreDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("oeuvre", "idexists", "A new oeuvre cannot already have an ID")).body(null);
        }
        Oeuvre oeuvre = oeuvreMapper.oeuvreDTOToOeuvre(oeuvreDTO);
        oeuvre = oeuvreRepository.save(oeuvre);
        OeuvreDTO result = oeuvreMapper.oeuvreToOeuvreDTO(oeuvre);
        return ResponseEntity.created(new URI("/api/oeuvres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("oeuvre", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oeuvres : Updates an existing oeuvre.
     *
     * @param oeuvreDTO the oeuvreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oeuvreDTO,
     * or with status 400 (Bad Request) if the oeuvreDTO is not valid,
     * or with status 500 (Internal Server Error) if the oeuvreDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/oeuvres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OeuvreDTO> updateOeuvre(@RequestBody OeuvreDTO oeuvreDTO) throws URISyntaxException {
        log.debug("REST request to update Oeuvre : {}", oeuvreDTO);
        if (oeuvreDTO.getId() == null) {
            return createOeuvre(oeuvreDTO);
        }
        Oeuvre oeuvre = oeuvreMapper.oeuvreDTOToOeuvre(oeuvreDTO);
        oeuvre = oeuvreRepository.save(oeuvre);
        OeuvreDTO result = oeuvreMapper.oeuvreToOeuvreDTO(oeuvre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("oeuvre", oeuvreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oeuvres : get all the oeuvres.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of oeuvres in body
     */
    @RequestMapping(value = "/oeuvres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OeuvreDTO> getAllOeuvres(@RequestParam(required = false) String filter) {
        if ("livre-is-null".equals(filter)) {
            log.debug("REST request to get all Oeuvres where livre is null");
            return StreamSupport
                .stream(oeuvreRepository.findAll().spliterator(), false)
                .filter(oeuvre -> oeuvre.getLivre() == null)
                .map(oeuvreMapper::oeuvreToOeuvreDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        if ("magazine-is-null".equals(filter)) {
            log.debug("REST request to get all Oeuvres where magazine is null");
            return StreamSupport
                .stream(oeuvreRepository.findAll().spliterator(), false)
                .filter(oeuvre -> oeuvre.getMagazine() == null)
                .map(oeuvreMapper::oeuvreToOeuvreDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all Oeuvres");
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();
        return oeuvreMapper.oeuvresToOeuvreDTOs(oeuvres);
    }

    /**
     * GET  /oeuvres/:id : get the "id" oeuvre.
     *
     * @param id the id of the oeuvreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oeuvreDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/oeuvres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OeuvreDTO> getOeuvre(@PathVariable Long id) {
        log.debug("REST request to get Oeuvre : {}", id);
        Oeuvre oeuvre = oeuvreRepository.findOne(id);
        OeuvreDTO oeuvreDTO = oeuvreMapper.oeuvreToOeuvreDTO(oeuvre);
        oeuvreDTO.setExemplaires(oeuvre.getExemplaires());
        oeuvreDTO.setExemplaireDisponible(exemplaireRepository.countDisponibleExemplaireBylivre(id)>0);
        return Optional.ofNullable(oeuvreDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /oeuvres/:id : delete the "id" oeuvre.
     *
     * @param id the id of the oeuvreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/oeuvres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOeuvre(@PathVariable Long id) {
        log.debug("REST request to delete Oeuvre : {}", id);
        oeuvreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oeuvre", id.toString())).build();
    }

}
