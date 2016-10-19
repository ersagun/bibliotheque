package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.EmpruntDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Emprunt and its DTO EmpruntDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpruntMapper {

    @Mapping(source = "usager.id", target = "usagerId")
    @Mapping(source = "exemplaire.id", target = "exemplaireId")
    EmpruntDTO empruntToEmpruntDTO(Emprunt emprunt);

    List<EmpruntDTO> empruntsToEmpruntDTOs(List<Emprunt> emprunts);

    @Mapping(source = "usagerId", target = "usager")
    @Mapping(source = "exemplaireId", target = "exemplaire")
    Emprunt empruntDTOToEmprunt(EmpruntDTO empruntDTO);

    List<Emprunt> empruntDTOsToEmprunts(List<EmpruntDTO> empruntDTOs);

    default Usager usagerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usager usager = new Usager();
        usager.setId(id);
        return usager;
    }

    default Exemplaire exemplaireFromId(Long id) {
        if (id == null) {
            return null;
        }
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setId(id);
        return exemplaire;
    }
}
