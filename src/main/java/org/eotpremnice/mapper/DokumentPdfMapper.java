package org.eotpremnice.mapper;

import org.eotpremnice.entity.DokumentPDFEntity;
import org.eotpremnice.model.DokumentPdf;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DokumentPdfMapper {

    DokumentPdf toModel(DokumentPDFEntity entity);

}
