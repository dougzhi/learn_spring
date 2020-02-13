package com.dongz.codeutils.core;

import com.dongz.codeutils.entitys.Settings;
import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.utils.DataBaseUtils;
import com.dongz.codeutils.utils.PropertiesUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/9 13:39
 * @desc 1, 采集用户ui界面输入的数据
 *          - 模板位置
 *          - 代码生成路劲
 *          - 工程配置对象 setting
 *          - 数据库对象
 *       2, 准备数据模型
 *          - 自定义配置
 *          - 元数据
 *          - setting
 *       3, 调用核心处理类，完成代码生成工作
 */
public class GeneratorFacade {
    private String templatePath;
    private String outPath;
    private Settings settings;
    private DataBase db;
    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase db) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.db = db;
        this.generator = new Generator(templatePath, outPath);
    }

    /**
     * 准备数据模型
     * 调用核心处理类，完成代码生成工作
     */
    public void generatorByDataBase() throws SQLException, ClassNotFoundException {
        List<Table> tables = DataBaseUtils.getDbInfo(db);
        tables.forEach(table -> {
            // 对每一个Table对象进行代码生成
            try {
                generator.scanAndGenerator(getDataModel(table));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Map<String, Object> getDataModel(Table table) {
        Map<String, Object> dataModel = new HashMap<>();
        // 1, 自定义配置
        dataModel.putAll(PropertiesUtils.customMap);
        // 2, 元数据
        dataModel.put("table", table);
        // 3, setting
        dataModel.putAll(this.settings.getSettingMap());
        // 4, 类型
        dataModel.put("ClassName", table.getClassName());
        return dataModel;
    }
}
