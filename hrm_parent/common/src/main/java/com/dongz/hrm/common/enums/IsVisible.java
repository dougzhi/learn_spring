package com.dongz.hrm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/1/3 00:04
 * @desc
 */
@Getter
@AllArgsConstructor
public enum IsVisible implements BaseEnum<Integer>{
    TRUE(1, "可见", true),
    FALSE(0, "不可见", false);

    private Integer value;
    private String name;
    private boolean type;

    public static IsVisible parse(Integer value) {
        Optional<IsVisible> first = Arrays.stream(IsVisible.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<IsVisible, Integer> {

    }
}
