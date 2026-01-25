package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.LogEntityId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "eoLog")
public class LogEntity implements Serializable {

    @EmbeddedId
    private LogEntityId id;

    @Column(name = "Komanda")
    @Convert(converter = StringTrimConverter.class)
    private String komanda;

    @Column(name = "ZaSlanje")
    private Integer zaSlanje;

    @Column(name = "Otpremljen")
    private Integer otpremljen;

    @Column(name = "DatumSlanja")
    private LocalDateTime datumSlanja;

    @Column(name = "DatumOtpreme")
    private LocalDateTime datumOtpreme;

    @Column(name = "IDError")
    private Integer idError;

    @Column(name = "SEF_ID")
    @Convert(converter = StringTrimConverter.class)
    private String sefId;

    @Column(name = "ResponseStatus")
    @Convert(converter = StringTrimConverter.class)
    private String responseStatus;

    @Column(name = "ObradjenStatus")
    private Integer obradjenStatus;

    @Column(name = "STATUS")
    @Convert(converter = StringTrimConverter.class)
    private String status;

    @Column(name = "PromenjenStatus")
    private Integer promenjenStatus;

    @Column(name = "IDRacunar")
    @Convert(converter = StringTrimConverter.class)
    private String idRacunar;

    @Column(name = "RequestID")
    @Convert(converter = StringTrimConverter.class)
    private String requestId;

}
