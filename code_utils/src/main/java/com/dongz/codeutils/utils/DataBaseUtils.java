package com.dongz.codeutils.utils;


import com.dongz.codeutils.entitys.db.DataBase;

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
}
