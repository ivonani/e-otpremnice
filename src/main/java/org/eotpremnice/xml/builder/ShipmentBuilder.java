package org.eotpremnice.xml.builder;

import lombok.NoArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.Isporuka;
import org.eotpremnice.model.Prevoznik;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@NoArgsConstructor
public final class ShipmentBuilder {

    private static final String SCHEME_ID_9948 = "9948";
    private static final String TAX_SCHEME_VAT = "VAT";

    public static ShipmentType build(Isporuka isporuka, Prevoznik prevoznik) {
        if (isporuka == null)
            return null;

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

        if (prevoznik != null) {
            shipment.getShipmentStage().add(buildShipmentStage(prevoznik));
        }

        return shipment;
    }

    private static ShipmentStageType buildShipmentStage(Prevoznik p) {
        ShipmentStageType stage = new ShipmentStageType();
        stage.getCarrierParty().add(buildCarrierParty(p));
        return stage;
    }

    private static PartyType buildCarrierParty(Prevoznik p) {
        PartyType party = new PartyType();

        // cbc:EndpointID (PIB) + @schemeID="9948"
        if (p.getPib() != null) {
            EndpointIDType endpoint = new EndpointIDType();
            endpoint.setSchemeID(SCHEME_ID_9948);
            endpoint.setValue(p.getPib());
            party.setEndpointID(endpoint);
        }

        // cac:PartyIdentification/cbc:ID (JBKJS) + @schemeID="9948"
        if (p.getJbkjs() != null) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType id = new IDType();
            id.setSchemeID(SCHEME_ID_9948);
            id.setValue(p.getJbkjs());
            pid.setID(id);
            party.getPartyIdentification().add(pid);
        }

        // cac:PartyName/cbc:Name (PunNaziv)
        if (p.getPunNaziv() != null) {
            PartyNameType pn = new PartyNameType();
            NameType name = new NameType();
            name.setValue(p.getPunNaziv());
            pn.setName(name);
            party.getPartyName().add(pn);
        }

        // cac:PostalAddress (Adresa, Mesto, ZIP, Drzava=RS)
        party.setPostalAddress(buildAddress(p));

        // cac:PartyTaxScheme (PIB_RS + VAT)
        if (p.getPibRs() != null) {
            PartyTaxSchemeType tax = new PartyTaxSchemeType();

            CompanyIDType companyId = new CompanyIDType();
            companyId.setValue(p.getPibRs());
            tax.setCompanyID(companyId);

            TaxSchemeType scheme = new TaxSchemeType();
            IDType taxId = new IDType();
            taxId.setValue(TAX_SCHEME_VAT);
            scheme.setID(taxId);
            tax.setTaxScheme(scheme);

            party.getPartyTaxScheme().add(tax);
        }

        // cac:PartyLegalEntity (RegistrationName= PunNaziv, CompanyID=MBR)
        PartyLegalEntityType ple = new PartyLegalEntityType();

        if (p.getPunNaziv() != null) {
            RegistrationNameType rn = new RegistrationNameType();
            rn.setValue(p.getPunNaziv());
            ple.setRegistrationName(rn);
        }

        if (p.getMbr() != null) {
            CompanyIDType mbr = new CompanyIDType();
            mbr.setValue(p.getMbr());
            ple.setCompanyID(mbr);
        }

        // Dodaj samo ako ima bar nešto
        if (ple.getRegistrationName() != null || ple.getCompanyID() != null) {
            party.getPartyLegalEntity().add(ple);
        }

        return party;
    }

    private static AddressType buildAddress(Prevoznik p) {
        AddressType addr = new AddressType();

        if (p.getAdresa() != null) {
            StreetNameType street = new StreetNameType();
            street.setValue(p.getAdresa());
            addr.setStreetName(street);
        }

        // AdditionalStreetName i AddressLine se NE ispisuju (ne puniš)

        if (p.getMesto() != null) {
            CityNameType city = new CityNameType();
            city.setValue(p.getMesto());
            addr.setCityName(city);
        }

        if (p.getZip() != null) {
            PostalZoneType postal = new PostalZoneType();
            postal.setValue(p.getZip());
            addr.setPostalZone(postal);
        }

        // Država "RS"
        CountryType country = new CountryType();
        IdentificationCodeType code = new IdentificationCodeType();
        code.setValue(p.getDrzava() != null ? p.getDrzava() : "RS");
        country.setIdentificationCode(code);
        addr.setCountry(country);

        return addr;
    }
}