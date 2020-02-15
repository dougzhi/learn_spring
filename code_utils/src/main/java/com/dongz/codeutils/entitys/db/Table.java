package com.dongz.codeutils.entitys.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表实体
 * @author Administrator
 * 外键
 */
@Data
@AllArgsConstructor
public class Table implements Cloneable{

	/**
	 * 表名称
	 */
	private String name;
	/**
	 * 处理后的表名称
	 */
	private String className;
	/**
	 * 介绍
	 */
	private String comment;
	/**
	 * 主键列
	 */
	private String key;
	/**
	 * 列集合
	 */
	private List<Column> columns;
	/**
	 * 是否继承BaseEntity
	 */
	private boolean extendsBase;

	@Override
	public Table clone() {
		Table table = null;
		try {
			table = (Table) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (columns != null) {
			columns = columns.stream().map(item -> {
				Column clone = item.clone();
				return clone;
			}).collect(Collectors.toList());
		}
		return table;
	}
}
