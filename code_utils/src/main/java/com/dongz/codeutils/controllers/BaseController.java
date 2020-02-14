package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
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
    protected static final String STEP3 = "/ui/stepThird.fxml";
    protected static final String STEP4 = "/ui/stepFourth.fxml";
    protected static final String SELECTFOREIGN = "/ui/selectForeign.fxml";

    protected static Map<String, BaseController> controllerMap = new HashMap<>();

    protected static boolean isConnection = false;
    protected static DataBase db;
    protected static List<Table> tables;
    protected static Map<String, Table> tableMap;
    protected static Map<String, Table> selectedTables = new HashMap<>();
    protected static Table selectedTable;
    protected static Column selectedColumn;
    protected static Map<String, Table> selectedVos = new HashMap<>();
    protected static String outPath;

    public Button close;

    protected void reload() {

    }

    public void close() {
        Stage window = (Stage) close.getScene().getWindow();
        window.close();
    }

    protected void changeStep(Button btn, String step) throws IOException {
        Stage secondStage = (Stage) btn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(step));
        Parent load = fxmlLoader.load();
        BaseController controller = fxmlLoader.getController();
        controller.reload();
        controllerMap.put(step, controller);
        Scene scene = new Scene(load);
        secondStage.setScene(scene);
        secondStage.centerOnScreen();
    }

    protected void alert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void openMadel(String step, String title) throws IOException {
        Parent anotherRoot = FXMLLoader.load(getClass().getResource(step));
        Stage anotherStage = new Stage();
        anotherStage.setTitle(title);
        anotherStage.setScene(new Scene(anotherRoot));
        anotherStage.centerOnScreen();
        anotherStage.setAlwaysOnTop(true);
        anotherStage.show();
    }
}
