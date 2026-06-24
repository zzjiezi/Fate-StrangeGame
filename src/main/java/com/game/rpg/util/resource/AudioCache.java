package com.game.rpg.util.resource;

import javafx.scene.media.AudioClip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AudioCache {
    private static final Logger logger = LoggerFactory.getLogger(AudioCache.class);
    
    private final Map<String, AudioClip> bgmCache;
    private final Map<String, AudioClip> sfxCache;
    private final int maxBgmCacheSize;
    private final int maxSfxCacheSize;
    
    public AudioCache() {
        this(10, 50);
    }
    
    public AudioCache(int maxBgmCacheSize, int maxSfxCacheSize) {
        if (maxBgmCacheSize <= 0 || maxSfxCacheSize <= 0) {
            throw new IllegalArgumentException("Max cache sizes must be positive");
        }
        this.bgmCache = new HashMap<>();
        this.sfxCache = new HashMap<>();
        this.maxBgmCacheSize = maxBgmCacheSize;
        this.maxSfxCacheSize = maxSfxCacheSize;
    }
    
    public AudioClip getBgm(String key) {
        return bgmCache.get(key);
    }
    
    public void putBgm(String key, AudioClip audio) {
        if (key == null || audio == null) {
            throw new IllegalArgumentException("Key and audio cannot be null");
        }
        bgmCache.put(key, audio);
        logger.debug("Cached BGM: {}", key);
    }
    
    public AudioClip getSfx(String key) {
        return sfxCache.get(key);
    }
    
    public void putSfx(String key, AudioClip audio) {
        if (key == null || audio == null) {
            throw new IllegalArgumentException("Key and audio cannot be null");
        }
        sfxCache.put(key, audio);
        logger.debug("Cached SFX: {}", key);
    }
    
    public boolean containsBgm(String key) {
        return bgmCache.containsKey(key);
    }
    
    public boolean containsSfx(String key) {
        return sfxCache.containsKey(key);
    }
    
    public void clearBgm() {
        bgmCache.clear();
        logger.info("BGM cache cleared");
    }
    
    public void clearSfx() {
        sfxCache.clear();
        logger.info("SFX cache cleared");
    }
    
    public void clearAll() {
        clearBgm();
        clearSfx();
    }
    
    public int getBgmCacheSize() {
        return bgmCache.size();
    }
    
    public int getSfxCacheSize() {
        return sfxCache.size();
    }
}