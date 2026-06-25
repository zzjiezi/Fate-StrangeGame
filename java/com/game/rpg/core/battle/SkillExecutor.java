package com.game.rpg.core.battle;

import com.game.rpg.model.entities.Character;
import com.game.rpg.model.entities.Enemy;
import com.game.rpg.model.entities.Skill;
import com.game.rpg.model.enums.CommandSpellEffect;
import com.game.rpg.model.enums.JudgementResult;
import com.game.rpg.state.BattleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SkillExecutor.class);
    
    private final JudgementSystem judgementSystem;
    
    public SkillExecutor() {
        this.judgementSystem = new JudgementSystem();
    }
    
    public int executeNormalAttack(Character attacker, Enemy target) {
        judgementSystem.startJudgement();
        return attacker.getAttackPower();
    }
    
    public int executeNormalAttack(Enemy attacker, Character target) {
        return attacker.getAttackPower();
    }
    
    public int executeSkill(Character caster, Skill skill, Enemy target, JudgementResult judgement) {
        if (!caster.canCastSkill(skill)) {
            logger.warn("Cannot cast skill: insufficient MP");
            return 0;
        }
        
        caster.consumeMP(skill.getMpCost());
        int damage = judgementSystem.calculateDamage(skill.getBaseDamage(), judgement);
        target.takeDamage(damage);
        
        logger.info("Skill executed: {} dealt {} damage to {}", 
                skill.getName(), damage, target.getName());
        
        return damage;
    }
    
    public int executeNoblePhantasm(Character caster, Skill noblePhantasm, Enemy target, 
                                    JudgementResult judgement) {
        if (!caster.canCastSkill(noblePhantasm)) {
            logger.warn("Cannot cast noble phantasm: insufficient MP");
            return 0;
        }
        
        caster.consumeMP(noblePhantasm.getMpCost());
        int damage = judgementSystem.calculateDamage(noblePhantasm.getBaseDamage(), judgement);
        damage = (int) Math.round(damage * 1.5);
        target.takeDamage(damage);
        
        logger.info("Noble Phantasm executed: {} dealt {} damage to {}", 
                noblePhantasm.getName(), damage, target.getName());
        
        return damage;
    }
    
    public void executeCommandSpell(Character caster, CommandSpellEffect effect, 
                                    BattleState battleState, Enemy target) {
        if (!caster.useCommandSpell()) {
            logger.warn("No command spells remaining");
            return;
        }
        
        switch (effect) {
            case REVIVE_SERVANT:
                reviveServant(battleState);
                break;
            case DAMAGE_BOOST:
                applyDamageBoost(battleState);
                break;
            case HEAL_HP:
                healHP(caster, 50);
                break;
            case MIND_CONTROL:
                applyMindControl(target);
                break;
            case REFLECT_DAMAGE:
                applyReflectDamage(battleState);
                break;
            case PUPPET:
                applyPuppet(target, caster);
                break;
        }
        
        logger.info("Command spell executed: {}", effect.getDisplayName());
    }
    
    private void reviveServant(BattleState battleState) {
        Character servant = battleState.getServant();
        if (servant != null && !servant.isAlive()) {
            servant.heal(servant.getMaxHp() / 2);
            logger.info("Servant revived with {} HP", servant.getHp());
        }
    }
    
    private void applyDamageBoost(BattleState battleState) {
        battleState.setDamageBoostMultiplier(1.5);
        logger.info("Damage boost applied: 50% increase");
    }
    
    private void healHP(Character character, int amount) {
        character.heal(amount);
        logger.info("HP restored: {} (current HP: {})", amount, character.getHp());
    }
    
    private void applyMindControl(Enemy enemy) {
        enemy.takeDamage(enemy.getMaxHp() / 10);
        logger.info("Mind control applied: enemy attack reduced by 30%");
    }
    
    private void applyReflectDamage(BattleState battleState) {
        battleState.setCustomData("reflectDamage", true);
        logger.info("Reflect damage activated");
    }
    
    private void applyPuppet(Enemy enemy, Character caster) {
        int damage = enemy.getMaxHp() / 10;
        enemy.takeDamage(damage);
        caster.takeDamage(caster.getMaxHp() / 10);
        logger.info("Puppet effect: dealt {} damage to enemy, caster lost 10% HP", damage);
    }
    
    public JudgementSystem getJudgementSystem() {
        return judgementSystem;
    }
}