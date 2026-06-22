package model;
/**
 * 具体从者：阿尔托莉雅·潘德拉贡
 */
public class Saber extends Servant {
    // 独特技能名称
    private String skillName;
    // 技能描述
    private String skillDescription;
    // 技能伤害倍率
    private int skillDamage;
    // 技能MP消耗
    private int skillCost;

    public Saber(String name, int hp, int mp, int attackPower) {
        super(name, hp, mp, attackPower);
        // 初始化专属技能 誓约胜利之剑
        this.skillName = "誓约胜利之剑(Excalibur)";
        this.skillDescription = "闪耀的王剑，对敌人造成毁灭性伤害";
        this.skillDamage = 200;
        this.skillCost = 50;
    }

    @Override
    public void uniqueSkill(Servant target) {
        if (this.mp < this.skillCost) {
            System.out.println("MP不足，无法释放" + this.skillName);
            return;
        }
        this.mp -= this.skillCost;
        System.out.println("Saber释放宝具: " + this.skillName + "!");
        System.out.println(this.skillDescription);
        int totalDamage = this.attackPower + this.skillDamage;
        target.takeDamage(totalDamage);
    }
}