package org.eotpremnice.mapper;

import org.eotpremnice.entity.OtpremniceEntity;
import org.eotpremnice.model.Otpremnice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {
                DateMapper.class,
                NumberMapper.class
        }
)
public interface OtpremniceMapper {

    @Mapping(target = "datumIzdavanja", source = "datumIzdavanja", qualifiedByName = "isoDate")
    Otpremnice toModel(OtpremniceEntity entity);

    @Named("isoDate")
    static Date isoDate(String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return sdf.parse(value.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date (expected yyyy-MM-dd): " + value, e);
        }
    }
}
