package model;
/**
 * 具体角色：鹿目圆（魔法少女小圆）
 */
public class Archer extends Servant {
    private String skillName;
    private String skillDescription;
    private int skillDamage;
    private int skillCost;

    public Archer(String name, int hp, int mp, int attackPower) {
        super(name, hp, mp, attackPower);
        this.skillName = "圆环之理";
        this.skillDescription = "重构世界规则，净化一切罪孽";
        this.skillDamage = 250;
        this.skillCost = 60;
    }

    @Override
    public void uniqueSkill(Servant target) {
        if (this.mp < skillCost) {
            System.out.println("MP不足，无法释放" + skillName);
            return;
        }
        this.mp -= skillCost;
        System.out.println("小圆发动: " + this.skillName + "!");
        System.out.println(this.skillDescription);
        target.takeDamage(skillDamage);
        // 额外效果：净化所有异常状态，回复全体HP
        this.setHp(this.getHp() + 50);
        System.out.println("圆环之理发动，回复自身50点HP");
    }
}