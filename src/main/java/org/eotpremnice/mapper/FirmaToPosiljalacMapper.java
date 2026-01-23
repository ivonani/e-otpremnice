package org.eotpremnice.mapper;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.mapper.config.MapstructMapperConfiguration;
import org.eotpremnice.model.Posiljalac;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FirmaToPosiljalacMapper {
    @Mapping(source = "pib_rs", target = "pibRs")   // IMPORTANT: different name
    Posiljalac toModel(FirmaEntity entity);
}
