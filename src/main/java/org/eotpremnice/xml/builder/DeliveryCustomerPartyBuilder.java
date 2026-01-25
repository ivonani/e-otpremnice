package org.eotpremnice.xml.builder;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.Kupac;
import org.springframework.stereotype.Component;

@Component
public class DeliveryCustomerPartyBuilder {

    public static CustomerPartyType build(Kupac kupac) {

        // === Party ===
        PartyType party = new PartyType();

        // EndpointID (schemeID="9948") : PIB
        if (kupac.getPib() != null) {
            EndpointIDType endpointID = new EndpointIDType();
            endpointID.setValue(kupac.getPib());
            endpointID.setSchemeID("9948");
            party.setEndpointID(endpointID);
        }

        // PartyIdentification (schemeID="JBKJS") : JBKJS
        if (kupac.getJbkjs() != null) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType id = new IDType();
            id.setValue(kupac.getJbkjs());
            id.setSchemeID("9948");
            pid.setID(id);
            party.getPartyIdentification().add(pid);
        }

        // PartyName : PunNaziv
        if (kupac.getPunNaziv() != null) {
            PartyNameType partyName = new PartyNameType();
            NameType name = new NameType();
            name.setValue(kupac.getPunNaziv());
            partyName.setName(name);
            party.getPartyName().add(partyName);
        }

        // PostalAddress
        AddressType address = new AddressType();

        if (kupac.getAdresa() != null) {
            StreetNameType streetName = new StreetNameType();
            streetName.setValue(kupac.getAdresa());
            address.setStreetName(streetName);
        }

        if (kupac.getMesto() != null) {
            CityNameType city = new CityNameType();
            city.setValue(kupac.getMesto());
            address.setCityName(city);
        }

        if (kupac.getZip() != null) {
            PostalZoneType postalZone = new PostalZoneType();
            postalZone.setValue(kupac.getZip());
            address.setPostalZone(postalZone);
        }

        // Country/IdentificationCode (RS)
        CountryType country = new CountryType();
        IdentificationCodeType code = new IdentificationCodeType();
        code.setValue("RS"); // po tabeli fiksno
        country.setIdentificationCode(code);
        address.setCountry(country);

        party.setPostalAddress(address);

        // PartyTaxScheme: CompanyID = PIB_RS, TaxScheme ID = VAT
        if (kupac.getPibRs() != null) {
            PartyTaxSchemeType tax = new PartyTaxSchemeType();

            CompanyIDType companyID = new CompanyIDType();
            companyID.setValue(kupac.getPibRs());
            tax.setCompanyID(companyID);

            TaxSchemeType scheme = new TaxSchemeType();
            IDType taxId = new IDType();
            taxId.setValue("VAT");
            scheme.setID(taxId);

            tax.setTaxScheme(scheme);
            party.getPartyTaxScheme().add(tax);
        }

        // PartyLegalEntity: RegistrationName = PunNaziv, CompanyID = MBR
        PartyLegalEntityType legal = new PartyLegalEntityType();

        if (kupac.getPunNaziv() != null) {
            RegistrationNameType regName = new RegistrationNameType();
            regName.setValue(kupac.getPunNaziv());
            legal.setRegistrationName(regName);
        }

        if (kupac.getMbr() != null) {
            CompanyIDType companyId = new CompanyIDType();
            companyId.setValue(kupac.getMbr());
            legal.setCompanyID(companyId);
        }

        party.getPartyLegalEntity().add(legal);

        // === CustomerParty wrapper ===
        CustomerPartyType customer = new CustomerPartyType();
        customer.setParty(party);
        return customer;
    }
}
