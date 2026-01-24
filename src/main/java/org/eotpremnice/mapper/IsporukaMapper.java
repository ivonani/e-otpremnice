package org.eotpremnice.mapper;

import org.eotpremnice.entity.IsporukaEntity;
import org.eotpremnice.model.Isporuka;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IsporukaMapper {

    Isporuka toModel(IsporukaEntity entity);
}
