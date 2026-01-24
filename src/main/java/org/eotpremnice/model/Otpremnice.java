package org.eotpremnice.model;

import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
public class Otpremnice {
    String nacinOtpreme;     // smallint
    String povratnica;       // bit
    String idUgovora;         // nvarchar(50)

    String brDok;             // varchar(20)
    LocalDate datumIzdavanja;    // varchar(10) npr "2026-01-24" ili "24.01.2026" (zavisi od baze)
    String tipOtpremnice;     // varchar(3)
    String napOpsta;          // nvarchar(max)
    String idNarudzbenice;    // varchar(20)
}
