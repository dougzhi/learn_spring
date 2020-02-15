package ${pPackage}.entities.vos;

import lombok.Data;

/**
 * @author dong
 * @date 2020/1/6 15:52
 * @desc
 */
@Data
public class ${ClassName}VO {

    <#list table.columns as column>
    <#if column.selected>
    /**
     * ${column.columnComment}
     */
    private ${column.columnType} ${column.fieldName};
    </#if>
    </#list>
}
