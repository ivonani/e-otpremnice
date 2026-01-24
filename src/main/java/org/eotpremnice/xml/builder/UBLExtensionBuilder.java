package org.eotpremnice.xml.builder;

import org.eotpremnice.model.Otpremnice;
import org.eotpremnice.utils.DomHelper;
import org.springframework.stereotype.Component;

import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContentType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


@Component
public class UBLExtensionBuilder {

    private static final String NS_SBT = "http://mfin.gov.rs/srbdt/srbdtext";
    private static final String NS_CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
    private static final String NS_CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";


    public static UBLExtensionsType build(Otpremnice dto) throws Exception {

        Document doc = DomHelper.newDocument();
        Element srbDtExt = DomHelper.el(doc, NS_SBT, "sbt", "SrbDtExt");

        // sbt:ShipmentMethod/cbc:ShipmentMethodType
        if (dto.getNacinOtpreme() != null) {
            Element shipmentMethod = DomHelper.el(doc, NS_SBT, "sbt", "ShipmentMethod");
            shipmentMethod.appendChild(DomHelper.elText(doc, NS_CBC, "cbc", "ShipmentMethodType", dto.getNacinOtpreme()));
            srbDtExt.appendChild(shipmentMethod);
        }

        // sbt:GoodsReturn/cbc:Return
        if (dto.getPovratnica() != null) {
            Element goodsReturn = DomHelper.el(doc, NS_SBT, "sbt", "GoodsReturn");
            goodsReturn.appendChild(DomHelper.elText(doc, NS_CBC, "cbc", "Return", dto.getPovratnica()));
            srbDtExt.appendChild(goodsReturn);
        }

        // sbt:HazardousGoods
        if (dto.getIdUgovora() != null) {
            Element extDocuments = DomHelper.el(doc, NS_SBT, "sbt", "ExtDocuments");
            Element contractDocumentReference = DomHelper.el(doc, NS_CAC, "cac", "ContractDocumentReference");
            extDocuments.appendChild(contractDocumentReference);
            contractDocumentReference.appendChild(DomHelper.elText(doc, NS_CBC, "cbc", "ID", dto.getIdUgovora()));
            srbDtExt.appendChild(extDocuments);
        }

        // 2) Ubaci to u UBLExtensions JAXB strukturu
        ExtensionContentType content = new ExtensionContentType();
        content.setAny(srbDtExt);

        UBLExtensionType ext = new UBLExtensionType();
        ext.setExtensionContent(content);

        UBLExtensionsType exts = new UBLExtensionsType();
        exts.getUBLExtension().add(ext);
        return exts;
    }
}
