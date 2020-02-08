package com.dongz.codeutils.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * @author dong
 * @date 2020/2/8 11:31
 * @desc
 */
public class MateDataTest {

    Connection connection;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/hrm?useUnicode=true&characterEncoding=utf8";
        String username = "root";
        String password = "123456";

        Properties properties = new Properties();
        properties.put("remarksReporting", "true"); //获取备注信息
        properties.put("user", username);
        properties.put("password", password);

        // 1, 获取连接
        Class.forName(driver);// 注册驱动
        connection = DriverManager.getConnection(url, properties);
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    /**
     * 获取数据库基本信息
     */
    @Test
    public void test01() throws SQLException {
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        // 3, 获取数据库基本信息
        System.out.println(metaData.getUserName());
        System.out.println(metaData.supportsTransactions());
        System.out.println(metaData.getDatabaseProductName());
    }

    /**
     * 获取数据库列表
     */
    @Test
    public void test02() throws SQLException {
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet catalogs = metaData.getCatalogs();
        while (catalogs.next()) {
            System.out.println(catalogs.getString(1));
        }
        catalogs.close();
    }

    /**
     * 获取指定数据库的所有表信息
     */
    @Test
    public void test03() throws SQLException {
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();

        //String catalog, String schemaPattern,String tableNamePattern, String types[]
        //  catalog         当前操作的数据库
        //  schemaPattern   mysql: null, orcal: 用户名
        //  tableNamePattern    操作的表名， null代表查询所有表
        //  types  类型   TABLE 表， VIEW 视图
        String[] types = {"TABLE"};
        ResultSet hrm = metaData.getTables(null, null, null, types);
        while (hrm.next()) {
            System.out.println(hrm.getString("TABLE_NAME"));
        }
        hrm.close();
    }

    /**
     * 获取指定表中的字段信息
     */
    @Test
    public void test04() throws SQLException {
        // 2, 获取元数据
        DatabaseMetaData metaData = connection.getMetaData();

        //String catalog, String schemaPattern,String tableNamePattern, String columnNamePattern
        //  catalog         当前操作的数据库
        //  schemaPattern   mysql: null, orcal: 用户名
        //  tableNamePattern    操作的表名， null代表查询所有表
        //  columnNamePattern   操作的字段名 null代表查询所有字段
        ResultSet user = metaData.getColumns(null, null, "bs_user", null);
        while (user.next()) {
            System.out.println(user.getString("COLUMN_NAME"));
        }
        user.close();
    }

    /**
     * 获取参数元数据
     */
    @Test
    public void test05() throws SQLException {
        String sql = "select t.* from bs_user t where t.id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "1063705482939731968");

        //获取参数元数据
        ParameterMetaData parameterMetaData = statement.getParameterMetaData();
        System.out.println(parameterMetaData.getParameterCount());
    }

    /**
     * 获取结果元数据
     */
    @Test
    public void test06() throws SQLException {
        String sql = "select t.* from bs_user t where t.id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "1063705482939731968");

        //执行sql
        ResultSet resultSet = statement.executeQuery();

        //获取结果元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        System.out.println(metaData.getColumnCount());

        while (resultSet.next()) {
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i + 1) + "--" + resultSet.getString(i + 1) + "--" + metaData.getColumnTypeName(i + 1) + "--" + metaData.getColumnClassName(i + 1));
            }
        }
        resultSet.close();
    }
}
