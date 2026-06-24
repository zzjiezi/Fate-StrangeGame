package com.game.rpg.core.battle;

import com.game.rpg.model.entities.Character;
import com.game.rpg.model.entities.Enemy;
import com.game.rpg.model.entities.Skill;
import com.game.rpg.model.enums.BattlePhase;
import com.game.rpg.model.enums.JudgementResult;
import com.game.rpg.state.BattleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class BattleEngine {
    private static final Logger logger = LoggerFactory.getLogger(BattleEngine.class);
    
    private final BattleState battleState;
    private final SkillExecutor skillExecutor;
    private Consumer<String> onBattleLog;
    private Runnable onBattleEnd;
    
    public BattleEngine() {
        this.battleState = new BattleState();
        this.skillExecutor = new SkillExecutor();
    }
    
    public void startBattle(Character player, Character servant, Enemy enemy) {
        battleState.initializeBattle(player, servant, enemy);
        logger.info("Battle started: {} vs {}", player.getName(), enemy.getName());
        
        if (onBattleLog != null) {
            onBattleLog.accept("战斗开始！面对 " + enemy.getName());
        }
    }
    
    public int executePlayerTurn(Skill skill, JudgementResult judgement) {
        if (battleState.getCurrentPhase() != BattlePhase.PLAYER_TURN) {
            logger.warn("Not player's turn");
            return 0;
        }
        
        Character player = battleState.getPlayer();
        Enemy enemy = battleState.getEnemy();
        
        int damage = 0;
        
        if (skill == null) {
            damage = skillExecutor.executeNormalAttack(player, enemy);
            damage = skillExecutor.getJudgementSystem().calculateDamage(damage, judgement);
            enemy.takeDamage(damage);
        } else {
            damage = skillExecutor.executeSkill(player, skill, enemy, judgement);
        }
        
        damage = (int) Math.round(damage * battleState.getDamageBoostMultiplier());
        
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
    
    public void executeEnemyTurn() {
        if (battleState.getCurrentPhase() != BattlePhase.ENEMY_TURN) {
            return;
        }
        
        Enemy enemy = battleState.getEnemy();
        Character player = battleState.getPlayer();
        Character servant = battleState.getServant();
        
        int damage = skillExecutor.executeNormalAttack(enemy, player);
        
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
        Character player = battleState.getPlayer();
        Character servant = battleState.getServant();
        Enemy enemy = battleState.getEnemy();
        
        boolean playerDefeated = !player.isAlive() && (servant == null || !servant.isAlive());
        boolean enemyDefeated = enemy == null || !enemy.isAlive();
        
        return playerDefeated || enemyDefeated;
    }
    
    public void endBattle(boolean playerVictory) {
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
    
    public void setOnBattleLog(Consumer<String> onBattleLog) {
        this.onBattleLog = onBattleLog;
    }
    
    public void setOnBattleEnd(Runnable onBattleEnd) {
        this.onBattleEnd = onBattleEnd;
    }
    
    public BattleState getBattleState() {
        return battleState;
    }
    
    public SkillExecutor getSkillExecutor() {
        return skillExecutor;
    }
}