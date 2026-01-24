package org.eotpremnice.xml.builder;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProfileIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.ObjectFactory;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.*;

import org.eotpremnice.model.Posiljalac;
import org.springframework.stereotype.Component;

@Component
public class DespatchAdviceXmlBuilder {

    public static DespatchAdviceType empty() {

        // 1. Create EMPTY DespatchAdvice
        DespatchAdviceType advice = new DespatchAdviceType();
        return advice;

    }
}
