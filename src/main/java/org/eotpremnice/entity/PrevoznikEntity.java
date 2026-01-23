package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.PrevoznikEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Prevoznik")
public class PrevoznikEntity implements Serializable {

    @EmbeddedId
    private PrevoznikEntityId id;

    @Column(name = "IDDOK")
    private Integer iddok;

    @Column(name = "PunNaziv")
    @Convert(converter = StringTrimConverter.class)
    private String punNaziv;

    @Column(name = "MBR")
    @Convert(converter = StringTrimConverter.class)
    private String mbr;

    @Column(name = "PIB")
    @Convert(converter = StringTrimConverter.class)
    private String pib;

    @Column(name = "PIB_RS")
    @Convert(converter = StringTrimConverter.class)
    private String pibRs;

    @Column(name = "JBKJS")
    @Convert(converter = StringTrimConverter.class)
    private String jbkjs;

    @Column(name = "Adresa")
    @Convert(converter = StringTrimConverter.class)
    private String adresa;

    @Column(name = "Mesto")
    @Convert(converter = StringTrimConverter.class)
    private String mesto;

    @Column(name = "ZIP")
    @Convert(converter = StringTrimConverter.class)
    private String zip;

    @Column(name = "Drzava")
    @Convert(converter = StringTrimConverter.class)
    private String drzava;
}
