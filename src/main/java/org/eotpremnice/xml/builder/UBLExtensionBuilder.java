package org.eotpremnice.xml.builder;

import org.springframework.stereotype.Component;

import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;


@Component
public class UBLExtensionBuilder {

    public static UBLExtensionsType build() {
        UBLExtensionsType exts = new UBLExtensionsType();

        UBLExtensionType ext = new UBLExtensionType();
        ext.setExtensionContent(new ExtensionContentType()); // <cec:ExtensionContent/>

        exts.getUBLExtension().add(ext);
        return exts;
    }
}
