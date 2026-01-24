package org.eotpremnice.mapper;

import org.eotpremnice.entity.KurirEntity;
import org.eotpremnice.model.Kurir;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface KurirMapper {

    @Mapping(target = "ime", source = "imePrezime", qualifiedByName = "extractIme")
    @Mapping(target = "prezime", source = "imePrezime", qualifiedByName = "extractPrezime")
    Kurir toModel(KurirEntity entity);

    @Named("extractIme")
    default String extractIme(String imePrezime) {
        if (imePrezime == null || imePrezime.isEmpty()) return null;
        String[] parts = imePrezime.trim().split("\\s+");
        return parts.length >= 1 ? parts[0] : null;
    }

    @Named("extractPrezime")
    default String extractPrezime(String imePrezime) {
        if (imePrezime == null || imePrezime.isEmpty()) return null;
        String[] parts = imePrezime.trim().split("\\s+");
        if (parts.length <= 1) return null;
        return String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
    }
}