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
public enum AuditState implements BaseEnum<Integer>{
    Enable(1, "启用"),
    Disable(0, "禁用");

    private Integer value;
    private String name;

    public AuditState parse(Integer value) {
        Optional<AuditState> first = Arrays.stream(AuditState.values()).filter(c -> c.value == value).findFirst();
        Assert.notNull(first.isPresent(), "审核状态未找到");
        return first.get();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public static class MyConverter extends BaseEnumConverter<AuditState, Integer> {

    }
}
