package view;
/*视图层，负责和界面展示相关的代码，你的GameView游戏界面相关类就放在这里*/
/*图形界面*/
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
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

public class GameView extends Application {
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
        taskList.getItems().addAll("主线任务:击退敌人", "支线任务:收集魔法水晶", "日常任务:帮助村民");
        taskList.setPrefHeight(200);

        Button goButton = new Button("前往");
        goButton.setFont(Font.font(14));
        goButton.setPrefWidth(100);
        taskArea.getChildren().addAll(taskList, goButton);
        // ========== 中部游戏主界面：背景图 + 文本提示 + 选项按钮 ==========
// 1. 导入你的自定义背景图，把图片放到resources目录下，这里替换成你的图片路径
        Image backgroundImage = new Image(getClass().getResource("/title_bg.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
// 让背景图适配窗口大小（你的窗口是500*400，调整背景图尺寸）
        backgroundView.setFitWidth(500);
        backgroundView.setFitHeight(300);
        backgroundView.setPreserveRatio(true);

// 2. 提示文本区域（你的开场白说明）
        TextArea tipArea = new TextArea();
        tipArea.setText("""
本作为RPG类,包含多个不同游戏,动漫的梗和要素,不限于:Fate系列,魔法少女小圆,怪诞小镇,崩坏:星穹铁道,传说之下等.
所有的画面都是人工绘制, 受限于精力所以有些粗糙.
如果你能接受—夹带众多私货,且有不同作品的大乱斗,对原作的一些浅薄的解构,幼稚的文笔以及多变的画风的话.
就请开始游戏吧!希望你能在这一场冒险中获得些许愉悦~
""");
        tipArea.setPrefHeight(120);
        tipArea.setEditable(false); // 禁止玩家编辑

// 3. 功能按钮区域
        HBox menuButtons = new HBox(20);
        menuButtons.setPadding(new Insets(15));
        menuButtons.setAlignment(javafx.geometry.Pos.CENTER);

        Button newGameBtn = new Button("新游戏");
        Button loadBtn = new Button("读档");
        Button exitBtn = new Button("退出游戏");
        Button achievementBtn = new Button("成就查询");

// 设置按钮样式（和你底部按钮风格统一）
        for (Button btn : new Button[]{newGameBtn, loadBtn, exitBtn, achievementBtn}) {
            btn.setFont(Font.font(14));
            btn.setPrefSize(100, 35);
            btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
        }

// 给「开始新游戏」绑定点击事件，跳转到选择界面（你可以后续扩展逻辑）
        newGameBtn.setOnAction(e -> {
            // 这里替换成你的初遇Saber场景跳转逻辑，示例弹出提示：
            System.out.println("点击了开始新游戏，跳转到开幕场景");
        });
        exitBtn.setOnAction(e -> primaryStage.close()); // 退出游戏

        menuButtons.getChildren().addAll(newGameBtn, loadBtn, exitBtn, achievementBtn);

// 把所有元素堆叠到VBox中作为主区域
        VBox gameView = new VBox(10);
        gameView.getChildren().addAll(backgroundView, tipArea, menuButtons);
        root.setCenter(gameView);

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
        primaryStage.setTitle("Fate/Strange Dream");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // 固定窗口大小
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}