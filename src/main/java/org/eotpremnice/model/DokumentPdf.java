package org.eotpremnice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DokumentPdf {

    private String idPdf;
    private String opisPdf;
    private String fileName;
    private String mimeCode;
}
