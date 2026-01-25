package org.eotpremnice.xml.builder;

import lombok.NoArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.*;
import org.eotpremnice.utils.XmlDates;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@NoArgsConstructor
public final class ShipmentBuilder {

    private static final String SCHEME_ID_9948 = "9948";
    private static final String TAX_SCHEME_VAT = "VAT";

    public static ShipmentType build(
            Isporuka isporuka, Prevoznik prevoznik,
            Vozac vozac, Kurir kurir,
            Odrediste odrediste,
            Otpremnice otpremnice,
            Magacin magacin) {
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

        ShipmentStageType stage = new ShipmentStageType();
        if (prevoznik != null) {
            stage.getCarrierParty().add(buildCarrierParty(prevoznik));
            shipment.getShipmentStage().add(stage);
        }

        // DriverPerson (vozač)
        if (vozac != null) {
            stage.getDriverPerson().add(buildDriverPerson(vozac));
        }

        // MasterPerson (kurir)
        if (kurir != null) {
            stage.setMasterPerson(buildMasterPerson(kurir));
        }

        // TransportMeans / RoadTransport / LicensePlateID (regBroj)
        if (vozac != null && vozac.getRegBroj() != null) {
            stage.setTransportMeans(buildTransportMeans(vozac.getRegBroj()));
        }

        shipment.getShipmentStage().add(stage);

        DeliveryType delivery = new DeliveryType();

        // 1) Odredište: DeliveryAddress
        if (odrediste != null) {
            AddressType addr = new AddressType();

            if (odrediste.getAdresa() != null) {
                StreetNameType street = new StreetNameType();
                street.setValue(odrediste.getAdresa());
                addr.setStreetName(street);
            }

            if (odrediste.getMesto() != null) {
                CityNameType city = new CityNameType();
                city.setValue(odrediste.getMesto());
                addr.setCityName(city);
            }

            if (odrediste.getZip() != null) {
                PostalZoneType pz = new PostalZoneType();
                pz.setValue(odrediste.getZip());
                addr.setPostalZone(pz);
            }

            // Country/IdentificationCode = RS (ili iz baze)
            if (odrediste.getDrzava() != null) {
                CountryType c = new CountryType();
                IdentificationCodeType code = new IdentificationCodeType();
                code.setValue(odrediste.getDrzava());
                c.setIdentificationCode(code);
                addr.setCountry(c);
            }

            delivery.setDeliveryAddress(addr);
        }

        // 2) Odredište: EstimatedDeliveryPeriod EndDate/EndTime
        if (otpremnice.getDatumKraj() != null || otpremnice.getVremeKraj() != null) {
            PeriodType p = new PeriodType();

            if (otpremnice.getDatumKraj() != null) {
                EndDateType ed = new EndDateType();
                ed.setValue(XmlDates.date(otpremnice.getDatumKraj()));
                p.setEndDate(ed);
            }
            if (otpremnice.getVremeKraj() != null) {
                EndTimeType et = new EndTimeType();
                et.setValue(XmlDates.time(otpremnice.getVremeKraj()));
                p.setEndTime(et);
            }

            delivery.setEstimatedDeliveryPeriod(p);
        }

        // 3) Mesto slanja: Despatch/ActualDespatchDate/Time (Despatch je LIST)
        if (otpremnice.getDatumOtpreme() != null || otpremnice.getVremeOtpreme() != null) {
            DespatchType despatch = new DespatchType();

            if (otpremnice.getDatumOtpreme() != null) {
                ActualDespatchDateType d = new ActualDespatchDateType();
                d.setValue(XmlDates.date(otpremnice.getDatumOtpreme()));
                despatch.setActualDespatchDate(d);
            }
            if (otpremnice.getVremeOtpreme() != null) {
                ActualDespatchTimeType t = new ActualDespatchTimeType();
                t.setValue(XmlDates.time(otpremnice.getVremeOtpreme()));
                despatch.setActualDespatchTime(t);
            }

            delivery.setDespatch(despatch);
        }

        shipment.setDelivery(delivery);

        DeliveryType deliveryMagacin = new DeliveryType();
        DespatchType despatch = new DespatchType();

        AddressType despatchAddress = new AddressType();
        if (magacin != null) {
            if (notBlank(magacin.getAdresa())) {
                StreetNameType s = new StreetNameType();
                s.setValue(magacin.getAdresa());
                despatchAddress.setStreetName(s);
            }

            if (notBlank(magacin.getMesto())) {
                CityNameType c = new CityNameType();
                c.setValue(magacin.getMesto());
                despatchAddress.setCityName(c);
            }

            if (notBlank(magacin.getZip())) {
                PostalZoneType p = new PostalZoneType();
                p.setValue(magacin.getZip());
                despatchAddress.setPostalZone(p);
            }

            if (notBlank(magacin.getDrzava())) {
                CountryType country = new CountryType();
                IdentificationCodeType code = new IdentificationCodeType();
                code.setValue(magacin.getDrzava());
                country.setIdentificationCode(code);
                despatchAddress.setCountry(country);
            }
        }

        despatch.setDespatchAddress(despatchAddress);
        deliveryMagacin.setDespatch(despatch);

        shipment.setDelivery(deliveryMagacin);

        return shipment;
    }

