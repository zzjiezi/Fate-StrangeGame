package model;
import java.time.LocalDateTime;

/**
 * 成就实体类
 */
public class Achievement {
    // 成就唯一ID
    private String achievementId;
    // 成就名称
    private String name;
    // 成就描述
    private String description;
    // 是否解锁
    private boolean unlocked;
    // 解锁时间
    private LocalDateTime unlockTime;

    public Achievement(String achievementId, String name, String description) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
        this.unlocked = false;
    }

    /**
     * 解锁成就
     */
    public void unlock() {
        if (!this.unlocked) {
            this.unlocked = true;
            this.unlockTime = LocalDateTime.now();
            System.out.println("解锁成就: [" + this.name + "] " + this.description);
        }
    }

    // Getter和Setter
    public String getAchievementId() { return achievementId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }
    public LocalDateTime getUnlockTime() { return unlockTime; }
}