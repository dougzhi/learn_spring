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
public enum CompanyState implements BaseEnum<Integer>{
    Enable(1, "停业"),
    Disable(0, "开业");

    private Integer value;
    private String name;

    public CompanyState parse(Integer value) {
        Optional<CompanyState> first = Arrays.stream(CompanyState.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "审核状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<CompanyState, Integer> {

    }
}
