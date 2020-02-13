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
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class SelectForeignController extends BaseController{

    public ListView entities;
    public ListView columns;
    public Label table;
    public Label column;

    private Table foreignTable;
    private Column foreignColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setText(selectedTable.getClassName());
        column.setText(selectedColumn.getFieldName());
        ToggleGroup tableGroup = new ToggleGroup();
        List<RadioButton> collect = tables.stream().map(item -> {
            RadioButton radioButton = new RadioButton(item.getClassName());
            if (item.getClassName().equals(selectedTable.getClassName())) {
                radioButton.setDisable(true);
            }
            radioButton.setToggleGroup(tableGroup);
            radioButton.setId(item.getClassName());
            radioButton.setOnMouseClicked(this::clickTable);
            return radioButton;
        }).collect(Collectors.toList());
        entities.getItems().addAll(collect);
    }

    private void clickTable(MouseEvent event) {
        RadioButton source = (RadioButton) event.getSource();
        foreignTable = tableMap.get(source.getId());
        showColumns(foreignTable);
    }

    private void showColumns(Table table) {
        ToggleGroup columnGroup = new ToggleGroup();
        List<RadioButton> collect = table.getColumns().stream().map(item -> {
            RadioButton radioButton = new RadioButton(item.getFieldName());
            radioButton.setToggleGroup(columnGroup);
            radioButton.setId(item.getFieldName());
            radioButton.setOnMouseClicked(this::clickColumn);
            return radioButton;
        }).collect(Collectors.toList());
        columns.setItems(null);
        columns.setItems(FXCollections.observableArrayList(collect));
    }

    private void clickColumn(MouseEvent event) {
        RadioButton source = (RadioButton) event.getSource();
        String columnName = source.getId();
        foreignColumn = foreignTable.getColumns().stream().filter(item -> item.getFieldName().equals(columnName)).findFirst().get();
    }

    public void submit() {
        if (foreignTable == null) {
            alert(Alert.AlertType.ERROR,"请选择关联表！");
            return;
        }
        if (foreignColumn == null) {
            alert(Alert.AlertType.ERROR,"请选择关联字段！");
            return;
        }

        Column.ForeignColumn column = new Column.ForeignColumn();
        column.setTable(foreignTable);
        column.setColumn(foreignColumn);

        selectedColumn.setForeignColumn(column);

        foreignTable = null;
        foreignColumn = null;

        ((StepSecondController)controllerMap.get(STEP2)).showColumns(selectedTable);

        close();
    }
}

