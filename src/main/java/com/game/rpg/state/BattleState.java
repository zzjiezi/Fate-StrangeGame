package com.game.rpg.state;

import com.game.rpg.model.entities.Character;
import com.game.rpg.model.entities.Enemy;
import com.game.rpg.model.enums.BattlePhase;

import java.util.Optional;

public class BattleState {
    private Character player;
    private Character servant;
    private Enemy enemy;
    private int turnCount;
    private BattlePhase currentPhase;
    private double damageBoostMultiplier;
    
    public BattleState() {
        this.turnCount = 0;
        this.currentPhase = BattlePhase.PLAYER_TURN;
        this.damageBoostMultiplier = 1.0;
    }
    
    public void initializeBattle(Character player, Character servant, Enemy enemy) {
        this.player = player;
        this.servant = servant;
        this.enemy = enemy;
        this.turnCount = 0;
        this.currentPhase = BattlePhase.PLAYER_TURN;
        this.damageBoostMultiplier = 1.0;
    }
    
    public void updateHP(String characterId, int newHp) {
        if (player != null && player.getId().equals(characterId)) {
            int damage = player.getHp() - newHp;
            if (damage > 0) {
                player.takeDamage(damage);
            } else {
                player.heal(-damage);
            }
        } else if (servant != null && servant.getId().equals(characterId)) {
            int damage = servant.getHp() - newHp;
            if (damage > 0) {
                servant.takeDamage(damage);
            } else {
                servant.heal(-damage);
            }
        } else if (enemy != null && enemy.getId().equals(characterId)) {
            int damage = enemy.getHp() - newHp;
            if (damage > 0) {
                enemy.takeDamage(damage);
            }
        }
    }
    
    public void updateMP(String characterId, int newMp) {
        if (player != null && player.getId().equals(characterId)) {
            int cost = player.getMp() - newMp;
            if (cost > 0) {
                player.consumeMP(cost);
            } else {
                player.restoreMP(-cost);
            }
        } else if (servant != null && servant.getId().equals(characterId)) {
            int cost = servant.getMp() - newMp;
            if (cost > 0) {
                servant.consumeMP(cost);
            } else {
                servant.restoreMP(-cost);
            }
        }
    }
    
    public Optional<Character> getCurrentActor() {
        if (currentPhase == BattlePhase.PLAYER_TURN) {
            return Optional.ofNullable(player);
        } else if (currentPhase == BattlePhase.ENEMY_TURN) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    
    public void nextTurn() {
        turnCount++;
        if (currentPhase == BattlePhase.PLAYER_TURN) {
            currentPhase = BattlePhase.ENEMY_TURN;
        } else if (currentPhase == BattlePhase.ENEMY_TURN) {
            currentPhase = BattlePhase.PLAYER_TURN;
        }
    }
    
    public void endBattle() {
        currentPhase = BattlePhase.BATTLE_END;
    }
    
    public boolean isBattleEnded() {
        return currentPhase == BattlePhase.BATTLE_END ||
               (player != null && !player.isAlive()) ||
               (enemy != null && !enemy.isAlive());
    }
    
    public Character getPlayer() {
        return player;
    }
    
    public Character getServant() {
        return servant;
    }
    
    public Enemy getEnemy() {
        return enemy;
    }
    
    public int getTurnCount() {
        return turnCount;
    }
    
    public BattlePhase getCurrentPhase() {
        return currentPhase;
    }
    
    public void setCurrentPhase(BattlePhase currentPhase) {
        this.currentPhase = currentPhase;
    }
    
    public double getDamageBoostMultiplier() {
        return damageBoostMultiplier;
    }
    
    public void setDamageBoostMultiplier(double damageBoostMultiplier) {
        this.damageBoostMultiplier = Math.max(0.0, damageBoostMultiplier);
    }

    public void setCustomData(String reflectDamage, boolean b) {
    }
}