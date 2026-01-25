package org.eotpremnice.mapper;

import org.eotpremnice.entity.OtpremniceEntity;
import org.eotpremnice.model.Otpremnice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {
                DateMapper.class,
                NumberMapper.class
        }
)
public interface OtpremniceMapper {

    @Mapping(
            target = "datumIzdavanja",
            source = "datumIzdavanja",
            qualifiedByName = "toLocalDate"
    )
    Otpremnice toModel(OtpremniceEntity entity);

    @Named("toLocalDate")
    static LocalDate toLocalDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(value.trim()); // yyyy-MM-dd
    }
}
