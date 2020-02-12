package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    public Label table;
    public Label column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setText(selectedTable.getName());
        column.setText(selectedColumn);
        ToggleGroup group = new ToggleGroup();
        List<RadioButton> collect = tables.stream().map(item -> {
            RadioButton radioButton = new RadioButton(item.getName());
            if (item.getName().equals(selectedTable.getName())) {
                radioButton.setDisable(true);
            }
            radioButton.setToggleGroup(group);
            radioButton.setOnMouseClicked(this::clickTable);
            return radioButton;
        }).collect(Collectors.toList());
        entities.getItems().addAll(collect);
    }

    private void clickTable(MouseEvent event) {
        RadioButton source = (RadioButton) event.getSource();
        Table table = tableMap.get(source.getText());
        showColumns(table);
    }

    private void showColumns(Table table) {
        ToggleGroup group = new ToggleGroup();
        List<RadioButton> collect = table.getColumns().stream().map(item -> {
            RadioButton radioButton = new RadioButton(item.getColumnName());
            radioButton.setId(table.getName());
            radioButton.setOnMouseClicked(event -> clickColumn(event));
            radioButton.setToggleGroup(group);
            return radioButton;
        }).collect(Collectors.toList());
        cloumns.setItems(null);
        cloumns.setItems(FXCollections.observableArrayList(collect));
    }

    private void clickColumn(MouseEvent event) {
        RadioButton source = (RadioButton) event.getSource();
        String talbeName = source.getId();
        String columnName = source.getText();
        Table table = selectedTables.get(talbeName);
        List<Column> columns = table.getColumns();
        columns.stream().filter(item -> item.getColumnName().equals(columnName)).forEach(item -> item.setSelected(!item.isSelected()));
    }

    public void submit(ActionEvent actionEvent) {
    }
}

