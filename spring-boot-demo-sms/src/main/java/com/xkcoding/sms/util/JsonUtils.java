package com.xkcoding.sms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaType getCollectionType(Class<? extends Collection> collectionClass, Class... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> List<T> toList(String json, Class<? extends Collection> collectionClass, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, getCollectionType(collectionClass, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object toObject(String json, JavaType javaType) {
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final static TypeReference<Map<String, String>> STRING_MAP_REFERENCE = new TypeReference<Map<String, String>>() {
    };

    private final static TypeReference<List<String>> STRING_LIST_REFERENCE = new TypeReference<List<String>>() {
    };

    public static Map<String, String> toStringMap(String json) {
        return toObject(json, STRING_MAP_REFERENCE);
    }

    public static List<String> toStringList(String json) {
        return toObject(json, STRING_LIST_REFERENCE);
    }
}
