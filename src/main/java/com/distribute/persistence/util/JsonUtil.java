package com.distribute.persistence.util;

import com.distribute.domain.action.BatchParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toClass(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("json转换异常", e);
        }
        throw new RuntimeException("json转换异常");
    }

    public static <T> T toClass(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("json转换异常", e);
        }
        throw new RuntimeException("json转换异常");
    }

    public static <T> String toJson(BatchParam<T> param) {
        try {
            return objectMapper.writeValueAsString(param);
        } catch (Exception e) {
            log.error("json转换异常", e);
        }
        return null;
    }

    public static <T> String toTypeJson(T param) {
        try {
            objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
            return objectMapper.writeValueAsString(param);
        } catch (Exception e) {
            log.error("json转换异常", e);
        }
        return null;
    }
}
