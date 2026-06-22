package model;
/*退出后仍可以读出*/
/**
 * 所有角色的抽象基类，定义通用属性和方法
 */
public abstract class Servant {
    // 角色名称
    protected String name;
    // 生命值(HP)
    protected int hp;
    // 魔法值(MP，能量条)
    protected int mp;
    // 基础攻击力
    protected int attackPower;

    public Servant(String name, int hp, int mp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.attackPower = attackPower;
    }

    /**
     * 普通攻击方法，子类可重写
     */
    public void attack(Servant target) {
        System.out.println(this.name + "对" + target.name + "发起普通攻击!");
        target.takeDamage(this.attackPower);
    }

    /**
     * 闪避方法
     */
    public boolean dodge() {
        // 默认按概率判定，子类可重写闪避逻辑
        return Math.random() > 0.7;
    }

    /**
     * 抽象独特技能，所有子类必须实现
     */
    public abstract void uniqueSkill(Servant target);

    /**
     * 受到伤害，扣减生命值
     */
    public void takeDamage(int damage) {
        this.hp -= damage;
        System.out.println(this.name + "受到了" + damage + "点伤害，剩余HP: " + this.hp);
    }

    /**
     * 判断角色是否存活
     */
    public boolean isAlive() {
        return this.hp > 0;
    }

    // Getter和Setter
    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getMp() { return mp; }
    public void setMp(int mp) { this.mp = mp; }
    public int getAttackPower() { return attackPower; }
}