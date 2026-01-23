package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.StavkeEntityId;
import org.eotpremnice.entity.id.VozacEntityId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Stavke")
public class StavkeEntity implements Serializable {

    @EmbeddedId
    private StavkeEntityId id;

    @Column(name = "IDDOK")
    private Integer iddok;

    @Column(name = "Rbr")
    private Integer rBr;

    @Column(name = "Kolicina")
    private BigDecimal kolicina;

    @Column(name = "JMere")
    @Convert(converter = StringTrimConverter.class)
    private String jMere;

    @Column(name = "SifraArtikla")
    @Convert(converter = StringTrimConverter.class)
    private String sifraArtikla;

    @Column(name = "NazivArtikla")
    @Convert(converter = StringTrimConverter.class)
    private String nazivArtikla;

    @Column(name = "OpisArtikla")
    @Convert(converter = StringTrimConverter.class)
    private String opisArtikla;

    @Column(name = "GTIN")
    @Convert(converter = StringTrimConverter.class)
    private String gtin;

    @Column(name = "AKategorija")
    @Convert(converter = StringTrimConverter.class)
    private String aKategorija;

    @Column(name = "ATipKolicine")
    @Convert(converter = StringTrimConverter.class)
    private String aTipKolicine;

    @Column(name = "AKolicina")
    private BigDecimal aKolicina;
}
