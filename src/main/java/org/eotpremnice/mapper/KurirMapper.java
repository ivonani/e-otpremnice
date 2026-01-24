package org.eotpremnice.mapper;

import org.eotpremnice.entity.KurirEntity;
import org.eotpremnice.model.Kurir;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface KurirMapper {

    @Mapping(target = "ime", expression = "java(extractIme(entity.getImePrezime()))")
    @Mapping(target = "prezime", expression = "java(extractPrezime(entity.getImePrezime()))")
    Kurir toModel(KurirEntity entity);

    default String extractIme(String imePrezime) {
        if (imePrezime == null || imePrezime.isEmpty()) {
            return null;
        }
        String[] parts = imePrezime.trim().split("\\s+");
        return parts.length >= 1 ? parts[0] : null;
    }

    default String extractPrezime(String imePrezime) {
        if (imePrezime == null || imePrezime.isEmpty()) {
            return null;
        }
        String[] parts = imePrezime.trim().split("\\s+");
        if (parts.length <= 1) {
            return null;
        }
        // sve osim prvog imena
        return String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
    }
}