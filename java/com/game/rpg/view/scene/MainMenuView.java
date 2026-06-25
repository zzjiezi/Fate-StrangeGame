package com.game.rpg.view.scene;

import com.game.rpg.controller.menu.MenuController;
import com.game.rpg.util.resource.ResourceLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuView extends StackPane {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuView.class);
    
    private final ResourceLoader resourceLoader;
    private final MenuController menuController;
    
    public MainMenuView(ResourceLoader resourceLoader, MenuController menuController) {
        this.resourceLoader = resourceLoader;
        this.menuController = menuController;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setPrefSize(1280, 720);
        
        ImageView backgroundView = loadBackground();
        if (backgroundView != null) {
            getChildren().add(backgroundView);
        }
        
        createTransparentButtons();
        
        logger.info("Main menu view initialized");
    }
    
    private ImageView loadBackground() {
        try {
            String backgroundPath = "C:\\Users\\23727\\IDEProjects\\demo\\src\\main\\resources\\images\\ui\\背景.png";
            Image backgroundImage = resourceLoader.loadImage(backgroundPath);
            
            ImageView imageView = new ImageView(backgroundImage);
            imageView.setFitWidth(1280);
            imageView.setFitHeight(720);
            imageView.setPreserveRatio(false);
            
            return imageView;
        } catch (Exception e) {
            logger.error("Failed to load background image", e);
            return createFallbackBackground();
        }
    }
    
    private ImageView createFallbackBackground() {
        Image fallbackImage = new Image("file:C:\\Users\\23727\\IDEProjects\\demo\\src\\main\\resources\\images\\ui\\背景.png");
        ImageView imageView = new ImageView(fallbackImage);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(720);
        imageView.setPreserveRatio(false);
        return imageView;
    }
    
    private void createTransparentButtons() {
        Button newGameButton = createTransparentHotspot(
            530, 380, 150, 50,
            "New game button clicked",
            () -> menuController.startNewGame()
        );
        
        Button loadGameButton = createTransparentHotspot(
            520, 440, 180, 50,
            "Load game button clicked",
            this::showSaveList
        );
        
        Button achievementsButton = createTransparentHotspot(
            520, 500, 180, 50,
            "Achievements button clicked",
            this::showAchievements
        );
        
        getChildren().addAll(newGameButton, loadGameButton, achievementsButton);
    }
    
    private Button createTransparentHotspot(double x, double y, double width, double height, 
                                            String logMessage, Runnable action) {
        Button button = new Button();
        
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);


        button.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-border-color: transparent; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                "-fx-border-color: rgba(255, 255, 255, 0.3); " +
                "-fx-border-width: 1; " +
                "-fx-cursor: hand;"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-border-color: transparent; " +
                "-fx-cursor: hand;"
            );
        });
        
        button.setOnAction(e -> {
            logger.info(logMessage);
            action.run();
        });
        
        return button;
    }
    
    private void showSaveList() {
        logger.info("Showing save list");
    }
    
    private void showAchievements() {
        logger.info("Showing achievements");
    }
}
