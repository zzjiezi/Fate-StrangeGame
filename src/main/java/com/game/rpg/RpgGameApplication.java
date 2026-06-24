package com.game.rpg;

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

    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Starting RPG Game Application...");
            
            StackPane root = new StackPane();
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            scene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
            
            primaryStage.setTitle(TITLE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            
            primaryStage.show();
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            System.exit(1);
        }
    }

    @Override
    public void stop() {
        logger.info("Application shutting down...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}