package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.LogEntityId;
import org.eotpremnice.entity.id.OtpremniceEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Otpremnice")
public class OtpremniceEntity implements Serializable {

    @EmbeddedId
    private OtpremniceEntityId id;

    @Column(name = "BrDok")
    @Convert(converter = StringTrimConverter.class)
    private String brDok;

    @Column(name = "TipOtpremnice")
    @Convert(converter = StringTrimConverter.class)
    private String tipOtpremnice;

    @Column(name = "DatumIzdavanja")
    @Convert(converter = StringTrimConverter.class)
    private String datumIzdavanja;

    @Column(name = "DatumKraj")
    @Convert(converter = StringTrimConverter.class)
    private String datumKraj;

    @Column(name = "VremeKraj")
    @Convert(converter = StringTrimConverter.class)
    private String vremeKraj;

    @Column(name = "DatumOtpreme")
    @Convert(converter = StringTrimConverter.class)
    private String datumOtpreme;

    @Column(name = "VremeOtpreme")
    @Convert(converter = StringTrimConverter.class)
    private String vremeOtpreme;

    @Column(name = "IDUgovora")
    @Convert(converter = StringTrimConverter.class)
    private String idUgovora;

    @Column(name = "IDNarudzbenice")
    @Convert(converter = StringTrimConverter.class)
    private String idNarudzbenice;

    @Column(name = "NacinOtpreme")
    private Integer nacinOtpreme;

    @Column(name = "NapOpsta")
    @Convert(converter = StringTrimConverter.class)
    private String napOpsta;

    @Column(name = "Povratnica")
    private Integer povratnica;

    @Column(name = "OpasneMaterije")
    private Integer opasneMaterije;
}
