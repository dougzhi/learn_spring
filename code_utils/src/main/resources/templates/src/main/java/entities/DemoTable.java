package ${pPackage}.entities;

<#if table.extendsBase>
import ${pPackage}.entities.BaseEntity;
</#if>
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ${author}
 * @date ${currTime?datetime}
 * @desc ${table.comment}
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${table.name}")
public class ${ClassName} <#if table.extendsBase>extends BaseEntity</#if> implements Serializable {

    <#list table.columns as column>
    <#if column.selected>
    /**
     * ${column.columnComment}
     */
    <#if column.columnKey??>
    @Id
    </#if>
    private ${column.columnType} ${column.fieldName};
    </#if>
    </#list>
}