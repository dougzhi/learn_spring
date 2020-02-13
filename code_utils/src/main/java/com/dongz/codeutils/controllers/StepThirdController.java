package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import com.dongz.codeutils.utils.PropertiesUtils;
import com.dongz.codeutils.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepThirdController extends BaseController{

    public Button forwardBtn;
    public Button nextBtn;
    public ListView entities;
    public ListView columns;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<CheckBox> collect = selectedTables.entrySet().stream().map(this::apply).collect(Collectors.toList());
        entities.getItems().addAll(collect);
    }

    private CheckBox apply(Map.Entry<String, Table> item) {
        CheckBox checkBox = new CheckBox();
        checkBox.setText(item.getValue().getName());
        if (selectedVos.containsKey(item.getValue().getName())) {
            checkBox.setSelected(false);
        }
        checkBox.setOnMouseClicked(this::clickTable);
        return checkBox;
    }

    private void clickTable(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        Table table = selectedTables.get(source.getText()).clone();
        if (selectedVos.containsKey(table.getName())) {
            ObservableList items = columns.getItems();
            if (items != null && items.size() != 0 ) {
                selectedVos.remove(table.getName());
                columns.setItems(null);
            } else {
                source.setSelected(true);
                showColumns(table);
            }
        } else {
            selectedVos.put(table.getName(), table);
            showColumns(table);
        }
    }

    public void showColumns(Table table) {
        List<CheckBox> collect = table.getColumns().stream().map(item -> {
            resetIsSelected(item);
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getColumnName());
            checkBox.setId(table.getName());
            checkBox.setSelected(item.isSelected());
            checkBox.setOnMouseClicked(this::clickColumn);
            return checkBox;
        }).collect(Collectors.toList());
        columns.setItems(null);
        columns.setItems(FXCollections.observableArrayList(collect));
    }

    private void resetIsSelected(final Column column) {
        String prefixes = PropertiesUtils.customMap.get("baseEntity");
        column.setSelected(!Arrays.asList(prefixes.toLowerCase().split(",")).contains(column.getColumnName2().toLowerCase()));
        column.setForeignColumn(null);
    }

    private void clickColumn(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        String talbeName = source.getId();
        String columnName = source.getText();
        Table table = selectedVos.get(talbeName);
        List<Column> columns = table.getColumns();
        columns.stream().filter(item -> item.getColumnName().equals(columnName)).forEach(item -> item.setSelected(!item.isSelected()));
    }


    public void forward() throws IOException {
        changeStep(forwardBtn, STEP2);
    }

    public void next(ActionEvent actionEvent) {
        System.out.println("");
    }
}

