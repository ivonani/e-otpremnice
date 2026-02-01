package org.eotpremnice.mapper;

import org.eotpremnice.entity.PromenaStatusaEntity;
import org.eotpremnice.model.PromenaStatusa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PromenaStatusaMapper {
    @Mapping(target = "datumPromene", source = "datumPromene", qualifiedByName = "toLocalDate")
    @Mapping(target = "datumIzdavanja", source = "datumIzdavanja", qualifiedByName = "toLocalDate")
    PromenaStatusa toModel(PromenaStatusaEntity entity);

    @Named("toLocalDate")
    static LocalDate toLocalDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(value.trim()); // yyyy-MM-dd
    }
}