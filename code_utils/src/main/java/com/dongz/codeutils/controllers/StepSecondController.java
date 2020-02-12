package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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
    public CheckBox isExtend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isExtend.setVisible(false);
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
            isExtend.setVisible(false);
        } else {
            selectedTable = table;
            selectedTables.put(table.getName(), table);
            showColumns(table);
            isExtend.setVisible(true);
            isExtend.setSelected(table.isExtendsBase());
        }
    }

    private void showColumns(Table table) {
        List<BorderPane> collect = table.getColumns().stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getColumnName());
            checkBox.setId(table.getName());
            checkBox.setDisable(item.getColumnKey() != null);
            checkBox.setSelected(item.isSelected());
            checkBox.setOnMouseClicked(this::clickColumn);
            Button button = new Button("外键关联");
            button.setOnMouseClicked(event -> {
                try {
                    addForeign(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            BorderPane bp = new BorderPane();
            bp.setLeft(checkBox);
            bp.setRight(button);
            return bp;
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


    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    public void next(ActionEvent actionEvent) {
    }

    public void isExcend() {
        selectedTable.setExtendsBase(!selectedTable.isExtendsBase());
    }

    public void addForeign(Event event) throws IOException {
        openMadel(SELECTFOREIGN,"selectForeign");
    }
}

