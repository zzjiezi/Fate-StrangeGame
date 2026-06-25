package com.game.rpg.core.battle;

import com.game.rpg.model.enums.JudgementResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class JudgementSystem {
    private static final Logger logger = LoggerFactory.getLogger(JudgementSystem.class);
    
    private static final int GREAT_SUCCESS_WINDOW = 50;
    private static final int SMALL_SUCCESS_WINDOW = 150;
    private static final int FAIL_WINDOW = 300;
    
    private final Random random;
    private long judgementStartTime;
    private boolean judgementActive;
    
    public JudgementSystem() {
        this.random = new Random();
        this.judgementActive = false;
    }
    
    public void startJudgement() {
        judgementStartTime = System.currentTimeMillis();
        judgementActive = true;
        logger.debug("Judgement started");
    }
    
    public JudgementResult judge() {
        if (!judgementActive) {
            logger.warn("No active judgement");
            return JudgementResult.FAIL;
        }
        
        long reactionTime = System.currentTimeMillis() - judgementStartTime;
        judgementActive = false;
        
        int targetTime = SMALL_SUCCESS_WINDOW;
        long deviation = Math.abs(reactionTime - targetTime);
        
        JudgementResult result;
        
        if (deviation <= GREAT_SUCCESS_WINDOW) {
            result = JudgementResult.GREAT_SUCCESS;
        } else if (deviation <= SMALL_SUCCESS_WINDOW) {
            result = JudgementResult.SMALL_SUCCESS;
        } else if (deviation <= FAIL_WINDOW) {
            result = JudgementResult.SMALL_SUCCESS;
        } else {
            result = JudgementResult.FAIL;
        }
        
        logger.debug("Judgement result: {} (reaction time: {}ms, deviation: {}ms)", 
                result, reactionTime, deviation);
        
        return result;
    }
    
    public int calculateDamage(int baseDamage, JudgementResult result) {
        int finalDamage = (int) Math.round(baseDamage * result.getDamageMultiplier());
        logger.debug("Damage calculated: base={}, result={}, final={}", 
                baseDamage, result, finalDamage);
        return finalDamage;
    }
    
    public boolean isJudgementActive() {
        return judgementActive;
    }
    
    public void cancelJudgement() {
        judgementActive = false;
        logger.debug("Judgement cancelled");
    }
}