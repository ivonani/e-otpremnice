package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.DokumentPDFEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "DokumentPDF")
public class DokumentPDFEntity implements Serializable {

    @EmbeddedId
    private DokumentPDFEntityId dokumentPDFEntityId;

    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDPDF")
    @Convert(converter = StringTrimConverter.class)
    private String idPdf;

    @Column(name = "OpisPDF")
    @Convert(converter = StringTrimConverter.class)
    private String opisPdf;

    @Column(name = "FolderPDF")
    @Convert(converter = StringTrimConverter.class)
    private String folderPdf;

    @Column(name = "NazivPDF")
    @Convert(converter = StringTrimConverter.class)
    private String nazivPdf;

    @Column(name = "FileName")
    @Convert(converter = StringTrimConverter.class)
    private String fileName;

    @Column(name = "EncodingCode")
    @Convert(converter = StringTrimConverter.class)
    private String encodingCode;

    @Column(name = "MimeCode")
    @Convert(converter = StringTrimConverter.class)
    private String mimeCode;
}
