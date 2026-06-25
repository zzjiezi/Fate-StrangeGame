package com.game.rpg.resource;

import com.game.rpg.config.ResourceConfig;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ResourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
    
    private final ResourceConfig resourceConfig;
    private final ImageCache imageCache;
    private final AudioCache audioCache;
    
    public ResourceLoader(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
        this.imageCache = new ImageCache();
        this.audioCache = new AudioCache();
    }
    
    public Image loadImage(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be null or empty");
        }
        
        if (imageCache.contains(path)) {
            logger.debug("Image cache hit: {}", path);
            return imageCache.get(path);
        }
        
        try {
            Image image;
            File file = new File(path);
            if (file.exists()) {
                image = new Image(new FileInputStream(file));
            } else {
                image = new Image(getClass().getResourceAsStream(path));
            }
            
            imageCache.put(path, image);
            logger.info("Loaded image: {}", path);
            return image;
        } catch (FileNotFoundException e) {
            logger.error("Failed to load image: {}", path, e);
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }
    
    public AudioClip loadAudio(String path, boolean isBgm) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Audio path cannot be null or empty");
        }
        
        AudioCache cache = isBgm ? audioCache : null;
        if (isBgm && audioCache.containsBgm(path)) {
            logger.debug("BGM cache hit: {}", path);
            return audioCache.getBgm(path);
        }
        
        if (!isBgm && audioCache.containsSfx(path)) {
            logger.debug("SFX cache hit: {}", path);
            return audioCache.getSfx(path);
        }
        
        try {
            AudioClip audio = new AudioClip(path);
            
            if (isBgm) {
                audioCache.putBgm(path, audio);
            } else {
                audioCache.putSfx(path, audio);
            }
            
            logger.info("Loaded audio: {}", path);
            return audio;
        } catch (Exception e) {
            logger.error("Failed to load audio: {}", path, e);
            throw new RuntimeException("Failed to load audio: " + path, e);
        }
    }
    
    public CompletableFuture<Void> preloadSceneResources(List<String> imagePaths, List<String> audioPaths) {
        logger.info("Starting scene resource preload...");
        
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            for (String path : imagePaths) {
                try {
                    loadImage(path);
                } catch (Exception e) {
                    logger.warn("Failed to preload image: {}", path, e);
                }
            }
        });
        
        CompletableFuture<Void> audioFuture = CompletableFuture.runAsync(() -> {
            for (String path : audioPaths) {
                try {
                    loadAudio(path, false);
                } catch (Exception e) {
                    logger.warn("Failed to preload audio: {}", path, e);
                }
            }
        });
        
        return CompletableFuture.allOf(imageFuture, audioFuture)
                .thenRun(() -> logger.info("Scene resource preload completed"));
    }
    
    public ImageCache getImageCache() {
        return imageCache;
    }
    
    public AudioCache getAudioCache() {
        return audioCache;
    }
    
    public void clearCache() {
        imageCache.clear();
        audioCache.clearAll();
        logger.info("All caches cleared");
    }
}