package org.eotpremnice.entity.converter;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StringTrimConverter implements AttributeConverter<String, String> {


    @Override
    public String convertToDatabaseColumn(String s) {
        return s.trim();
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s.trim();
    }
}
