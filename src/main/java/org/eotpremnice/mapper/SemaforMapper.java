package org.eotpremnice.mapper;

import org.eotpremnice.entity.SemaforEntity;
import org.eotpremnice.model.Semafor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SemaforMapper {
    @Mapping(source = "EDokument", target = "eDokument")
    Semafor toModel(SemaforEntity entity);
    @Mapping(source = "EDokument", target = "eDokument")
    SemaforEntity toEntity(Semafor entity);
}