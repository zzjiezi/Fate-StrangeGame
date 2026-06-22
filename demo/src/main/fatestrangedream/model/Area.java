package model;
import java.util.List;

/**
 * 地图区域类，每个区域包含多个触发器
 */
public class Area {
    private String areaId;
    private String areaName;
    private String areaDescription;
    private List<Trigger> triggers;

    public Area(String areaId, String areaName, String areaDescription, List<Trigger> triggers) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaDescription = areaDescription;
        this.triggers = triggers;
    }

    // Getter
    public String getAreaName() { return areaName; }
    public String getAreaDescription() { return areaDescription; }
    public List<Trigger> getTriggers() { return triggers; }
}