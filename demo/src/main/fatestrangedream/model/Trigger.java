package model;
/**
 * 区域触发器，进入区域后触发剧情/战斗
 */
public class Trigger {
    // 触发器ID
    private String triggerId;
    // 触发器名称
    private String triggerName;
    // 触发类型：剧情/战斗/宝箱
    private String triggerType;
    // 是否已经触发过
    private boolean isTriggered;

    public Trigger(String triggerId, String triggerName, String triggerType) {
        this.triggerId = triggerId;
        this.triggerName = triggerName;
        this.triggerType = triggerType;
        this.isTriggered = false;
    }

    /**
     * 触发触发器
     */
    public void trigger() {
        if (!isTriggered) {
            System.out.println("触发事件: " + triggerName);
            this.isTriggered = true;
            // 这里绑定具体剧情/战斗逻辑，由控制层处理
        }
    }

    // Getter
    public boolean isTriggered() { return isTriggered; }
    public String getTriggerType() { return triggerType; }
}