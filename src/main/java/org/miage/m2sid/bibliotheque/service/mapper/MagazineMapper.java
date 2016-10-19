package org.miage.m2sid.bibliotheque.service.mapper;

import org.miage.m2sid.bibliotheque.domain.*;
import org.miage.m2sid.bibliotheque.service.dto.MagazineDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Magazine and its DTO MagazineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MagazineMapper {

    @Mapping(source = "oeuvre.id", target = "oeuvreId")
    MagazineDTO magazineToMagazineDTO(Magazine magazine);

    List<MagazineDTO> magazinesToMagazineDTOs(List<Magazine> magazines);

    @Mapping(source = "oeuvreId", target = "oeuvre")
    Magazine magazineDTOToMagazine(MagazineDTO magazineDTO);

    List<Magazine> magazineDTOsToMagazines(List<MagazineDTO> magazineDTOs);

    default Oeuvre oeuvreFromId(Long id) {
        if (id == null) {
            return null;
        }
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setId(id);
        return oeuvre;
    }
}
