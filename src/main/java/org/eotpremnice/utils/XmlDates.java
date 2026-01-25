package org.eotpremnice.utils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalTime;

public final class XmlDates {

    private static final DatatypeFactory DF;

    static {
        try {
            DF = DatatypeFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot init DatatypeFactory", e);
        }
    }

    private XmlDates() {}

    public static XMLGregorianCalendar date(LocalDate d) {
        if (d == null) return null;
        return DF.newXMLGregorianCalendarDate(
                d.getYear(),
                d.getMonthValue(),
                d.getDayOfMonth(),
                DatatypeConstants.FIELD_UNDEFINED // no timezone
        );
    }

    public static XMLGregorianCalendar time(LocalTime t) {
        if (t == null) return null;

        XMLGregorianCalendar cal = DF.newXMLGregorianCalendar();
        cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        cal.setTime(t.getHour(), t.getMinute(), t.getSecond());
        return cal;
    }
}