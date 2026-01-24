package org.eotpremnice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public class NumberMapper {
    @Named("integerToString")
    public String integerToString(Integer value) {
        return value == null ? null : value.toString();
    }

    @Named("stringToInteger")
    public Integer stringToInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Integer.valueOf(value.trim());
    }
}
