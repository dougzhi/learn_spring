package com.dongz.codeutils.entitys.db;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列对象
 */
@Data
@AllArgsConstructor
public class Column implements Cloneable{
	/**
	 * 列名称
	 */
	private String columnName;
	/**
	 * 列名称(处理后的列名称)
	 */
	private String fieldName;
	/**
	 * 方法名称
	 */
	private String getName;
	private String setName;
	/**
	 * java列类型
	 */
	private String columnType;
	/**
	 * 列数据库类型
	 */
	private String columnDbType;
	/**
	 * 列备注D
	 */
	private String columnComment;
	/**
	 * 是否是主键
	 */
	private String columnKey;
	/**
	 * 是否选中
	 */
	private boolean selected;
	/**
	 * 是否唯一
	 */
	private boolean isOnly;
	/**
	 * 是否非空
	 */
	private boolean isNotNull;
	/**
	 * 外键关联表关系
	 */
	private ForeignColumn foreignColumn;

	@Data
	public static class ForeignColumn{
		private Table table;
		private Column column;
	}

	@Override
	protected Column clone() {
		Column column = null;
		try {
			column = (Column) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return column;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isOnly() {
		return isOnly;
	}

	public void setOnly(boolean only) {
		isOnly = only;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean notNull) {
		isNotNull = notNull;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
