package org.eotpremnice.mapper;

import org.eotpremnice.entity.VozacEntity;
import org.eotpremnice.model.Vozac;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface VozacMapper {
    Vozac toModel(VozacEntity entity);
}