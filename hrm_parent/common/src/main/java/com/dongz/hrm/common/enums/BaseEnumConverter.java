package com.dongz.hrm.common.enums;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * @author dong
 * @date 2020/1/3 00:35
 * @desc
 */
public abstract class BaseEnumConverter<X extends BaseEnum<Y>, Y> implements AttributeConverter<BaseEnum<Y>, Y> {
    private Method valuesMethod;

    public BaseEnumConverter() {
        Class<X> xclazz = (Class<X>) (((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments())[0];
        try {
            valuesMethod = xclazz.getMethod("values");
        } catch (Exception e) {
            throw new RuntimeException("can't get values method from " + xclazz);
        }
    }

    @Override
    public Y convertToDatabaseColumn(BaseEnum<Y> baseEnum) {
        return baseEnum == null ? null : baseEnum.getValue();
    }

    @Override
    public X convertToEntityAttribute(Y y) {
        try {
            X[] values = (X[]) valuesMethod.invoke(null);
            return Arrays.asList(values).stream().filter(c -> c.getValue() == y).findFirst().orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("can't convertToEntityAttribute" + e.getMessage());
        }
    }
}
