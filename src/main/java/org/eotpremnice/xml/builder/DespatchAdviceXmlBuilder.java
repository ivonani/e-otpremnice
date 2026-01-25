package org.eotpremnice.xml.builder;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.OrderReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;


import org.eotpremnice.model.*;
import org.eotpremnice.service.*;
import org.eotpremnice.utils.XmlDates;
import org.springframework.stereotype.Component;
import java.util.List;

import static org.eotpremnice.utils.XmlBuilderUtils.notBlank;

@Component
@RequiredArgsConstructor
public class DespatchAdviceXmlBuilder {

    private static final String CUSTOMIZATION_ID = "urn:fdc:peppol.eu:logistics:trns:advanced_despatch_advice:1";
    private static final String PROFILE_ID       = "urn:fdc:peppol.eu:logistics:bis:despatch_advice_only:1";

    private final OtpremniceService otpremniceService;
    private final DokumentPdfService dokumentPdfService;
    private final FirmaService firmaService;
    private final KupacService kupacService;
    private final IsporukaService isporukaService;
    private final PrevoznikService prevoznikService;
    private final VozacService vozacService;
    private final KurirService kurirService;
    private final OdredisteService odredisteService;
    private final MagacinService magacinService;
    private final StavkeService stavkeService;

    public DespatchAdviceType builder(String idFirme, String tipDokumenta, Long iddok) throws Exception {

        DespatchAdviceType advice = new DespatchAdviceType();
        Otpremnice otpremnice = otpremniceService.loadOtpremnice(idFirme, tipDokumenta, iddok);
        advice.setUBLExtensions(UBLExtensionBuilder.build(otpremnice));

        CustomizationIDType customizationIDType = new CustomizationIDType();
        customizationIDType.setValue(CUSTOMIZATION_ID);
        advice.setCustomizationID(customizationIDType);

        ProfileIDType profileIDType = new ProfileIDType();
        profileIDType.setValue(PROFILE_ID);
        advice.setProfileID(profileIDType);

        advice.setID(cbcId(otpremnice.getBrDok()));

        IssueDateType issueDateType = new IssueDateType();
        issueDateType.setValue(XmlDates.date(otpremnice.getDatumIzdavanja()));
        advice.setIssueDate(issueDateType);

        // 6) DespatchAdviceTypeCode <- TipOtpremnice
        if (notBlank(otpremnice.getTipOtpremnice())) {
            DespatchAdviceTypeCodeType despatchAdviceTypeCodeType = new DespatchAdviceTypeCodeType();
            despatchAdviceTypeCodeType.setValue(otpremnice.getTipOtpremnice());
            advice.setDespatchAdviceTypeCode(despatchAdviceTypeCodeType);
        }

        // 7) Note <- NapOpsta (0..n)
        if (notBlank(otpremnice.getNapOpsta())) {
            NoteType noteType = new NoteType();
            noteType.setValue(otpremnice.getNapOpsta());
            advice.getNote().add(noteType);
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

        Posiljalac posiljalac = firmaService.loadPosiljalac(idFirme, tipDokumenta);
        advice.setDespatchSupplierParty(DispatchSupplierPartyBuilder.build(posiljalac));

        Kupac kupac = kupacService.loadKupac(idFirme, tipDokumenta, iddok);
        advice.setDeliveryCustomerParty(DeliveryCustomerPartyBuilder.build(kupac));

        Isporuka isporuka = isporukaService.loadIsporuka(idFirme, tipDokumenta, iddok);
        Prevoznik prevoznik = prevoznikService.loadPrevoznik(idFirme, tipDokumenta, iddok);
        Vozac vozac = vozacService.loadVozac(idFirme, tipDokumenta, iddok);
        Kurir kurir = kurirService.loadKurir(idFirme, tipDokumenta, iddok);
        Odrediste odrediste = odredisteService.loadOdrediste(idFirme, tipDokumenta, iddok);
        Magacin magacin = magacinService.loadMagacin(idFirme, tipDokumenta, iddok);
        advice.setShipment(ShipmentBuilder.build(isporuka, prevoznik, vozac, kurir,
                odrediste, otpremnice, magacin));

        List<Stavke> stavke = stavkeService.loadStavke(idFirme, tipDokumenta, iddok);
        if (!stavke.isEmpty()) {
            advice.getDespatchLine().addAll(DespatchLineBuilder.build(stavke));
        }
        return advice;

    }

    private static IDType cbcId(String value) {
        IDType t = new IDType();
        t.setValue(value);
        return t;
    }
}
