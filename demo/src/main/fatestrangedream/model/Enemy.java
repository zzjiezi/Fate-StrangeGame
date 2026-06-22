package model;
/**
 * 敌人抽象基类，所有敌人继承此类
 */
public abstract class Enemy extends Servant {
    public Enemy(String name, int hp, int mp, int attackPower) {
        super(name, hp, mp, attackPower);
    }

    // 敌人可定义自己的AI行为逻辑
    public abstract void doAction(Servant player);
}