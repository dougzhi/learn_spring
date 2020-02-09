package com.dongz.codeutils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author dong
 * @date 2020/2/8 23:46
 * @desc
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("你好");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(button);

        Scene scene = new Scene(stackPane, 200, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
