package org.eotpremnice.mapper;

import org.eotpremnice.entity.OdredisteEntity;
import org.eotpremnice.model.Odrediste;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OdredisteMapper {

    Odrediste toModel(OdredisteEntity entity);
}