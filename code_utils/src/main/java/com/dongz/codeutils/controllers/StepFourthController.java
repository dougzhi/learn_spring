package com.dongz.codeutils.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author dong
 * @date 2020/2/10 21:52
 * @desc
 */
public class StepFourthController extends BaseController{

    public Button forwardBtn;
    public Button finish;
    public TextField author;
    public TextField introduction;
    public TextField path1;
    public TextField path2;
    public TextField path3;
    public TextField project;
    public ComboBox templates;
    public TextField dirUrl;
    public Button dirPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> templateFileList = getTemplateFileList();
        templates.setItems(FXCollections.observableArrayList(templateFileList));
    }

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP3);
    }

    public void dirPicker() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("选择一个文件夹");
        dc.setInitialDirectory(new File("/" + File.separator));
        File file = dc.showDialog(new Stage());
        if (file == null) return;
        String path = file.getPath();
        dirUrl.setText(path);
    }

    public void finish(ActionEvent actionEvent) {
    }
}

