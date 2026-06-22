package model;
/**
 * 具体角色：Bill Cipher（怪诞小镇）
 */
public class Caster extends Servant {
    private String skillName;
    private String skillDescription;
    private int skillDamage;
    private int skillCost;

    public Caster(String name, int hp, int mp, int attackPower) {
        super(name, hp, mp, attackPower);
        this.skillName = "梦境入侵";
        this.skillDescription = "扭曲现实，粉碎对手的心智";
        this.skillDamage = 180;
        this.skillCost = 40;
    }

    @Override
    public void uniqueSkill(Servant target) {
        if (this.mp < skillCost) {
            System.out.println("能量不足，无法释放" + skillName);
            return;
        }
        this.mp -= skillCost;
        System.out.println("Bill发动: " + this.skillName + "!");
        System.out.println(this.skillDescription);
        // 多重攻击效果
        for (int i = 0; i < 3; i++) {
            target.takeDamage(skillDamage / 3);
        }
    }
}