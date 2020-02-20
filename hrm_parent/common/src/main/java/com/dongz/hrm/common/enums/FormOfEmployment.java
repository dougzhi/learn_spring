package com.dongz.hrm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/1/3 00:04
 * @desc 聘用形式
 */
@Getter
@AllArgsConstructor
public enum FormOfEmployment implements BaseEnum<Integer>{
    Formal(1, "正式"),
    UnFormal(0, "非正式");

    private Integer value;
    private String name;

    public FormOfEmployment parse(Integer value) {
        Optional<FormOfEmployment> first = Arrays.stream(FormOfEmployment.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<FormOfEmployment, Integer> {

    }
}
