package com.game.rpg.util.resource;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static final Logger logger = LoggerFactory.getLogger(ImageCache.class);
    
    private final Map<String, Image> cache;
    private final int maxCacheSize;
    
    public ImageCache() {
        this(100);
    }
    
    public ImageCache(int maxCacheSize) {
        if (maxCacheSize <= 0) {
            throw new IllegalArgumentException("Max cache size must be positive");
        }
        this.cache = new HashMap<>();
        this.maxCacheSize = maxCacheSize;
    }
    
    public Image get(String key) {
        return cache.get(key);
    }
    
    public void put(String key, Image image) {
        if (key == null || image == null) {
            throw new IllegalArgumentException("Key and image cannot be null");
        }
        
        if (cache.size() >= maxCacheSize && !cache.containsKey(key)) {
            logger.warn("Cache is full, consider clearing old entries");
        }
        
        cache.put(key, image);
        logger.debug("Cached image: {}", key);
    }
    
    public boolean contains(String key) {
        return cache.containsKey(key);
    }
    
    public void remove(String key) {
        cache.remove(key);
        logger.debug("Removed image from cache: {}", key);
    }
    
    public void clear() {
        cache.clear();
        logger.info("Image cache cleared");
    }
    
    public int size() {
        return cache.size();
    }
    
    public int getMaxCacheSize() {
        return maxCacheSize;
    }
    
    public double getCacheHitRate() {
        return cache.isEmpty() ? 0.0 : (double) cache.size() / maxCacheSize;
    }
}