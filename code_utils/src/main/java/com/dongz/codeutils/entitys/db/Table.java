package com.dongz.codeutils.entitys.db;

import java.util.List;

/**
 * 表实体
 * @author Administrator
 *
 */
public class Table {

	/**
	 * 表名称
	 */
	private String name;
	/**
	 * 处理后的表名称
	 */
	private String name2;
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



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Table(String name, String name2, String comment, String key, List<Column> columns) {
		this.name = name;
		this.name2 = name2;
		this.comment = comment;
		this.key = key;
		this.columns = columns;
	}
}
