package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.IsporukaEntityId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Isporuka")
public class IsporukaEntity implements Serializable {

    @EmbeddedId
    private IsporukaEntityId id;

    @Column(name = "IDDOK")
    private Integer iddok;

    @Column(name = "Broj")
    @Convert(converter = StringTrimConverter.class)
    private String broj;

    @Column(name = "TezinaJM")
    @Convert(converter = StringTrimConverter.class)
    private String tezinaJM;

    @Column(name = "Tezina")
    private BigDecimal tezina;

    @Column(name = "ZapreminaJM")
    @Convert(converter = StringTrimConverter.class)
    private String zapreminJM;

    @Column(name = "Zapremina")
    private BigDecimal zapremina;

    @Column(name = "BrPaketa")
    private Integer brPaketa;

    @Column(name = "NapIsporuke")
    @Convert(converter = StringTrimConverter.class)
    private String napIsporuke;

    @Column(name = "ZIP")
    @Convert(converter = StringTrimConverter.class)
    private String zip;

    @Column(name = "Drzava")
    @Convert(converter = StringTrimConverter.class)
    private String drzava;
}
