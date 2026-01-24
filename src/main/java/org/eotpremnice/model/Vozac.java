package org.eotpremnice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Vozac {
    private String idVozac;
    private String ime;
    private String prezime;
    private String brojDozvole;
    private String email;
    private String regBroj;
}