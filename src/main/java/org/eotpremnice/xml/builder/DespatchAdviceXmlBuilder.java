package org.eotpremnice.xml.builder;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.OrderReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;


import org.eotpremnice.model.DokumentPdf;
import org.eotpremnice.model.Otpremnice;
import org.eotpremnice.service.DokumentPdfService;
import org.eotpremnice.service.OtpremniceService;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DespatchAdviceXmlBuilder {

    private static final String CUSTOMIZATION_ID = "urn:fdc:peppol.eu:logistics:trns:advanced_despatch_advice:1";
    private static final String PROFILE_ID       = "urn:fdc:peppol.eu:logistics:bis:despatch_advice_only:1";

    private final OtpremniceService otpremniceService;
    private final DokumentPdfService dokumentPdfService;

    public DespatchAdviceType builder(String idFirme, String tipDokumenta, Long iddok) throws Exception {

        DespatchAdviceType advice = new DespatchAdviceType();
        Otpremnice otpremnice = otpremniceService.loadOtpremnice(idFirme, tipDokumenta, iddok);
        advice.setUBLExtensions(UBLExtensionBuilder.build(otpremnice));

        advice.setCustomizationID(cbcCustomizationId(CUSTOMIZATION_ID));

        // 3) ProfileID (FIXNO)
        advice.setProfileID(cbcProfileId(PROFILE_ID));

        // 4) ID <- BrDok
        advice.setID(cbcId(required(otpremnice.getBrDok(), "BrDok")));

        // 5) IssueDate <- DatumIzdavanja
        advice.setIssueDate(cbcIssueDate(required(otpremnice.getDatumIzdavanja(), "DatumIzdavanja")));

        // 6) DespatchAdviceTypeCode <- TipOtpremnice
        if (notBlank(otpremnice.getTipOtpremnice())) {
            advice.setDespatchAdviceTypeCode(cbcDespatchAdviceTypeCode(otpremnice.getTipOtpremnice()));
        }

        // 7) Note <- NapOpsta (0..n)
        if (notBlank(otpremnice.getNapOpsta())) {
            advice.getNote().add(cbcNote(otpremnice.getNapOpsta()));
        }

        // 8) OrderReference/cbc:ID <- IDNarudzbenice
        if (notBlank(otpremnice.getIdNarudzbenice())) {
            OrderReferenceType orderRef = new OrderReferenceType();
            orderRef.setID(cbcId(otpremnice.getIdNarudzbenice()));
            advice.getOrderReference().add(orderRef);
        }

        List<DokumentPdf> dokumentPdfs = dokumentPdfService.loadPdfPrilozi(idFirme, tipDokumenta, iddok);
        if(!dokumentPdfs.isEmpty()) {
            advice.getAdditionalDocumentReference().addAll(
                    AdditionalDocumentReferenceBuilder.build(dokumentPdfs)
            );
        }
        return advice;

    }

    // -----------------------
    // Helper CBC builders
    // -----------------------

    private static CustomizationIDType cbcCustomizationId(String value) {
        CustomizationIDType t = new CustomizationIDType();
        t.setValue(value);
        return t;
    }

    private static ProfileIDType cbcProfileId(String value) {
        ProfileIDType t = new ProfileIDType();
        t.setValue(value);
        return t;
    }

    private static IDType cbcId(String value) {
        IDType t = new IDType();
        t.setValue(value);
        return t;
    }

    private static DespatchAdviceTypeCodeType cbcDespatchAdviceTypeCode(String value) {
        DespatchAdviceTypeCodeType t = new DespatchAdviceTypeCodeType();
        t.setValue(value);
        return t;
    }

    private static NoteType cbcNote(String value) {
        NoteType t = new NoteType();
        t.setValue(value);
        return t;
    }

    private static IssueDateType cbcIssueDate(LocalDate date) throws Exception {
        IssueDateType t = new IssueDateType();
        t.setValue(toXmlDate(date));
        return t;
    }

    public static XMLGregorianCalendar toXmlDate(LocalDate date) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendarDate(
                        date.getYear(),
                        date.getMonthValue(),
                        date.getDayOfMonth(),
                        DatatypeConstants.FIELD_UNDEFINED // ← NO timezone
                );
    }

    // -----------------------
    // small helpers
    // -----------------------

    private static boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private static <T> T required(T v, String field) {
        if (v == null) throw new IllegalArgumentException("Missing required field: " + field);
        return v;
    }

    private static String required(String v, String field) {
        if (!notBlank(v)) throw new IllegalArgumentException("Missing required field: " + field);
        return v.trim();
    }
}
