package com.dongz.hrm.common.Enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author dong
 * @date 2020/1/3 00:35
 * @desc
 */
@Converter
public class BaseEnumConverter<X extends BaseEnum<Y>, Y> implements AttributeConverter<BaseEnum<Y>, Y> {
    private Class<X> xclazz;
    private Method valuesMethod;

    public BaseEnumConverter() {
        this.xclazz = (Class<X>) (((ParameterizedType) this.getClass().getGenericSuperclass())
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
            for (X x : values) {
                if (x.getValue().equals(y)) {
                    return x;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("can't convertToEntityAttribute" + e.getMessage());
        }
        throw new RuntimeException("unknown y " + y);
    }
}
