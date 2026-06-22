
package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
public class MyGameUI extends Application {
        @Override
        public void start(Stage primaryStage) {
            // 根布局：BorderPane
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(10));

            //顶部信息面板 - GridPane
            GridPane infoPane = new GridPane();
            infoPane.setPadding(new Insets(10));
            infoPane.setHgap(15);
            infoPane.setVgap(10);
            infoPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

            //添加角色信息
            infoPane.add(new Label("角色名称:"), 0, 0);
            Label nameLabel = new Label("勇者亚瑟");
            nameLabel.setFont(Font.font(14));
            infoPane.add(nameLabel, 1, 0);

            infoPane.add(new Label("等级:"), 0, 1);
            Label levelLabel = new Label("Lv.10");
            levelLabel.setFont(Font.font(14));
            infoPane.add(levelLabel, 1, 1);

            infoPane.add(new Label("生命值:"), 2, 0);
            Label hpLabel = new Label("100/100");
            hpLabel.setFont(Font.font(14));
            hpLabel.setTextFill(Color.RED);
            infoPane.add(hpLabel, 3, 0);

            infoPane.add(new Label("魔法值:"), 2, 1);
            Label mpLabel = new Label("80/80");
            mpLabel.setFont(Font.font(14));
            mpLabel.setTextFill(Color.BLUE);
            infoPane.add(mpLabel, 3, 1);
            root.setTop(infoPane);

            // 2. 中部任务区域 - VBox(ListView + Button)
            VBox taskArea = new VBox(10);
            taskArea.setPadding(new Insets(10));

            ListView<String> taskList = new ListView<>();
            taskList.getItems().addAll("主线任务:消灭黑暗领主", "支线任务:收集魔法水晶", "日常任务:帮助村民");
            taskList.setPrefHeight(200);

            Button goButton = new Button("前往");
            goButton.setFont(Font.font(14));
            goButton.setPrefWidth(100);
            taskArea.getChildren().addAll(taskList, goButton);
            root.setCenter(taskArea);

            // 3. 底部按钮区域 - HBox(三个功能按钮)
            HBox bottomButtons = new HBox(20);
            bottomButtons.setPadding(new Insets(10));
            bottomButtons.setAlignment(javafx.geometry.Pos.CENTER);

            Button bagButton = new Button("背包");
            Button skillButton = new Button("技能");
            Button settingButton = new Button("设置");

            // 设置底部按钮样式
            for (Button btn : new Button[]{bagButton, skillButton, settingButton}) {
                btn.setFont(Font.font(14));
                btn.setPrefSize(80, 30);
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
            }
            bottomButtons.getChildren().addAll(bagButton, skillButton, settingButton);
            root.setBottom(bottomButtons);

            // 窗口设置
            Scene scene = new Scene(root, 500, 400);
            primaryStage.setTitle("英雄大陆——我的冒险");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // 固定窗口大小
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
