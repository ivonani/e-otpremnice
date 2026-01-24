package org.eotpremnice.xml.builder;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;


import org.eotpremnice.model.Otpremnice;
import org.eotpremnice.service.OtpremniceService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DespatchAdviceXmlBuilder {

    private final OtpremniceService otpremniceService;
    private final UBLExtensionBuilder ublExtensionBuilder;

    public DespatchAdviceType builder(String idFirme, String tipDokumenta, Long iddok) throws Exception {


        // 1. Create EMPTY DespatchAdvice
        DespatchAdviceType advice = new DespatchAdviceType();
        Otpremnice otpremnice = otpremniceService.loadOtpremnice(idFirme, tipDokumenta, iddok);
        advice.setUBLExtensions(UBLExtensionBuilder.build(otpremnice));
        return advice;

    }
}
