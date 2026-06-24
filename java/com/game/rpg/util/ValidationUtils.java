package com.game.rpg.util;

import java.util.Collection;

public class ValidationUtils {
    
    public static void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireNonNull(Object obj) {
        requireNonNull(obj, "Object cannot be null");
    }
    
    public static void requireNonEmpty(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireNonEmpty(String str) {
        requireNonEmpty(str, "String cannot be null or empty");
    }
    
    public static void requireInRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireInRange(int value, int min, int max) {
        requireInRange(value, min, max, 
                String.format("Value %d must be between %d and %d", value, min, max));
    }
    
    public static void requireInRange(double value, double min, double max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireInRange(double value, double min, double max) {
        requireInRange(value, min, max, 
                String.format("Value %f must be between %f and %f", value, min, max));
    }
    
    public static void requirePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requirePositive(int value) {
        requirePositive(value, "Value must be positive");
    }
    
    public static void requireNonNegative(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireNonNegative(int value) {
        requireNonNegative(value, "Value must be non-negative");
    }
    
    public static void requireNonEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void requireNonEmpty(Collection<?> collection) {
        requireNonEmpty(collection, "Collection cannot be null or empty");
    }
    
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }
    
    public static boolean isPositive(int value) {
        return value > 0;
    }
    
    public static boolean isNonNegative(int value) {
        return value >= 0;
    }
}