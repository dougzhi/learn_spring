package com.dongz.hrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dong
 */
@Entity
@Table(name = "permission_api")
@Getter
@Setter
public class PermissionApi implements Serializable {
    private static final long serialVersionUID = -1803315043290784820L;
    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 链接
     */
    private String baseUrl;
    /**
     * 链接
     */
    private String apiUrl;
    /**
     * 请求类型
     */
    private String apiMethod;
    /**
     * 请求类型
     */
    private String apiName;
    /**
     * 权限等级，1为通用接口权限，2为需校验接口权限
     */
    private String apiLevel;

    public Map<String, Object> getDataBaseMap(Object o){
        PermissionApi api = (PermissionApi) o;
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = PermissionApi.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(api));
            }catch (Exception ignored){}
        }
        return map;
    }
}