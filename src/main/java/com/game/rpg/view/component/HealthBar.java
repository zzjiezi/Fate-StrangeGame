package com.game.rpg.view.component;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar extends VBox {
    private final ProgressBar progressBar;
    private final Rectangle background;
    private int currentValue;
    private int maxValue;
    
    public HealthBar(double width, double height, Color color) {
        setSpacing(2);
        
        background = new Rectangle(width, height);
        background.setFill(Color.DARKGRAY);
        background.setStroke(Color.WHITE);
        background.setStrokeWidth(1);
        
        progressBar = new ProgressBar();
        progressBar.setPrefSize(width, height);
        progressBar.setProgress(1.0);
        progressBar.setStyle(String.format(
                "-fx-accent: derive(%s, -20%%); " +
                "-fx-background-color: transparent; " +
                "-fx-background-insets: 0; " +
                "-fx-padding: 0;",
                toRgbString(color)
        ));
        
        getChildren().addAll(background, progressBar);
        
        currentValue = 100;
        maxValue = 100;
    }
    
    public void update(int current, int max) {
        this.currentValue = current;
        this.maxValue = max;
        
        double percentage = max > 0 ? (double) current / max : 0.0;
        progressBar.setProgress(percentage);
        
        if (percentage > 0.6) {
            progressBar.setStyle("-fx-accent: #00ff00; -fx-background-color: transparent;");
        } else if (percentage > 0.3) {
            progressBar.setStyle("-fx-accent: #ffff00; -fx-background-color: transparent;");
        } else {
            progressBar.setStyle("-fx-accent: #ff0000; -fx-background-color: transparent;");
        }
    }
    
    public void setValue(int value) {
        update(value, maxValue);
    }
    
    public int getCurrentValue() {
        return currentValue;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}