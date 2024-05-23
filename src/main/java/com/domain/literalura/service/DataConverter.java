package com.domain.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> javaClass) {
        try {
            return objectMapper.readValue(json, javaClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
