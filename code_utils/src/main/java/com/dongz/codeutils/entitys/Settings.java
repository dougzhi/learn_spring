package com.dongz.codeutils.entitys;

import com.dongz.codeutils.utils.StringUtils;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dongz.codeutils.controllers.BaseController.db;
/**
 * @author dong
 * @date 2020/2/8 20:51
 * @desc
 */
@Getter
public class Settings {
    private String project;
    private String pPackage;
    private String projectComment;
    private String author;
    private String path1;
    private String path2;
    private String path3;
    private String pathAll;
    private String outPath;

    public Settings(String outPath, String path1, String path2, String path3, String project, String projectComment, String author) {
        this.project = project;
        this.projectComment = projectComment;
        this.author = author;
        this.outPath = outPath;
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
        this.pPackage = path1 + "." + path2 + "." + path3 + "." + project;
        this.pathAll = pPackage.replaceAll("\\.","/");
    }

    public Map<String, Object> getSettingMap(){
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = Settings.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(this));
            }catch (Exception ignored){}
        }
        map.put("currTime", new Date(System.currentTimeMillis()));
        map.putAll(db.getDataBaseMap());
        return map;
    }
}
