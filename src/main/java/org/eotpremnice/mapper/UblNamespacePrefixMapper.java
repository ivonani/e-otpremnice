package org.eotpremnice.mapper;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class UblNamespacePrefixMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        switch (namespaceUri) {
            case "urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2":
                return ""; // default namespace (no prefix)
            case "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2":
                return "cec";
            case "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2":
                return "cac";
            case "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2":
                return "cbc";
            case "http://mfin.gov.rs/srbdt/srbdtext":
                return "sbt";
            default:
                return suggestion;
        }
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {
                "urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2",
                "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2",
                "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2",
                "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
                "http://mfin.gov.rs/srbdt/srbdtext"
        };
    }


}
