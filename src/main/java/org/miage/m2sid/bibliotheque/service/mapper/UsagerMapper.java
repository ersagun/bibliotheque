package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.UsagerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Usager and its DTO UsagerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsagerMapper {

    UsagerDTO usagerToUsagerDTO(Usager usager);

    List<UsagerDTO> usagersToUsagerDTOs(List<Usager> usagers);

    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "emprunts", ignore = true)
    Usager usagerDTOToUsager(UsagerDTO usagerDTO);

    List<Usager> usagerDTOsToUsagers(List<UsagerDTO> usagerDTOs);
}
