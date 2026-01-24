package org.eotpremnice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Kupac {

    private String pib;
    private String jbkjs;
    private String punNaziv;
    private String adresa;
    private String mesto;
    private String zip;       // optional
    private String drzava;    // default RS
    private String pibRs;
    private String mbr;
}
