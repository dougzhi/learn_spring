package com.dongz.hrm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/1/3 00:04
 * @desc 用户角色
 */
@Getter
@AllArgsConstructor
public enum LevelState implements BaseEnum<Integer>{
    Admin(0, "超级管理员"),
    CompanyAdmin(1, "企业管理员"),
    NormalUser(2, "普通用户");

    private Integer value;
    private String name;

    public static LevelState parse(Integer value) {
        Optional<LevelState> first = Arrays.stream(LevelState.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<LevelState, Integer> {

    }
}
