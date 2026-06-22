package util;

import model.GameState;

import java.io.*;

/**
 * 存档工具类，负责IO流读写游戏存档
 */
public class SaveManager {

    /**
     * 保存游戏状态到本地文件
     * @param state 游戏状态对象
     * @param filePath 存档文件路径
     * @return 是否保存成功
     */
    public static boolean saveGame(GameState state, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(state);
            System.out.println("游戏存档成功，保存路径: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("存档失败: " + e.getMessage());
            return false;
        }
    }}

/**
 * 从本地文件读取游戏存档
 * @param filePath 存档文件路径
 * @return 读取*/