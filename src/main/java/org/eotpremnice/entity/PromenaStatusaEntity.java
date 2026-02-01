package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.PromenaStatusaEntityId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "PromenaStatusa")
public class PromenaStatusaEntity implements Serializable {

    @EmbeddedId
    private PromenaStatusaEntityId id;

    @Column(name = "KodPromene")
    private Integer kodPromene;

    @Column(name = "DatumPromene")
    @Convert(converter = StringTrimConverter.class)
    private LocalDate datumPromene;

    @Column(name = "OznakaPromene")
    @Convert(converter = StringTrimConverter.class)
    private String oznakaPromene;

    @Column(name = "RazlogPromene")
    @Convert(converter = StringTrimConverter.class)
    private String razlogPromene;

    @Column(name = "PIBDobavljac")
    @Convert(converter = StringTrimConverter.class)
    private String pibDobavljac;

    @Column(name = "PIBKupac")
    @Convert(converter = StringTrimConverter.class)
    private String pibKupac;

    @Column(name = "BrDok")
    @Convert(converter = StringTrimConverter.class)
    private String brDok;

    @Column(name = "DatumIzdavanja")
    @Convert(converter = StringTrimConverter.class)
    private LocalDate datumIzdavanja;

    @Column(name = "Poslato")
    private Integer poslato;

    @Column(name = "RequestID")
    @Convert(converter = StringTrimConverter.class)
    private String requestId;
}