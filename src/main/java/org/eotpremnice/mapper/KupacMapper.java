package org.eotpremnice.mapper;

import org.eotpremnice.entity.KupacEntity;
import org.eotpremnice.model.Kupac;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface KupacMapper {

    Kupac toModel(KupacEntity entity);
}
