package org.eotpremnice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eotpremnice.entity.converter.StringTrimConverter;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Semafor")
public class SemaforEntity {

    @Id
    @Column(name = "IDRacunar")
    @Convert(converter = StringTrimConverter.class)
    private String idRacunar;

    @Column(name = "EDokument")
    private Integer eDokument;
}
