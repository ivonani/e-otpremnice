package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.MagacinEntityId;
import org.eotpremnice.entity.id.OtpremniceEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Magacin")
public class MagacinEntity implements Serializable {

    @EmbeddedId
    private MagacinEntityId id;

    @Column(name = "IDDOK")
    private Integer iddok;

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
