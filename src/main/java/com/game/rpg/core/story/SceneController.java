package com.game.rpg.core.story;

import com.game.rpg.model.entities.Scene;
import com.game.rpg.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SceneController {
    private static final Logger logger = LoggerFactory.getLogger(SceneController.class);
    
    private final ResourceLoader resourceLoader;
    private final Map<String, Scene> scenes;
    private Scene currentScene;
    
    private Consumer<Scene> onSceneLoaded;
    private Consumer<String> onInteractionTriggered;
    
    public SceneController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.scenes = new HashMap<>();
        this.currentScene = null;
    }
    
    public void registerScene(Scene scene) {
        if (scene == null) {
            throw new IllegalArgumentException("Scene cannot be null");
        }
        
        scenes.put(scene.getId(), scene);
        logger.debug("Scene registered: {}", scene.getId());
    }
    
    public void loadScene(String sceneId) {
        Scene scene = scenes.get(sceneId);
        
        if (scene == null) {
            logger.error("Scene not found: {}", sceneId);
            return;
        }
        
        currentScene = scene;
        
        if (scene.getBackgroundPath() != null) {
            try {
                resourceLoader.loadImage(scene.getBackgroundPath());
            } catch (Exception e) {
                logger.error("Failed to load scene background: {}", scene.getBackgroundPath(), e);
            }
        }
        
        if (onSceneLoaded != null) {
            onSceneLoaded.accept(scene);
        }
        
        logger.info("Scene loaded: {}", sceneId);
    }
    
    public void renderScene() {
        if (currentScene == null) {
            logger.warn("No scene to render");
            return;
        }
        
        logger.debug("Rendering scene: {}", currentScene.getId());
    }
    
    public void handleInteraction(double x, double y) {
        if (currentScene == null) {
            return;
        }
        
        currentScene.getInteractableObject(x, y).ifPresent(obj -> {
            obj.interact();
            
            if (onInteractionTriggered != null) {
                onInteractionTriggered.accept(obj.getId());
            }
            
            logger.info("Interaction triggered: {} at ({}, {})", obj.getName(), x, y);
        });
    }
    
    public void applyIllusionEffect(boolean enable) {
        if (currentScene != null) {
            currentScene.setIllusionEffect(enable);
            logger.info("Illusion effect {} for scene: {}", 
                    enable ? "enabled" : "disabled", currentScene.getId());
        }
    }
    
    public Scene getCurrentScene() {
        return currentScene;
    }
    
    public Scene getScene(String sceneId) {
        return scenes.get(sceneId);
    }
    
    public void setOnSceneLoaded(Consumer<Scene> onSceneLoaded) {
        this.onSceneLoaded = onSceneLoaded;
    }
    
    public void setOnInteractionTriggered(Consumer<String> onInteractionTriggered) {
        this.onInteractionTriggered = onInteractionTriggered;
    }
}
