package org.eotpremnice.mapper;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.model.Posiljalac;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FirmaToPosiljalacMapper {
    Posiljalac toModel(FirmaEntity entity);
}
