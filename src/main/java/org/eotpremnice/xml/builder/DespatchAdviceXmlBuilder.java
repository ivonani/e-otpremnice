package org.eotpremnice.xml.builder;

import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;


import org.springframework.stereotype.Component;

@Component
public class DespatchAdviceXmlBuilder {

    public static DespatchAdviceType builder(Long iddok) {

        // 1. Create EMPTY DespatchAdvice
        DespatchAdviceType advice = new DespatchAdviceType();
        return advice;

    }
}
