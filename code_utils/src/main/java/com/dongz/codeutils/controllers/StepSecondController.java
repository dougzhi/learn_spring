package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ListView columns;
    public CheckBox isExtend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isExtend.setVisible(false);
        if (tables == null) {
            try {
                tables = DataBaseUtils.getDbInfo(db);
                tableMap = tables.stream().collect(Collectors.toMap(Table::getClassName, item -> item));
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "列表查询失败");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        List<CheckBox> collect = tables.stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getClassName());
            if (selectedTables.containsKey(item.getClassName())) {
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
        ObservableList items = columns.getItems();
        if (selectedTables.containsKey(table.getClassName())) {
            if (items != null && items.size() != 0 && table.getClassName().equals(columns.getId())){
                selectedTables.remove(table.getClassName());
                columns.setItems(null);
                columns.setId(null);
                selectedTable = null;
                isExtend.setVisible(false);
            } else {
                source.setSelected(true);
                showColumns(table);
                isExtend.setVisible(true);
                isExtend.setSelected(table.isExtendsBase());
            }
        } else {
            selectedTable = table;
            selectedTables.put(table.getClassName(), table);
            showColumns(table);
            isExtend.setVisible(true);
            isExtend.setSelected(table.isExtendsBase());
        }
    }

    public void showColumns(Table table) {
        List<BorderPane> collect = table.getColumns().stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getFieldName());
            checkBox.setId(table.getClassName());
            checkBox.setDisable(item.getColumnKey() != null);
            checkBox.setSelected(item.isSelected());
            checkBox.setOnMouseClicked(this::clickColumn);
            BorderPane bp = new BorderPane();
            bp.setLeft(checkBox);
            addColumnBtn(item, bp);
            return bp;
        }).collect(Collectors.toList());
        columns.setItems(null);
        columns.setId(table.getClassName());
        columns.setItems(FXCollections.observableArrayList(collect));
    }

    private void addColumnBtn(Column item,final BorderPane bp) {
        if (item.getColumnKey() == null) {
            if (item.getForeignColumn() == null) {
                Button button = new Button("外键关联");
                button.setId(item.getFieldName());
                button.setOnMouseClicked(event -> {
                    try {
                        addForeign(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                bp.setRight(button);
            }
            else{
                bp.setCenter(new Button(item.getForeignColumn().getTable().getClassName()+"."+item.getForeignColumn().getColumn().getFieldName()));
                Button button = new Button("删除");
                button.setId(item.getFieldName());
                button.setOnMouseClicked(this::removeForeign);
                bp.setRight(button);
            }
        }
    }

    private void clickColumn(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        String talbeName = source.getId();
        String columnName = source.getText();
        Table table = selectedTables.get(talbeName);
        List<Column> columns = table.getColumns();
        columns.stream().filter(item -> item.getFieldName().equals(columnName)).forEach(item -> item.setSelected(!item.isSelected()));
    }


    public void forward() throws IOException {
        changeStep(forwardBtn, STEP1);
    }

    public void next() throws IOException {
        if (selectedTables.isEmpty()) {
            alert(Alert.AlertType.WARNING, "请选择要生成的实体类");
            return;
        }
        changeStep(nextBtn, STEP3);
    }

    public void isExtend() {
        selectedTable.setExtendsBase(!selectedTable.isExtendsBase());
    }

    public void addForeign(MouseEvent event) throws IOException {
        String source = ((Button) event.getSource()).getId();
        selectedColumn = selectedTable.getColumns().stream().filter(item -> item.getFieldName().equals(source)).findFirst().get();
        openMadel(SELECTFOREIGN, "selectForeign");
    }

    private void removeForeign(MouseEvent event) {
        String source = ((Button) event.getSource()).getId();
        selectedTable.getColumns().stream().filter(item -> item.getFieldName().equals(source)).findFirst().get().setForeignColumn(null);
        showColumns(selectedTable);
    }
}

