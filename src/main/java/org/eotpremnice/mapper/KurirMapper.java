package org.eotpremnice.mapper;

import org.eotpremnice.entity.KurirEntity;
import org.eotpremnice.model.Kurir;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface KurirMapper {

    Kurir toModel(KurirEntity entity);
}