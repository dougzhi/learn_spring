package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.FXCollections;
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
public class SelectForeignController extends BaseController{

    public ListView entities;
    public ListView cloumns;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<CheckBox> collect = tables.stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getName());
            if (selectedTables.containsKey(item.getName())) {
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
        if (selectedTables.containsKey(table.getName())) {
            selectedTables.remove(table.getName());
            cloumns.setItems(null);
            selectedTable = null;
        } else {
            selectedTable = table;
            selectedTables.put(table.getName(), table);
            showColumns(table);
        }
    }

    private void showColumns(Table table) {
        List<CheckBox> collect = table.getColumns().stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getColumnName());
            checkBox.setId(table.getName());
            checkBox.setDisable(item.getColumnKey() != null);
            checkBox.setSelected(item.isSelected());
            checkBox.setOnMouseClicked(event -> clickColumn(event));
            return checkBox;
        }).collect(Collectors.toList());
        cloumns.setItems(null);
        cloumns.setItems(FXCollections.observableArrayList(collect));
    }

    private void clickColumn(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        String talbeName = source.getId();
        String columnName = source.getText();
        Table table = selectedTables.get(talbeName);
        List<Column> columns = table.getColumns();
        columns.stream().filter(item -> item.getColumnName().equals(columnName)).forEach(item -> item.setSelected(!item.isSelected()));
    }

    public void submit(ActionEvent actionEvent) {
    }
}

