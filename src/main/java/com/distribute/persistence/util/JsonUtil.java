package com.distribute.persistence.util;

import com.distribute.domain.action.BatchParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/*
* Json工具类, 转换为指定数据
*/
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

    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(json, collectionType);
        } catch (Exception e) {
            log.error("json序列化异常, json:{} ,e:", json, e);
        }
        return null;
    }

    public static <T> T findValue(String json, String fieldName, Class<T> clazz) {
        try {
            return objectMapper.readTree(json).findValue(fieldName).traverse(new ObjectMapper()).readValueAs(clazz);
        } catch (Exception e) {
            log.error("json序列化异常:", e);
        }
        return null;
    }
}
