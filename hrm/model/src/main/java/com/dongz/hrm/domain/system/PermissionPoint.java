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
@Table(name = "permission_point")
@Getter
@Setter
public class PermissionPoint implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 权限代码
     */
    private String pointClass;

    private String pointIcon;

    private String pointStatus;


    public Map<String, Object> getDataBaseMap(Object o){
        PermissionPoint point = (PermissionPoint) o;
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = PermissionPoint.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try{
                map.put(field.getName(), field.get(point));
            }catch (Exception ignored){}
        }
        return map;
    }
}