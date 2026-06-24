package com.game.rpg.view.scene;

import com.game.rpg.controller.achievement.AchievementController;
import com.game.rpg.model.entity.Achievement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class AchievementListView extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(AchievementListView.class);
    
    private final AchievementController achievementController;
    private Runnable onBack;
    
    public AchievementListView(AchievementController achievementController) {
        this.achievementController = achievementController;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(50));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");
        
        Label titleLabel = new Label("成就列表");
        titleLabel.setFont(Font.font(36));
        titleLabel.setTextFill(Color.CYAN);
        
        VBox achievementsBox = createAchievementsBox();
        
        Button backButton = createBackButton();
        
        getChildren().addAll(titleLabel, achievementsBox, backButton);
        
        logger.info("Achievement list view initialized");
    }
    
    private VBox createAchievementsBox() {
        VBox achievementsBox = new VBox(15);
        achievementsBox.setAlignment(Pos.CENTER);
        
        List<Achievement> achievements = achievementController.getAllAchievements();
        
        for (Achievement achievement : achievements) {
            HBox achievementBox = createAchievementBox(achievement);
            achievementsBox.getChildren().add(achievementBox);
        }
        
        if (achievements.isEmpty()) {
            Label emptyLabel = new Label("暂无成就");
            emptyLabel.setFont(Font.font(20));
            emptyLabel.setTextFill(Color.GRAY);
            achievementsBox.getChildren().add(emptyLabel);
        }
        
        return achievementsBox;
    }
    
    private HBox createAchievementBox(Achievement achievement) {
        HBox achievementBox = new HBox(20);
        achievementBox.setAlignment(Pos.CENTER_LEFT);
        achievementBox.setPadding(new Insets(15));
        achievementBox.setPrefWidth(600);
        
        if (achievement.isUnlocked()) {
            achievementBox.setStyle(
                "-fx-background-color: rgba(0, 100, 100, 0.5); " +
                "-fx-border-color: gold; " +
                "-fx-border-width: 2;"
            );
            
            Label nameLabel = new Label("🏆 " + achievement.getName());
            nameLabel.setFont(Font.font(22));
            nameLabel.setTextFill(Color.GOLD);
            
            Label descLabel = new Label(achievement.getDescription());
            descLabel.setFont(Font.font(16));
            descLabel.setTextFill(Color.LIGHTGRAY);
            
            VBox infoBox = new VBox(5);
            infoBox.getChildren().addAll(nameLabel, descLabel);
            
            achievementBox.getChildren().add(infoBox);
        } else {
            achievementBox.setStyle(
                "-fx-background-color: rgba(50, 50, 50, 0.5); " +
                "-fx-border-color: gray; " +
                "-fx-border-width: 1;"
            );
            
            Label nameLabel = new Label("🔒 " + achievement.getName());
            nameLabel.setFont(Font.font(22));
            nameLabel.setTextFill(Color.GRAY);
            
            Label descLabel = new Label("未解锁: " + achievement.getDescription());
            descLabel.setFont(Font.font(16));
            descLabel.setTextFill(Color.DARKGRAY);
            
            VBox infoBox = new VBox(5);
            infoBox.getChildren().addAll(nameLabel, descLabel);
            
            achievementBox.getChildren().add(infoBox);
        }
        
        return achievementBox;
    }
    
    private Button createBackButton() {
        Button button = new Button("返回");
        button.setPrefWidth(150);
        button.setFont(Font.font(18));
        button.setStyle(
            "-fx-background-color: rgba(100, 100, 100, 0.7); " +
            "-fx-text-fill: white; " +
            "-fx-border-color: white; " +
            "-fx-border-width: 2; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnAction(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });
        
        return button;
    }
    
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }
}