package com.dongz.codeutils.utils;


import com.dongz.codeutils.entitys.db.Column;
import com.dongz.codeutils.entitys.db.DataBase;
import com.dongz.codeutils.entitys.db.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        properties.put("password", db.getPassWord());

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
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                // 字段
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                try {
                    // 2， 类名
                    String className = removePrefix(tableName);
                    // 3， 描述
                    String remarks = tables.getString("REMARKS");
                    String keys = "";
                    while (primaryKeys.next()) {
                        String columnName = primaryKeys.getString("COLUMN_NAME");
                        keys += columnName + ",";
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
                        String comment = columns.getString("REMARKS");
                        // 5, 是否主键
                        String pri = null;
                        if (StringUtils.contains(columnName, keys.split(","))) {
                            pri = "PRI";
                        }
                        Column column = new Column(columnName, attName, javaType, dbType, comment, pri, true);
                        columnList.add(column);
                    }
                    Table table = new Table(tableName, className, remarks, keys, columnList);
                    list.add(table);
                } finally {
                    columns.close();
                    primaryKeys.close();
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
}
