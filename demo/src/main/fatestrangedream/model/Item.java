package model;
/**
 * 物品实体类，用于背包系统
 */
public class Item {
    // 物品唯一ID
    private String itemId;
    // 物品名称
    private String itemName;
    // 物品描述
    private String description;
    // 物品可堆叠数量
    private int count;

    public Item(String itemId, String itemName, String description, int count) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.count = count;
    }

    // Getter和Setter
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getDescription() { return description; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}