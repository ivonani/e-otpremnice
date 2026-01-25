package org.eotpremnice.mapper;

import org.eotpremnice.entity.MagacinEntity;
import org.eotpremnice.model.Magacin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MagacinMapper {
    Magacin toModel(MagacinEntity entity);
}