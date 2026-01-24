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
public class Isporuka {

    private String broj;
    private BigDecimal tezina;
    private String tezinaJM;
    private BigDecimal zapremina;
    private String zapreminaJM;

    private Integer brPaketa;
    private String napIsporuke;


}
