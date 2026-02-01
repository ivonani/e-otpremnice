package org.eotpremnice.xml.builder;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.applicationresponse_2.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import org.eotpremnice.model.PromenaStatusa;
import org.eotpremnice.utils.XmlDates;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationResponseXmlBuilder {

    private static final String CUSTOMIZATION_ID = "urn:fdc:mfin.gov.rs:logistics:trns:application_response:1:2025.12";
    private static final String PROFILE_ID = "application:response";
    private static final String SCHEME_9948 = "9948";

    public ApplicationResponseType builder(PromenaStatusa promenaStatusa) throws Exception {

        ApplicationResponseType response = new ApplicationResponseType();
        CustomizationIDType customizationIDType = new CustomizationIDType();
        customizationIDType.setValue(CUSTOMIZATION_ID);
        response.setCustomizationID(customizationIDType);

        ProfileIDType profileIDType = new ProfileIDType();
        profileIDType.setValue(PROFILE_ID);
        response.setProfileID(profileIDType);

        IDType idType = new IDType();
        idType.setValue(promenaStatusa.getOznakaPromene());
        response.setID(idType);

        IssueDateType issueDateType = new IssueDateType();
        issueDateType.setValue(XmlDates.date(promenaStatusa.getDatumPromene()));
        response.setIssueDate(issueDateType);

        if (promenaStatusa.getRazlogPromene() != null) {
            NoteType noteType = new NoteType();
            noteType.setValue(promenaStatusa.getRazlogPromene());
            response.getNote().add(noteType);
        }

        PartyType senderParty = new PartyType();
        EndpointIDType endpointID1 = new EndpointIDType();
        endpointID1.setValue(promenaStatusa.getPibDobavljac());
        endpointID1.setSchemeID(SCHEME_9948);
        senderParty.setEndpointID(endpointID1);
        response.setSenderParty(senderParty);

        PartyType receiverParty = new PartyType();
        EndpointIDType endpointID2 = new EndpointIDType();
        endpointID2.setValue(promenaStatusa.getPibKupac());
        endpointID2.setSchemeID(SCHEME_9948);
        receiverParty.setEndpointID(endpointID2);
        response.setSenderParty(receiverParty);

        DocumentResponseType documentResponseType = new DocumentResponseType();
        ResponseType responseType = new ResponseType();

        ResponseCodeType responseCodeType = new ResponseCodeType();
        responseCodeType.setValue(String.valueOf(promenaStatusa.getKodPromene()));
        responseType.setResponseCode(responseCodeType);
        documentResponseType.setResponse(responseType);

        DocumentReferenceType documentReferenceType = new DocumentReferenceType();
        IDType idType1 = new IDType();
        idType1.setValue(promenaStatusa.getBrDok());
        documentReferenceType.setID(idType1);

        IssueDateType issueDateType1 = new IssueDateType();
        issueDateType1.setValue(XmlDates.date(promenaStatusa.getDatumIzdavanja()));
        documentReferenceType.setIssueDate(issueDateType1);

        PartyType issuerParty = new PartyType();
        EndpointIDType endpointID3 = new EndpointIDType();
        endpointID3.setValue(promenaStatusa.getPibDobavljac());
        endpointID3.setSchemeID(SCHEME_9948);
        issuerParty.setEndpointID(endpointID3);
        response.setSenderParty(issuerParty);
        documentReferenceType.setIssuerParty(issuerParty);
        documentResponseType.getDocumentReference().add(documentReferenceType);

        response.getDocumentResponse().add(documentResponseType);
        return response;
    }
}