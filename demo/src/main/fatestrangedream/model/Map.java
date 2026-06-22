package model;
import java.util.List;

/**
 * 地图类，管理所有区域和触发器
 */
public class Map {
    // 所有区域列表
    private List<Area> areas;
    // 当前所在区域
    private Area currentArea;

    public Map(List<Area> areas) {
        this.areas = areas;
    }

    /**
     * 进入指定名称的区域
     */
    public boolean enterArea(String areaName) {
        for (Area area : areas) {
            if (area.getAreaName().equals(areaName)) {
                this.currentArea = area;
                System.out.println("\n进入区域: " + areaName);
                System.out.println(area.getAreaDescription());
                return true;
            }
        }
        System.out.println("找不到名为" + areaName + "的区域");
        return false;
    }

    /**
     * 检查当前区域所有触发器
     */
    public void checkTrigger() {
        if (currentArea == null) return;
        for (Trigger trigger : currentArea.getTriggers()) {
            if (!trigger.isTriggered()) {
                trigger.trigger();
            }
        }
    }

    public Area getCurrentArea() { return currentArea; }
}