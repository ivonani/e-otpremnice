package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.PrevoznikEntityId;
import org.eotpremnice.entity.id.VozacEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Vozac")
public class VozacEntity implements Serializable {

    @EmbeddedId
    private VozacEntityId id;

    @Column(name = "IDVozac")
    @Convert(converter = StringTrimConverter.class)
    private String idVozac;

    @Column(name = "Ime")
    @Convert(converter = StringTrimConverter.class)
    private String ime;

    @Column(name = "Prezime")
    @Convert(converter = StringTrimConverter.class)
    private String prezime;

    @Column(name = "BrojDozvole")
    @Convert(converter = StringTrimConverter.class)
    private String brojDozvole;

    @Column(name = "RegBroj")
    @Convert(converter = StringTrimConverter.class)
    private String regBroj;

    @Column(name = "EMail")
    @Convert(converter = StringTrimConverter.class)
    private String email;
}
