package com.dongz.codeutils.entitys.db;


import com.dongz.codeutils.entitys.Settings;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库实体类
 */
@Data
public class DataBase {

    private static String mysqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&characterEncoding=utf8";
    private static String oracleUrl = "jdbc:oracle:thin:@[ip]:[port]:[db]";

    /**
     * 数据库类型
     */
    private String dbType;
    private String driver;
    private String userName;
    private String password;
    private String url;
    private String db;
    private String port;
    private String ip;
    private List<String> dbList;

    public DataBase(String dbType,String ip,String port,String db) {
        this.dbType = dbType.toUpperCase();
        this.db = db;
        this.port = port;
        this.ip = ip;

        if("MYSQL".endsWith(this.dbType)) {
            this.driver="com.mysql.jdbc.Driver";
            this.url=mysqlUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }else{
            this.driver="oracle.jdbc.driver.OracleDriver";
            this.url=oracleUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }
    }

    public void setUrl(String db) {
        this.db = db;
        if("MYSQL".endsWith(dbType.toUpperCase())) {
            this.driver="com.mysql.jdbc.Driver";
            this.url=mysqlUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }else{
            this.driver="oracle.jdbc.driver.OracleDriver";
            this.url=oracleUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
        }
    }

    public Map<String, Object> getDataBaseMap(){
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = DataBase.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(this));
            }catch (Exception ignored){}
        }
        return map;
    }
}
