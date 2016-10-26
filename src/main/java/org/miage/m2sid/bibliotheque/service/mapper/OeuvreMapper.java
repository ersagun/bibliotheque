package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.OeuvreDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Oeuvre and its DTO OeuvreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OeuvreMapper {

    @Mapping(target = "exemplaireDisponible", ignore = true)
    OeuvreDTO oeuvreToOeuvreDTO(Oeuvre oeuvre);

    List<OeuvreDTO> oeuvresToOeuvreDTOs(List<Oeuvre> oeuvres);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "exemplaires", ignore = true)
    @Mapping(target = "livre", ignore = true)
    @Mapping(target = "magazine", ignore = true)
    Oeuvre oeuvreDTOToOeuvre(OeuvreDTO oeuvreDTO);

    List<Oeuvre> oeuvreDTOsToOeuvres(List<OeuvreDTO> oeuvreDTOs);
}
