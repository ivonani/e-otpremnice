package org.eotpremnice.mapper;

import org.eotpremnice.entity.StavkeEntity;
import org.eotpremnice.model.Stavke;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StavkeMapper {

    @Mapping(source = "id.RBr", target = "rbr")
    @Mapping(source = "JMere", target = "jMere")
    @Mapping(source = "AKategorija", target = "aKategorija")
    @Mapping(source = "ATipKolicine", target = "aTipKolicine")
    @Mapping(source = "AKolicina", target = "aKolicina")
    Stavke toModel(StavkeEntity entity);
}