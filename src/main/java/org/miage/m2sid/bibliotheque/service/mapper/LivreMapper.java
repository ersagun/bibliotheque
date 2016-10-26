package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.LivreDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Livre and its DTO LivreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LivreMapper {


    @Mapping(source = "oeuvre.id", target = "oeuvreId")
    @Mapping(source = "oeuvre.titre", target = "titre")
    @Mapping(source = "auteurs", target = "auteurs")
    LivreDTO livreToLivreDTO(Livre livre);


    List<LivreDTO> livresToLivreDTOs(List<Livre> livres);

    @Mapping(source = "oeuvreId", target = "oeuvre")
    @Mapping(target = "auteurs", ignore = true)
    Livre livreDTOToLivre(LivreDTO livreDTO);

    List<Livre> livreDTOsToLivres(List<LivreDTO> livreDTOs);

    default Oeuvre oeuvreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setId(id);
        return oeuvre;
    }
}
