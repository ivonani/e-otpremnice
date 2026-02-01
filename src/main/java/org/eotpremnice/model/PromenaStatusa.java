package org.eotpremnice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PromenaStatusa {
    private Integer kodPromene;
    private String oznakaPromene;
    private LocalDate datumPromene;
    private String razlogPromene;
    private String pibDobavljac;
    private String pibKupac;
    private String brDok;
    private String requestId;
    private LocalDate datumIzdavanja;
}