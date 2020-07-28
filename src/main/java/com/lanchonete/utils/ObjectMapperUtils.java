package com.lanchonete.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperUtils {

    private ObjectMapperUtils() {
    }

    public static String toJson(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(o);
            return json;
        } catch (Exception e) {
            return json;
        }
    }

    public static <T> T jsonTo(String json, Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        T entity = null;
        try {
            entity = objectMapper.readValue(json, type);
            return entity;
        } catch (Exception e) {
            return entity;
        }
    }
}