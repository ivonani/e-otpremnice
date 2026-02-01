package org.eotpremnice.mapper;

import org.eotpremnice.entity.PromenaStatusaEntity;
import org.eotpremnice.model.PromenaStatusa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PromenaStatusaMapper {
    PromenaStatusa toModel(PromenaStatusaEntity entity);
}