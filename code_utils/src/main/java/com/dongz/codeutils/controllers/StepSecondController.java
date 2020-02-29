package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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
    public VBox foreignBox;
    public CheckBox isOnly;
    public Label foreignInfo;
    public Button foreignBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isExtend.setVisible(false);
        foreignBox.setVisible(false);
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
        init();
    }

    public void init() {
        List<CheckBox> collect = tables.stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getClassName());
            if (selectedTables.containsKey(item.getClassName())) {
                checkBox.setSelected(true);
            }
            checkBox.setOnMouseClicked(this::clickTable);
            return checkBox;
        }).collect(Collectors.toList());
        entities.setItems(FXCollections.observableArrayList(collect));
    }

    private void clickTable(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        Table table = tableMap.get(source.getText());
        ObservableList items = columns.getItems();
        if (selectedTables.containsKey(table.getClassName())
                && items != null && items.size() != 0
                && table.getClassName().equals(columns.getId())) {
            if (checkForeign(table)) {
                source.setSelected(true);
                alert(Alert.AlertType.WARNING, "存在外键关联，禁止删除！");
                return;
            }
            selectedTables.remove(table.getClassName());
            columns.setItems(null);
            columns.setId(null);
            selectedTable = null;
            isExtend.setVisible(false);
        } else {
            selectedTable = table;
            selectedTables.putIfAbsent(table.getClassName(), table);
            getForeignList(table).forEach(item -> selectedTables.putIfAbsent(item.getClassName(), item));
            showColumns(table);
            isExtend.setVisible(true);
            isExtend.setSelected(table.isExtendsBase());
            init();
        }
    }

    private boolean checkForeign(final Table table) {
        AtomicBoolean flag = new AtomicBoolean(false);
        selectedTables.entrySet().parallelStream().forEach(item -> item.getValue().getColumns().parallelStream().forEach(it -> {
            Column.ForeignColumn foreignColumn = it.getForeignColumn();
            if (foreignColumn != null && foreignColumn.getTable() != null && foreignColumn.getTable().getClassName().equals(table.getClassName())) {
                flag.set(true);
            }
        }));
        return flag.get();
    }

    private List<Table> getForeignList(final Table table) {
        return table.getColumns().stream().filter(item -> item.getForeignColumn() != null && item.getForeignColumn().getTable() != null).map(item -> item.getForeignColumn().getTable()).collect(Collectors.toList());
    }

    protected void showColumns(Table table) {
        List<BorderPane> collect = table.getColumns().stream().map(item -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(item.getFieldName());
            checkBox.setId(table.getClassName());
            checkBox.setDisable(item.getColumnKey() != null);
            checkBox.setSelected(item.isSelected());
            checkBox.setOnMouseClicked(this::clickColumn);
            BorderPane bp = new BorderPane();
            bp.setLeft(checkBox);
            return bp;
        }).collect(Collectors.toList());
        columns.setItems(null);
        columns.setId(table.getClassName());
        columns.setItems(FXCollections.observableArrayList(collect));
    }

    private void clickColumn(MouseEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        String talbeName = source.getId();
        String columnName = source.getText();
        Table table = selectedTables.get(talbeName);
        List<Column> columns = table.getColumns();
        columns.stream().filter(item -> item.getFieldName().equals(columnName)).forEach(item -> item.setSelected(!item.isSelected()));
        Column column= columns.stream().filter(item -> item.getFieldName().equals(columnName)).findFirst().orElseGet(null);
        if (column == null) {
            alert(Alert.AlertType.WARNING, "所选字段不存在");
            return;
        }
        if (column.equals(selectedColumn)) {
            foreignBox.setVisible(false);
            source.setSelected(false);
            selectedColumn = null;
        } else {
            selectedColumn = column;
            source.setSelected(true);
            foreignBox.setVisible(true);

            isOnly.setSelected(selectedColumn.isOnly());
            getForeign();
        }
    }

    protected void getForeign() {
        foreignBtn.setId(selectedColumn.getFieldName());
        if (selectedColumn.getColumnKey() == null) {
            if (selectedColumn.getForeignColumn() == null) {
                foreignBtn.setText("新增");
                foreignInfo.setText("无");
                foreignBtn.setOnMouseClicked(event -> {
                    try {
                        addForeign(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            else{
                foreignInfo.setText(selectedColumn.getForeignColumn().getTable().getClassName() + "." + selectedColumn.getForeignColumn().getColumn().getFieldName());
                foreignBtn.setText("删除");
                foreignBtn.setOnMouseClicked(this::removeForeign);
            }
        }
    }

    public void forward() throws IOException {
        selectedColumn = null;
        selectedTable = null;
        changeStep(forwardBtn, STEP1);
    }

    public void next() throws IOException {
        if (selectedTables.isEmpty()) {
            alert(Alert.AlertType.WARNING, "请选择要生成的实体类");
            return;
        }
        selectedColumn = null;
        selectedTable = null;
        changeStep(nextBtn, STEP3);
    }

    public void isExtend() {
        selectedTable.setExtendsBase(!selectedTable.isExtendsBase());
    }

    public void isOnly() {
        selectedColumn.setOnly(!selectedColumn.isOnly());
    }

    /**
     * 新增外键
     * @param event
     * @throws IOException
     */
    public void addForeign(MouseEvent event) throws IOException {
        if (selectedColumn == null) {
            alert(Alert.AlertType.WARNING, "请选择字段");
            return;
        }
        openMadel(SELECTFOREIGN, "selectForeign");
    }

    private void removeForeign(MouseEvent event) {
        String source = ((Button) event.getSource()).getId();
        selectedTable.getColumns().stream().filter(item -> item.getFieldName().equals(source)).findFirst().get().setForeignColumn(null);
        foreignBtn.setText("新增");
        foreignInfo.setText("无");
    }
}

