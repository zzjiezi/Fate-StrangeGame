package com.game.rpg.controller.battle;

import com.game.rpg.model.entity.Character;
import com.game.rpg.model.entity.Enemy;
import com.game.rpg.model.entity.Skill;
import com.game.rpg.model.enums.JudgementResult;
import com.game.rpg.model.state.BattleState;
import com.game.rpg.model.state.GameStateManager;
import com.game.rpg.view.component.JudgementWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class BattleController {
    private static final Logger logger = LoggerFactory.getLogger(BattleController.class);
    
    private final GameStateManager stateManager;
    private final JudgementWindow judgementWindow;
    
    private Consumer<String> onBattleLog;
    private Runnable onBattleEnd;
    
    public BattleController(GameStateManager stateManager) {
        this.stateManager = stateManager;
        this.judgementWindow = new JudgementWindow();
    }
    
    public void startBattle(Character player, Character servant, Enemy enemy) {
        BattleState battleState = stateManager.getBattleState();
        battleState.initializeBattle(player, servant, enemy);
        
        logger.info("Battle started: {} vs {}", player.getName(), enemy.getName());
        
        if (onBattleLog != null) {
            onBattleLog.accept("战斗开始！面对 " + enemy.getName());
        }
    }
    
    public int executePlayerTurn(Skill skill, JudgementResult judgement) {
        BattleState battleState = stateManager.getBattleState();
        
        if (battleState.getCurrentPhase() == null) {
            logger.warn("Not in battle");
            return 0;
        }
        
        Character player = battleState.getPlayer();
        Enemy enemy = battleState.getEnemy();
        
        int damage = calculateDamage(player, skill, judgement, battleState);
        enemy.takeDamage(damage);
        
        if (onBattleLog != null) {
            onBattleLog.accept(String.format("玩家攻击造成 %d 点伤害！", damage));
        }
        
        if (checkBattleEnd()) {
            endBattle(true);
            return damage;
        }
        
        battleState.nextTurn();
        executeEnemyTurn();
        
        return damage;
    }
    
    private int calculateDamage(Character attacker, Skill skill, JudgementResult judgement, BattleState battleState) {
        int baseDamage = skill != null ? skill.getBaseDamage() : attacker.getAttackPower();
        int damage = (int) Math.round(baseDamage * judgement.getDamageMultiplier());
        damage = (int) Math.round(damage * battleState.getDamageBoostMultiplier());
        return damage;
    }
    
    public void executeEnemyTurn() {
        BattleState battleState = stateManager.getBattleState();
        
        if (battleState.getCurrentPhase() == null) {
            return;
        }
        
        Enemy enemy = battleState.getEnemy();
        Character player = battleState.getPlayer();
        Character servant = battleState.getServant();
        
        int damage = enemy.getAttackPower();
        
        Character target = servant != null && servant.isAlive() ? servant : player;
        target.takeDamage(damage);
        
        if (onBattleLog != null) {
            onBattleLog.accept(String.format("%s 攻击造成 %d 点伤害！", enemy.getName(), damage));
        }
        
        if (checkBattleEnd()) {
            endBattle(false);
            return;
        }
        
        battleState.nextTurn();
    }
    
    public boolean checkBattleEnd() {
        BattleState battleState = stateManager.getBattleState();
        return battleState.isBattleEnded();
    }
    
    public void endBattle(boolean playerVictory) {
        BattleState battleState = stateManager.getBattleState();
        battleState.endBattle();
        
        if (onBattleLog != null) {
            if (playerVictory) {
                onBattleLog.accept("战斗胜利！");
            } else {
                onBattleLog.accept("战斗失败...");
            }
        }
        
        if (onBattleEnd != null) {
            onBattleEnd.run();
        }
        
        logger.info("Battle ended. Player victory: {}", playerVictory);
    }
    
    public void startJudgement() {
        judgementWindow.startJudgement();
    }
    
    public JudgementResult judge() {
        return judgementWindow.judge();
    }
    
    public void setOnBattleLog(Consumer<String> onBattleLog) {
        this.onBattleLog = onBattleLog;
    }
    
    public void setOnBattleEnd(Runnable onBattleEnd) {
        this.onBattleEnd = onBattleEnd;
    }
    
    public JudgementWindow getJudgementWindow() {
        return judgementWindow;
    }
}