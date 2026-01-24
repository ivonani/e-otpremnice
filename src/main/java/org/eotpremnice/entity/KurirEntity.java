package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;
import org.eotpremnice.entity.id.KurirEntityId;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Kurir")
public class KurirEntity implements Serializable {

    @EmbeddedId
    private KurirEntityId id;

    @Column(name = "ImePrezima")
    @Convert(converter = StringTrimConverter.class)
    private String imePartizan;

    @Column(name = "BrLK")
    @Convert(converter = StringTrimConverter.class)
    private String brLk;

    @Column(name = "OpisDok")
    @Convert(converter = StringTrimConverter.class)
    private String opisDok;
}
