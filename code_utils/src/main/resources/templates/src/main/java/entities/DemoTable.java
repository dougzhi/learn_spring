package ${pPackage}.entities;

import ${path1}.${path2}.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dong
 * @date
 * @desc ${table.comment}
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${table.name}")
public class ${ClassName} extends BaseEntity implements Serializable {

<#list table.columns as column>
/**
 * ${column.columnComment}
 */
<#if column.columnKey??>
@Id
</#if>
private ${column.columnType} ${column.fieldName};

</#list>
}