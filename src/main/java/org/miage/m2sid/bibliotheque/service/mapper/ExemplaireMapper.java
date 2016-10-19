package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.ExemplaireDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Exemplaire and its DTO ExemplaireDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExemplaireMapper {

    @Mapping(source = "oeuvre.id", target = "oeuvreId")
    ExemplaireDTO exemplaireToExemplaireDTO(Exemplaire exemplaire);

    List<ExemplaireDTO> exemplairesToExemplaireDTOs(List<Exemplaire> exemplaires);

    @Mapping(target = "emprunts", ignore = true)
    @Mapping(source = "oeuvreId", target = "oeuvre")
    Exemplaire exemplaireDTOToExemplaire(ExemplaireDTO exemplaireDTO);

    List<Exemplaire> exemplaireDTOsToExemplaires(List<ExemplaireDTO> exemplaireDTOs);

    default Oeuvre oeuvreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setId(id);
        return oeuvre;
    }
}
