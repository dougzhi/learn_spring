package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.entitys.db.Table;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/12 00:52
 * @desc 第二
 */
public abstract class BaseController implements Initializable {
    protected static final String STEP1 = "/ui/stepFirst.fxml";
    protected static final String STEP2 = "/ui/stepSecond.fxml";

    protected static boolean isConnection = false;
    protected static DataBase db;
    public Button close;
    protected static List<Table> tables;
    protected static Map<String, Table> tableMap;
    protected static Map<String, Table> selectedTables = new HashMap<>();

    protected void reload() {

    }

    public void close() {
        Stage window = (Stage) close.getScene().getWindow();
        window.close();
    }

    protected void changeStep(Button btn,String step) throws IOException {
        Stage secondStage = (Stage) btn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(step));
        Parent load = fxmlLoader.load();
        BaseController controller = fxmlLoader.getController();
        controller.reload();
        Scene scene = new Scene(load);
        secondStage.setScene(scene);
    }

    protected void alert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
