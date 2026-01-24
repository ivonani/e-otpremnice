package org.eotpremnice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public class DateMapper {

    private static final String PATTERN = "YYYY-MM-DD";

    @Named("stringToDate")
    public Date stringToDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat(PATTERN).parse(value.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value, e);
        }
    }
}
