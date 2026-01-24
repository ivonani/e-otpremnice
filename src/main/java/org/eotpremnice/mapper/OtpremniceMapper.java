package org.eotpremnice.mapper;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.OtpremniceEntity;
import org.eotpremnice.model.Otpremnice;
import org.eotpremnice.model.Posiljalac;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {
                DateMapper.class,
                NumberMapper.class
        }
)
public interface OtpremniceMapper {
    Otpremnice toModel(OtpremniceEntity entity);
}
