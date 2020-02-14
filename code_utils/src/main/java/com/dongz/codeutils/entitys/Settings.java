package com.dongz.codeutils.entitys;

import com.dongz.codeutils.utils.StringUtils;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/8 20:51
 * @desc
 */
@Getter
public class Settings {
    private String project="example";
    private String pPackage="com.example.demo";
    private String projectComment;
    private String author;
    private String path1="com";
    private String path2="example";
    private String path3="demo";
    private String pathAll;
    private String outPath;

    public Settings(String outPath, String project, String pPackage, String projectComment, String author) {
        if(StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if(StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.projectComment = projectComment;
        this.author = author;
        String[] paths = pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length>1?paths[1]:path2;
        path3 = paths.length>2?paths[2]:path3;
        pathAll = pPackage.replaceAll(".","/");
        this.outPath = outPath;
    }

    public Map<String, Object> getSettingMap(){
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = Settings.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(this));
            }catch (Exception e){}
        }
        return map;
    }

    public String getBasePath() {
        return outPath + "/" + project + "/src/";
    }

    public String getResourcePath() {
        return outPath + "/" + project + "/src/main/resources/";
    }

    public String getJavaBasePath() {
        return outPath + "/" + project + "/src/main/java/" + pPackage.replace(".", "/");
    }

    public String getTablePath() {
        return  getJavaBasePath() + "/entities/";
    }

    public String getTableVOPath() {
        return getTablePath() + "/vos/";
    }

    public String getControllerPath() {
        return  getJavaBasePath() + "/controllers/";
    }

    public String getServicePath() {
        return  getJavaBasePath() + "/services/";
    }
}
