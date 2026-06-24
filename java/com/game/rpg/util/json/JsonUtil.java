package com.game.rpg.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    
    public static String toJson(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Failed to serialize object to JSON", e);
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be null or empty");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Failed to deserialize JSON to object", e);
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }
    
    public static void writeToFile(File file, Object obj) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        
        try {
            objectMapper.writeValue(file, obj);
            logger.debug("Written object to file: {}", file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to write object to file: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("Failed to write object to file", e);
        }
    }
    
    public static <T> T readFromFile(File file, Class<T> clazz) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        
        try {
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            logger.error("Failed to read object from file: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("Failed to read object from file", e);
        }
    }
    
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}