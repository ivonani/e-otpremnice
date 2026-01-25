package org.eotpremnice.xml.builder;

import lombok.NoArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.Stavke;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.eotpremnice.utils.XmlBuilderUtils.notBlank;

@Component
@NoArgsConstructor
public final class DespatchLineBuilder {

    private static final String AKCIZE_KATEGORIJA = "AKCIZE.KATEGORIJA";

    public static List<DespatchLineType> build(List<Stavke> stavke) {
        List<DespatchLineType> out = new ArrayList<>();
        if (stavke == null) return out;

        for (Stavke s : stavke) {
            DespatchLineType line = new DespatchLineType();

            // cbc:ID <- Rbr
            if (s.getRbr() != null) {
                IDType id = new IDType();
                id.setValue(String.valueOf(s.getRbr()));
                line.setID(id);
            }

            // cbc:DeliveredQuantity (@unitCode = JMere) <- Kolicina
            if (s.getKolicina() != null) {
                DeliveredQuantityType q = new DeliveredQuantityType();
                q.setValue(s.getKolicina());
                if (notBlank(s.getJMere())) {
                    q.setUnitCode(s.getJMere().trim());
                }
                line.setDeliveredQuantity(q);
            }

            // Item
            ItemType item = new ItemType();

            // SellersItemIdentification/ID <- SifraArtikla
            if (notBlank(s.getSifraArtikla())) {
                ItemIdentificationType sellerId = new ItemIdentificationType();
                IDType sid = new IDType();
                sid.setValue(s.getSifraArtikla().trim());
                sellerId.setID(sid);
                item.setSellersItemIdentification(sellerId);
            }

            // Name <- NazivArtikla
            if (notBlank(s.getNazivArtikla())) {
                NameType n = new NameType();
                n.setValue(s.getNazivArtikla().trim());
                item.setName(n);
            }

            // Description <- OpisArtikl
            if (notBlank(s.getOpisArtikla())) {
                DescriptionType d = new DescriptionType();
                d.setValue(s.getOpisArtikla().trim());
                item.getDescription().add(d); // Description je lista
            }

            // StandardItemIdentification/ID <- GTIN
            if (notBlank(s.getGtin())) {
                ItemIdentificationType stdId = new ItemIdentificationType();
                IDType gid = new IDType();
                gid.setValue(s.getGtin().trim());
                stdId.setID(gid);
                item.setStandardItemIdentification(stdId);
            }

            // AdditionalItemProperty #1: Name FIXNO, Value <- AKategorija
            if (notBlank(s.getAKategorija())) {
                ItemPropertyType p = new ItemPropertyType();

                NameType pn = new NameType();
                pn.setValue(AKCIZE_KATEGORIJA);
                p.setName(pn);

                ValueType pv = new ValueType();
                pv.setValue(s.getAKategorija().trim());
                p.setValue(pv);

                item.getAdditionalItemProperty().add(p);
            }

            // AdditionalItemProperty #2: Name <- ATipKolicine, Value <- AKolicina
            if (notBlank(s.getATipKolicine()) && s.getAKolicina() != null) {
                ItemPropertyType p = new ItemPropertyType();

                NameType pn = new NameType();
                pn.setValue(s.getATipKolicine().trim());
                p.setName(pn);

                ValueType pv = new ValueType();
                pv.setValue(s.getAKolicina().toPlainString());
                p.setValue(pv);

                item.getAdditionalItemProperty().add(p);
            }

            line.setItem(item);
            out.add(line);
        }
        return out;
    }
}