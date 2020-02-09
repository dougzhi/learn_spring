package com.dongz.codeutils.entitys.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
