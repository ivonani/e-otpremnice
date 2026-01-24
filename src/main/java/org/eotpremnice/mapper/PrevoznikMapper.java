package org.eotpremnice.mapper;

import org.eotpremnice.entity.PrevoznikEntity;
import org.eotpremnice.model.Prevoznik;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PrevoznikMapper {
    Prevoznik toModel(PrevoznikEntity entity);
}
