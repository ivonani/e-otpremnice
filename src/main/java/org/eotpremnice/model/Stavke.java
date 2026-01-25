package org.eotpremnice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Stavke {

    private Integer rbr;
    private BigDecimal kolicina;
    private String jMere;

    private String sifraArtikla;
    private String nazivArtikla;
    private String opisArtikla;
    private String gtin;

    private String aKategorija;
    private String aTipKolicine;
    private BigDecimal aKolicina;
}