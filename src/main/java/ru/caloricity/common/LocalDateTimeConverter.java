package ru.caloricity.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return LocalDateTime.parse(dbData, formatter);
    }
}
