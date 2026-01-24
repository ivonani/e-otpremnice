package org.eotpremnice.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class StringTrimConverter implements AttributeConverter<String, String> {


    @Override
    public String convertToDatabaseColumn(String s) {
        return Objects.nonNull(s) ? s.trim() : null;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return Objects.nonNull(s) ? s.trim() : null;
    }
}
