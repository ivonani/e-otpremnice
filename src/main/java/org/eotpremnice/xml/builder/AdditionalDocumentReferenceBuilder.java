package org.eotpremnice.xml.builder;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import org.eotpremnice.model.DokumentPdf;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class AdditionalDocumentReferenceBuilder {

    public static List<DocumentReferenceType> build(List<DokumentPdf> prilozi) {
        List<DocumentReferenceType> out = new ArrayList<>();
        if (prilozi == null) return out;

        for (DokumentPdf p : prilozi) {
            DocumentReferenceType docRef = new DocumentReferenceType();

            IDType id = new IDType();
            id.setValue(p.getIdPdf());
            docRef.setID(id);

            if (p.getOpisPdf() != null && !p.getOpisPdf().isEmpty()) {
                DocumentDescriptionType desc = new DocumentDescriptionType();
                desc.setValue(p.getOpisPdf());
                docRef.getDocumentDescription().add(desc);
            }

            out.add(docRef);
        }

        return out;
    }
}
