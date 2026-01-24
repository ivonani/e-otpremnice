package org.eotpremnice.xml.builder;

import lombok.NoArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ShipmentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.Isporuka;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@NoArgsConstructor
public final class ShipmentBuilder {

    public static ShipmentType build(Isporuka isporuka) {
        if (isporuka == null) return null;

        ShipmentType shipment = new ShipmentType();

        // cbc:ID
        if (isporuka.getBroj() != null) {
            IDType id = new IDType();
            id.setValue(isporuka.getBroj());
            shipment.setID(id);
        }

        // cbc:GrossWeightMeasure @unitCode
        if (isporuka.getTezina() != null) {
            GrossWeightMeasureType gw = new GrossWeightMeasureType();
            gw.setValue(isporuka.getTezina());
            if (isporuka.getTezinaJM() != null) {
                gw.setUnitCode(isporuka.getTezinaJM()); // "KGM" ili "LTN"
            }
            shipment.setGrossWeightMeasure(gw);
        }

        // cbc:GrossVolumeMeasure @unitCode
        if (isporuka.getZapremina() != null) {
            GrossVolumeMeasureType gv = new GrossVolumeMeasureType();
            gv.setValue(isporuka.getZapremina());
            if (isporuka.getZapreminaJM() != null) {
                gv.setUnitCode(isporuka.getZapreminaJM()); // "MTQ"
            }
            shipment.setGrossVolumeMeasure(gv);
        }

        // cbc:TotalTransportHandlingUnitQuantity
        if (isporuka.getBrPaketa() != null) {
            TotalTransportHandlingUnitQuantityType qty = new TotalTransportHandlingUnitQuantityType();
            // u UBL JAXB obično je BigDecimal
            qty.setValue(BigDecimal.valueOf(isporuka.getBrPaketa().longValue()));
            shipment.setTotalTransportHandlingUnitQuantity(qty);
        }

        // cbc:DeliveryInstructions
        if (isporuka.getNapIsporuke() != null) {
            DeliveryInstructionsType di = new DeliveryInstructionsType();
            di.setValue(isporuka.getNapIsporuke());
            shipment.getDeliveryInstructions().add(di);
        }

        return shipment;
    }
}