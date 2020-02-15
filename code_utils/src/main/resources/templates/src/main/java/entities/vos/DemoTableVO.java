package ${pPackage}.entities.vos;

import lombok.Data;

/**
 * @author ${author}
 * @date ${currTime}
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
