package com.dongz.codeutils.controllers;

import com.dongz.codeutils.entitys.Settings;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.FileUtils;
import com.dongz.codeutils.utils.PropertiesUtils;
import com.dongz.codeutils.utils.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
        if (file == null) return;
        outPath = file.getPath();
        dirUrl.setText(outPath);
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
        if (StringUtils.isBlank(path1Text)||StringUtils.isBlank(path2Text)||StringUtils.isBlank(path3Text)) {
            alert(Alert.AlertType.WARNING, "请输入完整包名");
            return;
        }

        String packageName = path1Text + "." + path2Text + "." + path3Text;
        Settings settings = new Settings(outPath, projectName, packageName, introduction.getText(), author.getText());

        selectedTables.forEach((key, table) -> {
            try {
                createTable(key, table, settings);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        });

        selectedVos.forEach((key, table) -> {
            try {
                createTableVO(key, table, settings);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    private void createTable(String key, Table table, Settings settings) throws IOException, TemplateException {
        saveFile(key, "DemoTable.java", settings.getTablePath(), getDataModel(table, settings));
    }

    private void createTableVO(String key, Table table, Settings settings) throws IOException, TemplateException {
        saveFile(key, "DemoTableVO.java", settings.getTableVOPath(), getDataModel(table, settings));
    }

    private Map<String, Object> getDataModel(Table table,Settings settings) {
        Map<String, Object> dataModel = new HashMap<>();
        // 1, 自定义配置
        dataModel.putAll(PropertiesUtils.customMap);
        // 2, 元数据
        dataModel.put("table", table);
        // 3, setting
        dataModel.putAll(settings.getSettingMap());
        // 4, 类型
        dataModel.put("ClassName", table.getClassName());
        return dataModel;
    }

    private void saveFile(String className, String fileName, String path, Map<String, Object> dateModel) throws IOException, TemplateException {
        Configuration cfg = new Configuration();

        cfg.setTemplateLoader(new StringTemplateLoader());

        InputStream inputStream = Object.class.getResourceAsStream("/templates/" + fileName);
        String stringTemplate = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        Template template = new Template("template01", stringTemplate, cfg);

        template.process(dateModel, new FileWriter(FileUtils.mkdir(path, className + ".java")));
    }
}

