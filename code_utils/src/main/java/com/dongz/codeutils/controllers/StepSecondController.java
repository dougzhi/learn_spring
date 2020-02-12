package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepSecondController extends BaseController{

    public Button forwardBtn;
    public Button nextBtn;
    public ListView entities;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Table> tables = DataBaseUtils.getDbInfo(db);
            List<String> tableNames = tables.stream().map(Table::getName).collect(Collectors.toList());
            entities.getItems().addAll(tableNames);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "列表查询失败");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    public void next(ActionEvent actionEvent) {
    }
}

