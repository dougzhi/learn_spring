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

	private boolean isSelected;
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
}
