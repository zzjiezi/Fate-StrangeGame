package com.game.rpg;

import com.game.rpg.controller.menu.MenuController;
import com.game.rpg.controller.save.SaveController;
import com.game.rpg.controller.achievement.AchievementController;
import com.game.rpg.controller.story.StoryController;
import com.game.rpg.model.state.GameStateManager;
import com.game.rpg.util.config.GameConfig;
import com.game.rpg.util.config.ResourceConfig;
import com.game.rpg.util.resource.ResourceLoader;
import com.game.rpg.view.scene.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpgGameApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(RpgGameApplication.class);
    
    private static final String TITLE = "RPG Game - 圣杯战争";
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    
    private GameConfig gameConfig;
    private ResourceConfig resourceConfig;
    private ResourceLoader resourceLoader;
    private GameStateManager stateManager;
    
    private MenuController menuController;
    private SaveController saveController;
    private AchievementController achievementController;
    private StoryController storyController;

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Starting RPG Game Application...");
            
            initializeComponents();
            
            StackPane root = new StackPane();
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            scene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
            
            MainMenuView mainMenuView = new MainMenuView(resourceLoader, menuController);
            root.getChildren().add(mainMenuView);
            
            primaryStage.setTitle(TITLE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            
            primaryStage.show();
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void initializeComponents() {
        gameConfig = new GameConfig();
        resourceConfig = new ResourceConfig();
        resourceLoader = new ResourceLoader(resourceConfig);
        stateManager = new GameStateManager();
        
        menuController = new MenuController(stateManager);
        saveController = new SaveController(stateManager);
        achievementController = new AchievementController(stateManager);
        storyController = new StoryController(stateManager, resourceLoader);
        
        menuController.setOnNewGame(v -> {
            logger.info("Starting new game...");
            storyController.startAct(1);
        });
        
        logger.info("Components initialized");
    }

    @Override
    public void stop() {
        logger.info("Application shutting down...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
