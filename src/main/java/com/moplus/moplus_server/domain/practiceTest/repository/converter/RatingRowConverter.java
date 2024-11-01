package com.moplus.moplus_server.domain.practiceTest.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingRow;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class RatingRowConverter implements AttributeConverter<List<RatingRow>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<RatingRow> ratingRows) {
        if (ratingRows == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(ratingRows);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("RatingRow 목록을 JSON 문자열로 변환할 수 없습니다 : ", e);
        }
    }

    @Override
    public List<RatingRow> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, RatingRow.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 문자열을 RatingRow 목록으로 변환할 수 없습니다 : ", e);
        }
    }
}
