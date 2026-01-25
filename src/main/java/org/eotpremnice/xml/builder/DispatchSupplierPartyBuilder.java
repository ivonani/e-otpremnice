package org.eotpremnice.xml.builder;

import org.eotpremnice.model.Posiljalac;
import org.springframework.stereotype.Component;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;

import static org.eotpremnice.utils.XmlBuilderUtils.notBlank;

@Component
public class DispatchSupplierPartyBuilder {

    private static final String SCHEME_9948 = "9948";

    public static SupplierPartyType build(Posiljalac posiljalac) {
        SupplierPartyType supplierParty = new SupplierPartyType();
        PartyType party = new PartyType();

        // 1) EndpointID (PIB) + schemeID="9948"
        if (notBlank(posiljalac.getPib())) {
            EndpointIDType endpointID = new EndpointIDType();
            endpointID.setValue(posiljalac.getPib());
            endpointID.setSchemeID(SCHEME_9948);
            party.setEndpointID(endpointID);
        }

        // 2) PartyIdentification (JBKJS) + schemeID="JBKJS"
        if (notBlank(posiljalac.getJbkjs())) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType id = new IDType();
            id.setValue(posiljalac.getJbkjs());
            id.setSchemeID("JBKJS");
            pid.setID(id);
            party.getPartyIdentification().add(pid);
        }

        // 3) PartyName/Name (PunNaziv)
        if (notBlank(posiljalac.getPunNaziv())) {
            PartyNameType pn = new PartyNameType();
            NameType name = new NameType();
            name.setValue(posiljalac.getPunNaziv());
            pn.setName(name);
            party.getPartyName().add(pn);
        }

        // 4) PostalAddress
        AddressType addr = new AddressType();

        if (notBlank(posiljalac.getAdresa())) {
            StreetNameType street = new StreetNameType();
            street.setValue(posiljalac.getAdresa());
            addr.setStreetName(street);
        }

        // AdditionalStreetName / AddressLine -> "NE ISPISUJE SE" => ne dodajemo

        if (notBlank(posiljalac.getMesto())) {
            CityNameType city = new CityNameType();
            city.setValue(posiljalac.getMesto());
            addr.setCityName(city);
        }

        if (notBlank(posiljalac.getZip())) {
            PostalZoneType postal = new PostalZoneType();
            postal.setValue(posiljalac.getZip());
            addr.setPostalZone(postal);
        }

        // Country/IdentificationCode (na slici RS fiksno)
        CountryType country = new CountryType();
        IdentificationCodeType code = new IdentificationCodeType();
        code.setValue(notBlank(posiljalac.getDrzava()) ? posiljalac.getDrzava() : "RS");
        country.setIdentificationCode(code);
        addr.setCountry(country);

        // set address samo ako bar nešto ima
        party.setPostalAddress(addr);

        // 5) PartyTaxScheme: CompanyID = PIB_RS ; TaxScheme/ID = "VAT"
        if (notBlank(posiljalac.getPibRs())) {
            PartyTaxSchemeType tax = new PartyTaxSchemeType();

            CompanyIDType companyID = new CompanyIDType();
            companyID.setValue(posiljalac.getPibRs());
            tax.setCompanyID(companyID);

            TaxSchemeType taxScheme = new TaxSchemeType();
            IDType taxId = new IDType();
            taxId.setValue("VAT");
            taxScheme.setID(taxId);
            tax.setTaxScheme(taxScheme);

            party.getPartyTaxScheme().add(tax);
        }

        // 6) PartyLegalEntity: RegistrationName = PunNaziv ; CompanyID = MBR
        PartyLegalEntityType le = new PartyLegalEntityType();

        if (notBlank(posiljalac.getPunNaziv())) {
            RegistrationNameType rn = new RegistrationNameType();
            rn.setValue(posiljalac.getPunNaziv());
            le.setRegistrationName(rn);
        }

        if (notBlank(posiljalac.getMbr())) {
            CompanyIDType companyId = new CompanyIDType();
            companyId.setValue(posiljalac.getMbr());
            le.setCompanyID(companyId);
        }

        // dodajemo samo ako ima bar nešto
        party.getPartyLegalEntity().add(le);

        supplierParty.setParty(party);
        return supplierParty;
    }

}
