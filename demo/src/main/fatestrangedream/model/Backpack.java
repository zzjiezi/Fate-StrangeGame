package model;
import java.util.ArrayList;
import java.util.List;

/**
 * 背包类，管理玩家物品存储
 */
public class Backpack {
    // 存储物品列表
    private List<Item> items;
    // 背包最大容量
    private int maxSize;

    public Backpack(int maxSize) {
        this.items = new ArrayList<>();
        this.maxSize = maxSize;
    }

    /**
     * 添加物品到背包
     */
    public boolean addItem(Item item) {
        if (items.size() >= maxSize) {
            System.out.println("背包已满，无法添加" + item.getItemName());
            return false;
        }
        // 检查是否已有同ID物品，如果有直接叠加数量
        for (Item existItem : items) {
            if (existItem.getItemId().equals(item.getItemId())) {
                existItem.setCount(existItem.getCount() + item.getCount());
                System.out.println("成功添加" + item.getItemName() + " x" + item.getCount());
                return true;
            }
        }
        items.add(item);
        System.out.println("成功添加" + item.getItemName() + " x" + item.getCount());
        return true;
    }

    /**
     * 根据物品名称移除物品
     */
    public boolean removeItem(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equals(itemName)) {
                items.remove(item);
                System.out.println("成功移除" + itemName);
                return true;
            }
        }
        System.out.println("背包中不存在" + itemName);
        return false;
    }

    /**
     * 展示背包中所有物品
     */
    public void showItems() {
        System.out.println("===== 你的背包 =====");
        if (items.isEmpty()) {
            System.out.println("背包里什么都没有...");
            return;
        }
        for (Item item : items) {
            System.out.printf("%s x%d : %s\n", item.getItemName(), item.getCount(), item.getDescription());
        }
    }

    public List<Item> getItems() { return items; }
}