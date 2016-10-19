package org.miage.m2sid.bibliotheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.miage.m2sid.bibliotheque.domain.Reservation;

import org.miage.m2sid.bibliotheque.repository.ReservationRepository;
import org.miage.m2sid.bibliotheque.web.rest.util.HeaderUtil;
import org.miage.m2sid.bibliotheque.service.dto.ReservationDTO;
import org.miage.m2sid.bibliotheque.service.mapper.ReservationMapper;
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
 * REST controller for managing Reservation.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);
        
    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ReservationMapper reservationMapper;

    /**
     * POST  /reservations : Create a new reservation.
     *
     * @param reservationDTO the reservationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservationDTO, or with status 400 (Bad Request) if the reservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservationDTO);
        if (reservationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reservation", "idexists", "A new reservation cannot already have an ID")).body(null);
        }
        Reservation reservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        reservation = reservationRepository.save(reservation);
        ReservationDTO result = reservationMapper.reservationToReservationDTO(reservation);
        return ResponseEntity.created(new URI("/api/reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reservation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservations : Updates an existing reservation.
     *
     * @param reservationDTO the reservationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservationDTO,
     * or with status 400 (Bad Request) if the reservationDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}", reservationDTO);
        if (reservationDTO.getId() == null) {
            return createReservation(reservationDTO);
        }
        Reservation reservation = reservationMapper.reservationDTOToReservation(reservationDTO);
        reservation = reservationRepository.save(reservation);
        ReservationDTO result = reservationMapper.reservationToReservationDTO(reservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reservation", reservationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reservations : get all the reservations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reservations in body
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ReservationDTO> getAllReservations() {
        log.debug("REST request to get all Reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper.reservationsToReservationDTOs(reservations);
    }

    /**
     * GET  /reservations/:id : get the "id" reservation.
     *
     * @param id the id of the reservationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/reservations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        ReservationDTO reservationDTO = reservationMapper.reservationToReservationDTO(reservation);
        return Optional.ofNullable(reservationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reservations/:id : delete the "id" reservation.
     *
     * @param id the id of the reservationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/reservations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reservation", id.toString())).build();
    }

}
