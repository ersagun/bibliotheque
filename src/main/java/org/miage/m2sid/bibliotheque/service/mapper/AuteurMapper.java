package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.AuteurDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Auteur and its DTO AuteurDTO.
 */
@Mapper(componentModel = "spring", uses = {LivreMapper.class, })
public interface AuteurMapper {

    AuteurDTO auteurToAuteurDTO(Auteur auteur);

    List<AuteurDTO> auteursToAuteurDTOs(List<Auteur> auteurs);

    Auteur auteurDTOToAuteur(AuteurDTO auteurDTO);

    List<Auteur> auteurDTOsToAuteurs(List<AuteurDTO> auteurDTOs);

    default Livre livreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Livre livre = new Livre();
        livre.setId(id);
        return livre;
    }
}
