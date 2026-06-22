package model;
/*模型层，也叫实体/数据层，负责存放业务实体类、数据模型，
比如你的GameState游戏状态、Monster怪物、Servant从者这类业务对象都放这*/
/*当前游戏的描述*/
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 游戏全局状态，包含所有存档信息，可序列化
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // 当前剧情进度
    private String currentScene;
    // 玩家当前HP/MP
    private int playerHp;
    private int playerMp;
    // 当前从者
    private Character currentServant;
    // 背包
    private Backpack backpack;
    // 当前地图区域
    private String currentArea;
    // 存档时间
    private LocalDateTime saveTime;

    public GameState() {
        this.saveTime = LocalDateTime.now();
    }

    // Getter和Setter
    public String getCurrentScene() { return currentScene; }
    public void setCurrentScene(String currentScene) { this.currentScene = currentScene; }
    public int getPlayerHp() { return playerHp; }
    public void setPlayerHp(int playerHp) { this.playerHp = playerHp; }
    public int getPlayerMp() { return playerMp; }
    public void setPlayerMp(int playerMp) { this.playerMp = playerMp; }
    public Character getCurrentServant() { return currentServant; }
    public void setCurrentServant(Character currentServant) { this.currentServant = currentServant; }
    public Backpack getBackpack() { return backpack; }
    public void setBackpack(Backpack backpack) { this.backpack = backpack; }
    public String getCurrentArea() { return currentArea; }
    public void setCurrentArea(String currentArea) { this.currentArea = currentArea; }
}