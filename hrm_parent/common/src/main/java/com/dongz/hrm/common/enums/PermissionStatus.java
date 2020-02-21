package com.dongz.hrm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/1/3 00:04
 * @desc 1为菜单 2为功能 3为API
 */
@Getter
@AllArgsConstructor
public enum PermissionStatus implements BaseEnum<Integer> {
    MENU(1, "菜单", "PermissionMenuService","permission_menu"),
    Point(2, "功能", "PermissionPointService","permission_point"),
    API(3,"API", "PermissionApiService","permission_api");

    private Integer value;
    private String name;
    private String className;
    private String tableName;

    public static PermissionStatus parse(Integer value) {
        Optional<PermissionStatus> first = Arrays.stream(PermissionStatus.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<PermissionStatus, Integer> {

    }
}
