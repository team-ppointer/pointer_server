package com.moplus.moplus_server.domain.problem.repository.converter;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = ", ";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            if (attribute == null || attribute.isEmpty()) {
                return "[]";
            }
            return attribute.stream()
                    .filter(str -> str != null && !str.isEmpty())
                    .collect(Collectors.joining(DELIMITER));
        } catch (Exception e) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .filter(str -> str != null && !str.isEmpty())
                .collect(Collectors.toList());
    }
}