package com.game.rpg.ui.components;

import com.game.rpg.model.enums.JudgementResult;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class JudgementWindow extends Pane {
    private static final double WINDOW_WIDTH = 400;
    private static final double WINDOW_HEIGHT = 200;
    
    private final Canvas canvas;
    private final GraphicsContext gc;
    
    private double targetX;
    private double currentX;
    private double speed;
    private boolean active;
    private long startTime;
    
    private Consumer<JudgementResult> onJudgementComplete;
    
    public JudgementWindow() {
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        getChildren().add(canvas);
        
        targetX = WINDOW_WIDTH / 2;
        currentX = 0;
        speed = 5;
        active = false;
    }
    
    public void startJudgement() {
        currentX = 0;
        active = true;
        startTime = System.currentTimeMillis();
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!active) {
                    stop();
                    return;
                }
                
                update();
                render();
            }
        };
        
        timer.start();
    }
    
    public JudgementResult judge() {
        if (!active) {
            return JudgementResult.FAIL;
        }
        
        active = false;
        
        double distance = Math.abs(currentX - targetX);
        
        JudgementResult result;
        if (distance < 20) {
            result = JudgementResult.GREAT_SUCCESS;
        } else if (distance < 50) {
            result = JudgementResult.SMALL_SUCCESS;
        } else {
            result = JudgementResult.FAIL;
        }
        
        if (onJudgementComplete != null) {
            onJudgementComplete.accept(result);
        }
        
        return result;
    }
    
    private void update() {
        currentX += speed;
        
        if (currentX > WINDOW_WIDTH) {
            active = false;
            if (onJudgementComplete != null) {
                onJudgementComplete.accept(JudgementResult.FAIL);
            }
        }
    }
    
    private void render() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        gc.setStroke(Color.CYAN);
        gc.setLineWidth(3);
        gc.strokeLine(targetX, 0, targetX, WINDOW_HEIGHT);
        
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        double greatSuccessLeft = targetX - 20;
        double greatSuccessRight = targetX + 20;
        gc.strokeLine(greatSuccessLeft, 0, greatSuccessLeft, WINDOW_HEIGHT);
        gc.strokeLine(greatSuccessRight, 0, greatSuccessRight, WINDOW_HEIGHT);
        
        gc.setStroke(Color.ORANGE);
        double smallSuccessLeft = targetX - 50;
        double smallSuccessRight = targetX + 50;
        gc.strokeLine(smallSuccessLeft, 0, smallSuccessLeft, WINDOW_HEIGHT);
        gc.strokeLine(smallSuccessRight, 0, smallSuccessRight, WINDOW_HEIGHT);
        
        gc.setFill(Color.RED);
        gc.fillOval(currentX - 10, WINDOW_HEIGHT / 2 - 10, 20, 20);
    }
    
    public void setOnJudgementComplete(Consumer<JudgementResult> onJudgementComplete) {
        this.onJudgementComplete = onJudgementComplete;
    }
    
    public boolean isActive() {
        return active;
    }
}