package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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
    public ListView cloumns;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tables == null) {
            try {
                tables = DataBaseUtils.getDbInfo(db);
                tableMap = tables.stream().collect(Collectors.toMap(Table::getName, item -> item));
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "列表查询失败");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        List<CheckBox> collect = tables.stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getName());
            if (selectedTables.contains(tableMap.get(item.getName()))) {
                checkBox.setSelected(true);
            }
            checkBox.setOnMouseClicked(this::clickTable);
            return checkBox;
        }).collect(Collectors.toList());
        entities.getItems().addAll(collect);
    }

    private void clickTable(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        Table table = tableMap.get(source.getText());
        if (selectedTables.contains(table)) {
            selectedTables.remove(table);
            cloumns.getItems().removeAll();
        } else {
            selectedTables.add(table);
            showColumns(table);
        }
    }

    private void showColumns(Table table) {
        List<CheckBox> collect = table.getColumns().stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getColumnName());
            checkBox.setSelected(true);
//            checkBox.setOnMouseClicked(event -> clickTable(event));
            return checkBox;
        }).collect(Collectors.toList());
        cloumns.getItems().removeAll();
        cloumns.getItems().addAll(collect);
    }


    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    public void next(ActionEvent actionEvent) {
    }
}

