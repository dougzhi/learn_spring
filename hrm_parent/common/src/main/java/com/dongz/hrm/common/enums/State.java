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
public enum State implements BaseEnum<Integer>{
    Enable(1, "已激活"),
    Disable(0, "未激活");

    private Integer value;
    private String name;

    public State parse(Integer value) {
        Optional<State> first = Arrays.stream(State.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "审核状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<State, Integer> {

    }
}
