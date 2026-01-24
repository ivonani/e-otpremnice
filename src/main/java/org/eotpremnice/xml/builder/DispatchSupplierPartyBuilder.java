package org.eotpremnice.xml.builder;

import org.eotpremnice.model.Posiljalac;
import org.springframework.stereotype.Component;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;

@Component
public class DispatchSupplierPartyBuilder {

    private static final String SCHEME_9948 = "9948";

    public static SupplierPartyType build(Posiljalac p) {
//        require(p.getPib(), "Pošiljalac.PIB");
//        require(p.getPunNaziv(), "Pošiljalac.Naziv");
//        require(p.getAdresa(), "Pošiljalac.Adresa");
//        require(p.getMesto(), "Pošiljalac.Mesto");
//        require(p.getPibRs(), "Pošiljalac.PIB_RS");
//        require(p.getMbr(), "Pošiljalac.MBR");

        SupplierPartyType supplier = new SupplierPartyType();

        PartyType party = new PartyType();

        // EndpointID
        EndpointIDType endpoint = new EndpointIDType();
        endpoint.setSchemeID(SCHEME_9948);
        endpoint.setValue("p.getPib()");

        party.setEndpointID(endpoint);

        // optional PartyIdentification for JBKJS
//        if (notBlank(p.getJbkjs())) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType id = new IDType();
            id.setSchemeID(SCHEME_9948);
            id.setValue("JBKJS:" + "p.getJbkjs()");
            pid.setID(id);
            party.getPartyIdentification().add(pid);
//        }

        // PartyName
        PartyNameType pn = new PartyNameType();
        NameType name = new NameType();
        name.setValue("p.getPunNaziv()");
        pn.setName(name);
        party.getPartyName().add(pn);

        // Address
        AddressType addr = new AddressType();

        StreetNameType street = new StreetNameType();
        street.setValue("p.getAdresa()");
        addr.setStreetName(street);

        CityNameType city = new CityNameType();
        city.setValue("p.getMesto()");
        addr.setCityName(city);

//        if (notBlank(p.getZip())) {
            PostalZoneType postal = new PostalZoneType();
            postal.setValue("p.getZip()");
            addr.setPostalZone(postal);
//        }

        CountryType country = new CountryType();
        IdentificationCodeType countryCode = new IdentificationCodeType();
        countryCode.setValue(notBlank("p.getDrzava()") ? "p.getDrzava()" : "RS");
        country.setIdentificationCode(countryCode);
        addr.setCountry(country);

        party.setPostalAddress(addr);

        // Tax scheme
        PartyTaxSchemeType pts = new PartyTaxSchemeType();
        CompanyIDType companyId = new CompanyIDType();
        companyId.setValue("p.getPibRs()");
        pts.setCompanyID(companyId);

        TaxSchemeType taxScheme = new TaxSchemeType();
        IDType taxId = new IDType();
        taxId.setValue("VAT");
        taxScheme.setID(taxId);
        pts.setTaxScheme(taxScheme);

        party.getPartyTaxScheme().add(pts);

        // Legal entity
        PartyLegalEntityType ple = new PartyLegalEntityType();

        RegistrationNameType regName = new RegistrationNameType();
        regName.setValue("p.getPunNaziv()");
        ple.setRegistrationName(regName);

        CompanyIDType mbr = new CompanyIDType();
        mbr.setValue("p.getMbr()");
        ple.setCompanyID(mbr);

        party.getPartyLegalEntity().add(ple);

        supplier.setParty(party);
        return supplier;
    }

    private static boolean notBlank(String s) { return s != null && !s.trim().isEmpty(); }
    private static void require(String v, String name) {
        if (!notBlank(v)) throw new IllegalArgumentException("Missing mandatory field: " + name);
    }

}
