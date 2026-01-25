package org.eotpremnice.entity.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class KurirEntityId implements Serializable {

    @Column(name = "IDFirme")
    @Convert(converter = StringTrimConverter.class)
    private String iDFirme;

    @Column(name = "TipDokumenta")
    @Convert(converter = StringTrimConverter.class)
    private String tipDokumenta;

    @Column(name = "IDDOK")
    private Integer iddok;
}
