package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.Settings;
import com.dongz.codeutils.utils.DataBaseUtils;
import com.dongz.codeutils.utils.StringUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    public TextField dirUrl;
    public Button dirPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
    }

    @Override
    protected void reload() {
        if (StringUtils.isNotBlank(outPath)) {
            dirUrl.setText(outPath);
        }
    }

    public void forward() throws IOException {
        changeStep(forwardBtn, STEP3);
    }

    public void dirPicker() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("选择一个文件夹");
        String initPath = "/";
        if (StringUtils.isNotBlank(outPath)) {
            initPath = outPath;
        }
        dc.setInitialDirectory(new File(initPath + File.separator));
        File file = dc.showDialog(new Stage());
        if (file != null) {
            outPath = file.getPath();
            dirUrl.setText(outPath);
        }
    }

    /**
     * 生成代码
     */
    public void finish() throws IOException {
        if (StringUtils.isBlank(outPath)) {
            alert(Alert.AlertType.WARNING, "请选择代码生成路径");
            return;
        }
        String projectName = project.getText();
        if (StringUtils.isBlank(projectName)) {
            alert(Alert.AlertType.WARNING, "请输入项目英文名");
            return;
        }
        String path1Text = path1.getText();
        String path2Text = path2.getText();
        String path3Text = path3.getText();
        if (StringUtils.isBlank(path1Text) || StringUtils.isBlank(path2Text) || StringUtils.isBlank(path3Text)) {
            alert(Alert.AlertType.WARNING, "请输入完整包名");
            return;
        }

        settings = new Settings(outPath, path1Text, path2Text, path3Text, projectName, introduction.getText(), author.getText());

        DataBaseUtils.makeTemplate();
        changeStep(finish, STEP5);
    }
}

