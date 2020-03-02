package com.dongz.codeutils.utils;


import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.entitys.db.Table;
import com.dongz.codeutils.entitys.enums.TemplateEnum;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.dongz.codeutils.controllers.BaseController.*;

/**
 * @author dong
 * @date 2020/2/8 23:22
 * @desc
 */
public class DataBaseUtils {

    /**
     * 获取数据库Connection连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(DataBase db) throws ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        //获取备注信息
        properties.put("remarksReporting", "true");
        properties.put("user", db.getUserName());
        properties.put("password", db.getPassword());

        // 注册驱动
        Class.forName(db.getDriver());
        // 1, 获取连接
        return DriverManager.getConnection(db.getUrl(), properties);
    }

    /**
     * 获取数据库列表
     * @throws SQLException
     */
    public static List<String> getSchemas(DataBase db) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection(db);
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet catalogs = metaData.getCatalogs();
        List<String> list = new ArrayList<>();
        while (catalogs.next()) {
            list.add(catalogs.getString(1));
        }
        catalogs.close();
        connection.close();
        return list;
    }

    /**
     * 获取数据库中表和字段的构造实体类
     * 操作步骤
     *  1， 获取连接
     *  2， 获取databasematedata
     *  3， 获取当前数据库的所有表信息
     *  4， 获取每个表中所有字段
     *  5， 封装到Java对象中
     * @return
     */
    public static List<Table> getDbInfo(DataBase db) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection(db);
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //String catalog, String schemaPattern,String tableNamePattern, String types[]
        //  catalog         当前操作的数据库
        //  schemaPattern   mysql: null, orcal: 用户名
        //  tableNamePattern    操作的表名， null代表查询所有表
        //  types  类型   TABLE 表， VIEW 视图
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        List<Table> list = new ArrayList<>();
        try {
            while (tables.next()) {
                // 1， 表名
                String tableName = tables.getString("TABLE_NAME");
                // 4， 主键
                // 字段
                try (ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName); ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
                    // 2， 类名
                    String className = removePrefix(tableName);
                    // 3， 描述
                    String remarks = StringUtils.isBlank(tables.getString("REMARKS")) ? "xxx" : tables.getString("REMARKS");
                    StringBuilder keys = new StringBuilder();
                    while (primaryKeys.next()) {
                        String columnName = primaryKeys.getString("COLUMN_NAME");
                        keys.append(columnName).append(",");
                    }
                    List<Column> columnList = new ArrayList<>();
                    while (columns.next()) {
                        // 1, 列名称
                        String columnName = columns.getString("COLUMN_NAME");
                        // 2, 属性名
                        String attName = StringUtils.toJavaVariableName(columnName);
                        // 3, java类型 ,数据库类型
                        String dbType = columns.getString("TYPE_NAME");
                        String javaType = PropertiesUtils.customMap.get(dbType);
                        // 4, 备注
                        String comment = StringUtils.isBlank(columns.getString("REMARKS")) ? "xxx" : columns.getString("REMARKS");
                        // 5, 是否主键
                        String pri = null;
                        if (Arrays.asList(keys.toString().split(",")).contains(columnName)) {
                            pri = "PRI";
                        }
                        Column column = new Column(columnName, attName, StringUtils.makeGetName(columnName), StringUtils.makeSetName(columnName),javaType, dbType, comment, pri, true, false, true, null);
                        columnList.add(column);
                    }
                    Table table = new Table(tableName, className, StringUtils.uncapitalize(className), remarks, keys.toString(), columnList, true);
                    list.add(table);
                }
            }
        } finally {
            tables.close();
            connection.close();
        }
        return list;
    }

    public static String removePrefix(String tableName) {
        String prefixes = PropertiesUtils.customMap.get("tableRemovePrefixes");
        // "tb_,co_,t_"
        String temp = tableName;
        for (String prefix : prefixes.split(",")) {
            temp = StringUtils.removePrefix(temp, prefix, true);
        }
        return StringUtils.makeAllWordFirstLetterUpperCase(temp);
    }

    public static void makeTemplate() throws IOException {
        createTable(TemplateEnum.Table, ".java");
        createTableVO(TemplateEnum.TableVO, "VO.java");
        createProfession(TemplateEnum.Service, "Service.java");
        createProfession(TemplateEnum.Controller, "Controller.java");
        Arrays.asList(TemplateEnum.values()).parallelStream().filter(TemplateEnum::isBase).forEach(item -> {
            try {
                createOthers(item);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    private static void createTable(TemplateEnum templateEnum, String suffix) throws IOException {
        Template template = getTemplate(templateEnum);
        String outPath = templateEnum.getOutPath();
        selectedTables.forEach((k, v) ->
                {
                    try {
                        Map<String, Object> dataModel = getDataModel(v);
                        template.process(dataModel, new FileWriter(FileUtils.mkdir(outPath, k + suffix)));
                    } catch (TemplateException | IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static void createTableVO(TemplateEnum templateEnum, String suffix) throws IOException {
        Template template = getTemplate(templateEnum);
        String outPath = templateEnum.getOutPath();
        selectedVos.forEach((k, v) ->
                {
                    try {
                        Map<String, Object> dataModel = getDataModel(v);
                        template.process(dataModel, new FileWriter(FileUtils.mkdir(outPath, k + suffix)));
                    } catch (TemplateException | IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static void createProfession(TemplateEnum templateEnum, String suffix) throws IOException {
        Template template = getTemplate(templateEnum);
        String outPath = templateEnum.getOutPath();
        selectedTables.forEach((k, v) ->
                {
                    try {
                        Map<String, Object> dataModel = getDataModel(v);
                        dataModel.put("hasVO", selectedVos.containsKey(k));
                        if (selectedVos.containsKey(k)) {
                            dataModel.put("tableVO", selectedVos.get(k));
                        }
                        // 外键
                        dataModel.put("foreignTables", getAllForeignTables(v));
                        template.process(dataModel, new FileWriter(FileUtils.mkdir(outPath, k + suffix)));
                    } catch (TemplateException | IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private static void createOthers(TemplateEnum templateEnum) throws IOException, TemplateException {
        getTemplate(templateEnum).process(settings.getSettingMap(), new FileWriter(FileUtils.mkdir(templateEnum.getOutPath(), templateEnum.getName())));
    }


    private static Template getTemplate(TemplateEnum templateEnum) throws IOException {
        Configuration cfg = new Configuration();

        cfg.setTemplateLoader(new StringTemplateLoader());

        try (InputStream inputStream = Object.class.getResourceAsStream(templateEnum.getDemoPath())){
            String stringTemplate = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            return new Template(templateEnum.getName(), stringTemplate, cfg);
        }
    }

    private static Map<String, Object> getDataModel(final Table table) {
        // 1, 自定义配置
        Map<String, Object> dataModel = new HashMap<>(PropertiesUtils.customMap);
        // 2, 元数据
        resetIsExtends(table);
        dataModel.put("table", table);
        // 3, setting
        dataModel.putAll(settings.getSettingMap());
        // 4, 类型
        dataModel.put("ClassName", table.getClassName());
        return dataModel;
    }

    private static List<Map<String, Object>> getAllForeignTables(final Table table) {
        return table.getColumns().stream().filter(item -> item.getForeignColumn() != null).map(item -> {
            Map<String, Object> info = new HashMap<>();
            info.put("table", item.getForeignColumn().getTable());
            info.put("foreignColumn", item.getForeignColumn().getColumn());
            info.put("column", item);
            return info;
        }).distinct().collect(Collectors.toList());
    }

    public static void resetIsExtends(final Table table) {
        if (table.isExtendsBase()) {
            table.getColumns().parallelStream().filter(item -> getBaseEntityColumns().contains(item.getFieldName().toLowerCase())).forEach(item -> item.setSelected(false));
        }
    }

    public static void resetIsSelected(final Column column) {
        column.setSelected(!getBaseEntityColumns().contains(column.getFieldName().toLowerCase()));
        column.setForeignColumn(null);
    }

    private static List<String> getBaseEntityColumns() {
        String prefixes = PropertiesUtils.customMap.get("baseEntity");
        return Arrays.asList(prefixes.toLowerCase().split(","));
    }
}