    private static PersonType buildDriverPerson(Vozac v) {
        PersonType p = new PersonType();

        if (v.getIdVozac() != null) {
            IDType id = new IDType();
            id.setValue(v.getIdVozac());
            p.setID(id);
        }

        if (v.getIme() != null) {
            FirstNameType fn = new FirstNameType();
            fn.setValue(v.getIme());
            p.setFirstName(fn);
        }

        if (v.getPrezime() != null) {
            FamilyNameType ln = new FamilyNameType();
            ln.setValue(v.getPrezime());
            p.setFamilyName(ln);
        }

        // Vozacka dozvola: IdentityDocumentReference
        if (v.getBrojDozvole() != null) {
            DocumentReferenceType doc = new DocumentReferenceType();

            IDType docId = new IDType();
            docId.setValue(v.getBrojDozvole());
            doc.setID(docId);

            DocumentTypeType docType = new DocumentTypeType();
            docType.setValue("Vozačka dozvola");
            doc.setDocumentType(docType);

            p.getIdentityDocumentReference().add(doc);
        }

        // Email: Contact/ElectronicMail
        if (v.getEmail() != null) {
            ContactType c = new ContactType();
            ElectronicMailType mail = new ElectronicMailType();
            mail.setValue(v.getEmail());
            c.setElectronicMail(mail);
            p.setContact(c);
        }

        // Telefon NE popunjavaš (po specifikaciji)

        return p;
    }

    private static PersonType buildMasterPerson(Kurir k) {
        PersonType p = new PersonType();

        if (k.getIme() != null) {
            FirstNameType fn = new FirstNameType();
            fn.setValue(k.getIme());
            p.setFirstName(fn);
        }

        if (k.getPrezime() != null) {
            FamilyNameType ln = new FamilyNameType();
            ln.setValue(k.getPrezime());
            p.setFamilyName(ln);
        }

        // Licna karta: IdentityDocumentReference
        if (k.getBrLk() != null) {
            DocumentReferenceType doc = new DocumentReferenceType();

            IDType docId = new IDType();
            docId.setValue(k.getBrLk());
            doc.setID(docId);

            DocumentTypeType docType = new DocumentTypeType();
            docType.setValue("Lična karta");
            doc.setDocumentType(docType);

            p.getIdentityDocumentReference().add(doc);
        }

        return p;
    }

    private static TransportMeansType buildTransportMeans(String licensePlate) {
        TransportMeansType tm = new TransportMeansType();

        RoadTransportType road = new RoadTransportType();
        LicensePlateIDType lp = new LicensePlateIDType();
        lp.setValue(licensePlate);
        road.setLicensePlateID(lp);

        tm.setRoadTransport(road);
        return tm;
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

    private static boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}