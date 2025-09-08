package ru.caloricity.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String convertToDatabaseColumn(LocalDate localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(formatter);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LocalDate.parse(dbData, formatter);
    }
}
