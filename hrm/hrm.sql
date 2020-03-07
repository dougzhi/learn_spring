/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : hrm

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/03/2020 15:12:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for archive
-- ----------------------------
DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive` (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `op_user` varchar(255) NOT NULL COMMENT '操作用户',
  `month` varchar(255) NOT NULL COMMENT '月份',
  `company_id` varchar(255) NOT NULL COMMENT '企业ID',
  `totals` int(8) NOT NULL DEFAULT '0' COMMENT '总人数',
  `payrolls` int(8) NOT NULL DEFAULT '0' COMMENT '在职人数',
  `departures` int(8) NOT NULL DEFAULT '0' COMMENT '离职人数',
  `data` longtext COMMENT '数据',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `manager_id` varchar(255) DEFAULT NULL COMMENT '企业登录账号ID',
  `version` varchar(255) DEFAULT NULL COMMENT '当前版本',
  `renewal_date` datetime DEFAULT NULL COMMENT '续期时间',
  `expiration_date` datetime DEFAULT NULL COMMENT '到期时间',
  `company_area` varchar(255) DEFAULT NULL COMMENT '公司地区',
  `company_address` text COMMENT '公司地址',
  `business_license_id` varchar(255) DEFAULT NULL COMMENT '营业执照-图片ID',
  `legal_representative` varchar(255) DEFAULT NULL COMMENT '法人代表',
  `company_phone` varchar(255) DEFAULT NULL COMMENT '公司电话',
  `mailbox` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `company_size` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `industry` varchar(255) DEFAULT NULL COMMENT '所属行业',
  `remarks` text COMMENT '备注',
  `audit_state` tinyint(2) DEFAULT NULL COMMENT '审核状态',
  `state` tinyint(2) DEFAULT '1' COMMENT '状态',
  `balance` double DEFAULT NULL COMMENT '当前余额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `delete_id` bigint(20) NOT NULL,
  `delete_name` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `last_update_id` bigint(20) NOT NULL,
  `last_update_name` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `business_license` varchar(255) DEFAULT NULL,
  `deleter_id` bigint(20) NOT NULL,
  `deleter_name` varchar(255) DEFAULT NULL,
  `last_updater_id` bigint(20) NOT NULL,
  `last_updater_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of company
-- ----------------------------
BEGIN;
INSERT INTO `company` VALUES ('1', '江苏传智播客教育科技股份有限公司', 'abc', '12', NULL, NULL, NULL, NULL, NULL, '张三', NULL, NULL, NULL, NULL, NULL, 0, 1, 0, '2018-11-07 13:30:05', 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `company` VALUES ('1060043412612452352', 'zhangsan', 'abc', '12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, '2018-11-07 13:36:58', 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `company` VALUES ('1061532681482932224', 'zhangsan', 'abc', '12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, '2018-11-11 16:14:48', 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `company` VALUES ('1065494996154650624', '江苏传智播客教育股份有限公司', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` varchar(40) NOT NULL,
  `company_id` varchar(255) NOT NULL COMMENT '企业ID',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `code` varchar(255) DEFAULT NULL COMMENT '部门编码',
  `category` int(11) DEFAULT NULL COMMENT '部门类型',
  `manager` varchar(40) DEFAULT NULL COMMENT '部门负责人',
  `introduce` text COMMENT '介绍',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `manager_id` varchar(255) DEFAULT NULL COMMENT '负责人ID',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `creator_id` bigint(20) NOT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `delete_id` bigint(20) NOT NULL,
  `delete_name` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `last_update_id` bigint(20) NOT NULL,
  `last_update_name` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `deleter_id` bigint(20) NOT NULL,
  `deleter_name` varchar(255) DEFAULT NULL,
  `last_updater_id` bigint(20) NOT NULL,
  `last_updater_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO `department` VALUES ('1063676045212913664', '1', '研发部', 'DEV-TECHNOLOG', NULL, '技术部总监', '包含开发部，测试部', '2018-11-17 14:11:45', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1063678149528784896', '1', '测试部', 'DEPT-TEST', NULL, '测试部门领导', '所有测试人员统一划分到测试部', '2018-11-17 14:20:07', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066238836272664576', '1', '财务部', 'DEPT-FIN', NULL, '李四', '包含出纳和会计', '2018-11-24 15:55:22', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066239766607040512', '1', '行政中心', 'DEPT-XZ', NULL, '张三', '包含人力资源和行政部门', '2018-11-24 15:59:04', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066239913642561536', '1', '人力资源部', 'DEPT-HR', NULL, '李四', '人力资源部', '2018-11-24 15:59:39', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066240303092076544', '1', '行政部', 'DEPT-XZ', NULL, '王五', '行政部', '2018-11-24 16:01:12', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066240656856453120', '1', '开发部', 'DEPT-DEV', NULL, '研发', '全部java开发人员', '2018-11-24 16:02:37', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066241293639880704', '1', 'test1', 'test2', NULL, 'test', 'test4', '2018-11-24 16:05:08', NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066296800727531520', '1', 'ttt', 'ttt', NULL, 'ttt', 'ttt', NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `department` VALUES ('1066296884038991872', '1', 'aaa', 'aa', NULL, 'aa', 'aa', NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, b'0', 0, NULL, NULL, NULL, 0, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (1);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `description` text COMMENT '权限描述',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型 1为菜单 2为功能 3为API',
  `pid` varchar(40) DEFAULT '0' COMMENT '主键',
  `code` varchar(200) DEFAULT NULL,
  `is_visible` int(1) DEFAULT NULL COMMENT '企业可见性 0：不可见，1：可见',
  `full_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('1063315016368918528', '用户管理菜单', '员工管理', 1, '0', 'employees', 1, '');
INSERT INTO `permission` VALUES ('1063315194329042944', '用户删除按钮', '用户删除按钮', 2, '1063315016368918528', 'point-user-delete', 1, 'employees.');
INSERT INTO `permission` VALUES ('1063322760811515904', '删除api', '删除用户api', 3, '1063315194329042944', 'API-USER-DELETE', 1, 'employees.point-user-delete.');
INSERT INTO `permission` VALUES ('1063327833876729856', '组织架构菜单', '组织架构', 1, '0', 'departments', 1, '');
INSERT INTO `permission` VALUES ('1063328310592933888', '用户添加按钮', '用户添加按钮', 2, '1063315016368918528', 'POINT-USER-ADD', 1, 'employees.');
INSERT INTO `permission` VALUES ('1063328532492587008', '用户修改按钮', '用户修改按钮', 2, '1063315016368918528', 'POINT-USER-UPDATE', 1, 'employees.');
INSERT INTO `permission` VALUES ('1064104257952813056', '1', '权限设置', 1, '0', 'permissions', 0, '');
INSERT INTO `permission` VALUES ('1231439557265428480', '1', '企业管理', 1, '0', 'company', 1, '');
INSERT INTO `permission` VALUES ('1231439676782120960', '1', '企业管理', 2, '1231439557265428480', 'testPoint1', 1, 'company.');
INSERT INTO `permission` VALUES ('1231457302493237248', '1', '查看企业列表', 3, '1231439676782120960', 'API-COMPANY-LIST', 1, 'company.testPoint1.');
INSERT INTO `permission` VALUES ('1231523867766956032', '11', '组织管理', 2, '1231439557265428480', 'testPoint2', 1, 'company.');
INSERT INTO `permission` VALUES ('1232605141419716608', '查看详情', '查看详情', 3, '1231439676782120960', 'API-COMPANY-INFO', 1, 'company.testPoint1.');
INSERT INTO `permission` VALUES ('1232665695487389696', '角色授权', '角色设置', 1, '0', 'settings', 0, '');
INSERT INTO `permission` VALUES ('1232666899382550528', '角色授权', '角色授权', 2, '1232665695487389696', 'assignPrem', 1, 'settings.');
INSERT INTO `permission` VALUES ('1232676355369713664', '授权', '授权', 3, '1232666899382550528', 'API-ROLE-ASSIGNPREM', 0, 'settings.assignPrem.');
INSERT INTO `permission` VALUES ('1232678269889134592', '查询', '组织查询', 2, '1063327833876729856', 'department-search', 1, 'departments.');
INSERT INTO `permission` VALUES ('1232678396926214144', '查询', '组织结构查询', 3, '1232678269889134592', 'API-DEPARTMENT-FINDALL', 1, 'departments.department-search.');
INSERT INTO `permission` VALUES ('1232941887437574144', '角色管理', '角色管理', 2, '1232665695487389696', '22', 1, 'settings.');
COMMIT;

-- ----------------------------
-- Table structure for permission_api
-- ----------------------------
DROP TABLE IF EXISTS `permission_api`;
CREATE TABLE `permission_api` (
  `id` varchar(40) NOT NULL COMMENT '主键ID',
  `api_level` varchar(2) DEFAULT NULL COMMENT '权限等级，1为通用接口权限，2为需校验接口权限',
  `api_method` varchar(255) DEFAULT NULL COMMENT '请求类型',
  `api_url` varchar(255) DEFAULT NULL COMMENT '链接',
  `api_name` varchar(255) DEFAULT NULL,
  `base_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission_api
-- ----------------------------
BEGIN;
INSERT INTO `permission_api` VALUES ('1063322760811515904', '1', 'delete', '/sys/user/delete', NULL, NULL);
INSERT INTO `permission_api` VALUES ('1231457302493237248', '1', '1', '1', NULL, NULL);
INSERT INTO `permission_api` VALUES ('1232605141419716608', '1', 'get', '/api/company/info', '企业详情', '/api/company');
INSERT INTO `permission_api` VALUES ('1232676355369713664', '1', 'post', '/api/sys/role/assignPrem', '角色授权', '/api/sys/role');
INSERT INTO `permission_api` VALUES ('1232678396926214144', '1', 'get', '/api/department/findAll', '组织机构查询', '/api/department');
COMMIT;

-- ----------------------------
-- Table structure for permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_menu`;
CREATE TABLE `permission_menu` (
  `id` varchar(40) NOT NULL COMMENT '主键ID',
  `menu_icon` varchar(20) DEFAULT NULL COMMENT '权限代码',
  `menu_order` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission_menu
-- ----------------------------
BEGIN;
INSERT INTO `permission_menu` VALUES ('1063315016368918528', 'menu-user', '2');
INSERT INTO `permission_menu` VALUES ('1063327833876729856', '2', '2');
INSERT INTO `permission_menu` VALUES ('1064104257952813056', '1', '1');
INSERT INTO `permission_menu` VALUES ('1231439557265428480', '1', '1');
INSERT INTO `permission_menu` VALUES ('1232665695487389696', '1', '3');
INSERT INTO `permission_menu` VALUES ('2', 'def', '2');
COMMIT;

-- ----------------------------
-- Table structure for permission_point
-- ----------------------------
DROP TABLE IF EXISTS `permission_point`;
CREATE TABLE `permission_point` (
  `id` varchar(40) NOT NULL COMMENT '主键ID',
  `point_class` varchar(20) DEFAULT NULL COMMENT '按钮类型',
  `point_icon` varchar(20) DEFAULT NULL COMMENT '按钮icon',
  `point_status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission_point
-- ----------------------------
BEGIN;
INSERT INTO `permission_point` VALUES ('1063315194329042944', 'point-user-delete', 'point-user-delete', 1);
INSERT INTO `permission_point` VALUES ('1063328310592933888', 'POINT-USER-ADD', 'POINT-USER-ADD', 1);
INSERT INTO `permission_point` VALUES ('1063328532492587008', 'POINT-USER-UPDATE', 'POINT-USER-UPDATE', 1);
INSERT INTO `permission_point` VALUES ('1231439676782120960', '1', '1', 1);
INSERT INTO `permission_point` VALUES ('1231523867766956032', '1', '1', 1);
INSERT INTO `permission_point` VALUES ('1232666899382550528', '1', '1', 1);
INSERT INTO `permission_point` VALUES ('1232678269889134592', '1', '1', 1);
INSERT INTO `permission_point` VALUES ('1232941887437574144', '1', '1', 1);
COMMIT;

-- ----------------------------
-- Table structure for positive
-- ----------------------------
DROP TABLE IF EXISTS `positive`;
CREATE TABLE `positive` (
  `user_id` varchar(40) NOT NULL COMMENT '员工ID',
  `date_of_correction` datetime DEFAULT NULL COMMENT '转正日期',
  `correction_evaluation` text COMMENT '转正评价',
  `enclosure` text COMMENT '附件',
  `estatus` int(2) NOT NULL COMMENT '单据状态 1是未执行，2是已执行',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of positive
-- ----------------------------
BEGIN;
INSERT INTO `positive` VALUES ('1063705989926227968', '2018-12-23 08:00:00', '111', NULL, 1, '2018-12-15 21:45:10');
COMMIT;

-- ----------------------------
-- Table structure for resignation
-- ----------------------------
DROP TABLE IF EXISTS `resignation`;
CREATE TABLE `resignation` (
  `user_id` varchar(40) NOT NULL COMMENT '用户ID',
  `resignation_time` varchar(255) NOT NULL,
  `type_of_turnover` varchar(255) DEFAULT NULL COMMENT '离职类型',
  `reasons_for_leaving` varchar(255) DEFAULT NULL COMMENT '申请离职原因',
  `compensation` varchar(255) DEFAULT NULL COMMENT '补偿金',
  `notifications` varchar(255) DEFAULT NULL COMMENT '代通知金',
  `social_security_reduction_month` varchar(255) DEFAULT NULL COMMENT '社保减员月',
  `provident_fund_reduction_month` varchar(255) DEFAULT NULL COMMENT '公积金减员月',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of resignation
-- ----------------------------
BEGIN;
INSERT INTO `resignation` VALUES ('1063705989926227968', '2018-02-05', '主动离职', NULL, '100', '100', '离职日本月', '离职日次月', NULL, '2018-12-16 20:49:26');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(40) NOT NULL COMMENT '主键ID',
  `name` varchar(40) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '说明',
  `company_id` varchar(40) DEFAULT NULL COMMENT '企业id',
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) NOT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `deleter_id` bigint(20) NOT NULL,
  `deleter_name` varchar(255) DEFAULT NULL,
  `last_updater_id` bigint(20) NOT NULL,
  `last_updater_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k3beff7qglfn58qsf2yvbg41i` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1062944989845262336', '人事经理', '负责整合人事部门', '1', NULL, 0, NULL, NULL, b'0', NULL, 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1064098829009293312', '系统管理员', '管理整合平台，可以操作企业所有功能', '1', NULL, 0, NULL, NULL, b'0', NULL, 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1064098935443951616', '人事专员', '只能操作员工菜单', '1', NULL, 0, NULL, NULL, b'0', NULL, 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1064099035536822272', '薪资专员', '绩效管理', '1', NULL, 0, NULL, NULL, b'0', '2020-02-20 15:25:30', 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1230509775529447424', '管理员', NULL, NULL, '2020-02-20 15:09:27', 0, NULL, '2020-02-20 15:24:31', b'1', NULL, 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1230867603347763200', 'ce1', '111', NULL, '2020-02-21 14:51:20', 0, NULL, '2020-02-22 11:01:44', b'1', '2020-02-22 06:20:34', 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1231172340988698624', 'ceshi1', 'ceshi', NULL, '2020-02-22 11:02:15', 0, NULL, '2020-02-22 11:25:42', b'1', NULL, 0, NULL, 0, NULL);
INSERT INTO `role` VALUES ('1232938683022848000', '1', '1', NULL, '2020-02-27 08:01:04', 0, NULL, '2020-02-27 08:01:07', b'1', NULL, 0, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` varchar(40) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(40) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK74qx7rkbtq2wqms78gljv87a0` (`permission_id`),
  KEY `FKee9dk0vg99shvsytflym6egxd` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES ('1064098829009293312', '1063315016368918528');
INSERT INTO `role_permission` VALUES ('1064098829009293312', '1063315194329042944');
INSERT INTO `role_permission` VALUES ('1064099035536822272', '1063315194329042944');
INSERT INTO `role_permission` VALUES ('1064098829009293312', '1063322760811515904');
INSERT INTO `role_permission` VALUES ('1064099035536822272', '1063322760811515904');
INSERT INTO `role_permission` VALUES ('1064098829009293312', '1063328310592933888');
INSERT INTO `role_permission` VALUES ('1064098829009293312', '1063328532492587008');
INSERT INTO `role_permission` VALUES ('1062944989845262336', '1231439557265428480');
INSERT INTO `role_permission` VALUES ('1062944989845262336', '1231439676782120960');
INSERT INTO `role_permission` VALUES ('1062944989845262336', '1231457302493237248');
INSERT INTO `role_permission` VALUES ('1062944989845262336', '1231523867766956032');
INSERT INTO `role_permission` VALUES ('1062944989845262336', '1232605141419716608');
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(40) NOT NULL COMMENT 'id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件原始名称',
  `path` varchar(255) DEFAULT NULL COMMENT '存储路径',
  `uuid_name` varchar(255) DEFAULT NULL COMMENT '文件实际名称',
  `type` tinyint(2) DEFAULT NULL COMMENT '文件类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sys_settings
-- ----------------------------
DROP TABLE IF EXISTS `sys_settings`;
CREATE TABLE `sys_settings` (
  `company_id` varchar(40) NOT NULL COMMENT '企业ID',
  `settings_type` int(2) NOT NULL COMMENT '设置类型',
  `settings_info` text NOT NULL COMMENT '详细设置',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record` (
  `id` varchar(40) NOT NULL,
  `company_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `type` int(2) NOT NULL,
  `amount` double NOT NULL,
  `balance` double NOT NULL,
  `note` text,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for transferposition
-- ----------------------------
DROP TABLE IF EXISTS `transferposition`;
CREATE TABLE `transferposition` (
  `user_id` varchar(40) NOT NULL COMMENT '用户ID',
  `post` varchar(255) DEFAULT NULL COMMENT '岗位',
  `rank` varchar(255) DEFAULT NULL COMMENT '职级',
  `reporting_object` varchar(255) DEFAULT NULL COMMENT '汇报对象',
  `hrbp` varchar(255) DEFAULT NULL COMMENT 'HRBP',
  `adjustment_time` datetime NOT NULL COMMENT '调岗时间',
  `cause_of_adjusting_post` text COMMENT '调岗原因',
  `enclosure` text COMMENT '附件 [1,2,3]',
  `form_of_management` varchar(255) DEFAULT NULL COMMENT '管理形式',
  `working_city` varchar(255) DEFAULT NULL COMMENT '工作城市',
  `taxable_city` varchar(255) DEFAULT NULL COMMENT '纳税城市',
  `current_contract_start_time` varchar(255) DEFAULT NULL COMMENT '现合同开始时间',
  `closing_time_of_current_contract` varchar(255) DEFAULT NULL COMMENT '现合同结束时间',
  `working_place` varchar(255) DEFAULT NULL COMMENT '工作地点',
  `initial_contract_start_time` varchar(255) DEFAULT NULL COMMENT '首次合同开始时间',
  `first_contract_termination_time` varchar(255) DEFAULT NULL COMMENT '首次合同结束时间',
  `contract_period` varchar(255) DEFAULT NULL COMMENT '合同期限',
  `renewal_number` int(8) DEFAULT NULL COMMENT '续签次数',
  `recommender_business_people` varchar(255) DEFAULT NULL COMMENT '推荐企业人',
  `estatus` int(2) NOT NULL COMMENT '单据状态 1是未执行，2是已执行',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of transferposition
-- ----------------------------
BEGIN;
INSERT INTO `transferposition` VALUES ('1063705989926227968', '', '', '', '', '2018-12-03 08:00:00', 'aaaaa', '', '', '', '', '2018-12-03', '2018-12-04', '', '', '', '12月', 1, '', 1, '2018-12-15 22:01:08');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `mobile` varchar(40) NOT NULL COMMENT '手机号码',
  `username` varchar(255) NOT NULL COMMENT '用户名称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `enable_state` int(2) DEFAULT '1' COMMENT '启用状态 0是禁用，1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `department_id` varchar(40) DEFAULT NULL COMMENT '部门ID',
  `time_of_entry` datetime DEFAULT NULL COMMENT '入职时间',
  `form_of_employment` int(1) DEFAULT NULL COMMENT '聘用形式',
  `work_number` varchar(20) DEFAULT NULL COMMENT '工号',
  `form_of_management` varchar(8) DEFAULT NULL COMMENT '管理形式',
  `working_city` varchar(16) DEFAULT NULL COMMENT '工作城市',
  `correction_time` datetime DEFAULT NULL COMMENT '转正时间',
  `in_service_status` int(1) DEFAULT NULL COMMENT '在职状态 1.在职  2.离职',
  `company_id` varchar(40) DEFAULT NULL COMMENT '企业ID',
  `company_name` varchar(40) DEFAULT NULL,
  `department_name` varchar(40) DEFAULT NULL,
  `level` varchar(40) DEFAULT NULL COMMENT '用户级别：saasAdmin，coAdmin，user',
  `staff_photo` mediumtext,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `exit_time` datetime DEFAULT NULL,
  `service_status` int(11) DEFAULT NULL,
  `deleter_id` bigint(20) DEFAULT NULL,
  `deleter_name` varchar(255) DEFAULT NULL,
  `last_updater_id` bigint(20) NOT NULL,
  `last_updater_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_phone` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1063705482939731968', '13800000001', 'cgx', '88012a09484c94fcec9e65b2377c44b9', 1, '2018-11-17 16:08:44', '', '2018-11-01 08:00:00', 1, '', NULL, NULL, '2018-11-01 00:00:00', NULL, '', '', '', '0', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1063705989926227968', '13800000002', 'aj123', 'c8b7722b1139bb9b346409e503302e82', 1, '2018-11-17 16:10:45', '1066241293639880704', '2018-11-02 08:00:00', 1, '9002', NULL, NULL, '2018-11-30 00:00:00', NULL, '1', '传智播客', 'test1', '1', 'http://pkbivgfrm.bkt.clouddn.com/1063705989926227968?t=1545788007343', 0, NULL, '2020-02-21 02:10:36', b'1', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1066370498633486336', '13800000003', 'zbz', '14af10ffa3798486632a79cbbf469376', 1, NULL, '1063678149528784896', '2018-11-04 08:00:00', 1, '111', NULL, NULL, '2018-11-20 00:00:00', NULL, '1', '传智播客', '测试部', '2', 'http://pkbivgfrm.bkt.clouddn.com/1066370498633486336?t=1545812322518', 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1071632760222810112', '13800000004', 'll', '456134d471010ecba14c6f89cac349ff', 1, NULL, '1063678149528784896', '2018-12-02 08:00:00', 1, '1111', NULL, NULL, '2018-12-31 00:00:00', NULL, '1', '传智播客', '测试部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1074238801330704384', '13400000001', 'a01', '80069fc2872ce3cf269053f4a84b2f0d', 1, '2018-12-16 17:44:22', '1066240656856453120', '2018-01-01 00:00:00', 1, '1001', NULL, NULL, NULL, 1, '1', '传智播客', '开发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1074238801402007552', '13400000002', 'a02', 'a4f6437f96466ff2ad41dc8c46317e7f', 1, '2018-12-16 17:44:23', '1066240656856453120', '2018-01-01 00:00:00', 1, '1002', NULL, NULL, NULL, 1, '1', '传智播客', '开发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1075383133106425856', '13500000001', 'test001', 'aa824c0d32d9725482e58a7503a20521', 1, NULL, '1066240656856453120', '2018-01-01 00:00:00', 1, '2001', NULL, NULL, NULL, 1, '1', '传智播客', '开发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1075383135371350016', '13500000002', 'test002', 'becc21ed8df7975fc845c6c70f46c2dd', 1, NULL, '1066240656856453120', '2018-01-01 00:00:00', 1, '2002', NULL, NULL, NULL, 1, '1', '传智播客', '开发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1075383135459430400', '13500000003', 'test003', '7321b9c9cfaa938abc7408147fc29441', 1, NULL, '1066240656856453120', '2018-01-01 00:00:00', 1, '2003', NULL, NULL, NULL, 1, '1', '传智播客', '开发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1230521114205360128', '18888888888', 'ceshi', '12122', 1, '2020-02-20 15:54:30', NULL, '2020-02-03 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, 0, NULL, '2020-02-24 06:51:09', b'1', NULL, NULL, NULL, 0, NULL, 0, NULL);
INSERT INTO `user` VALUES ('1231579718649249792', '18566557507', 'dongz', '00f9f03e5ff3f2e2f32e220e23b5e927', NULL, '2020-02-23 14:01:01', '1063676045212913664', '2020-02-10 00:00:00', NULL, '7527', NULL, NULL, '2020-02-24 16:00:00', NULL, NULL, NULL, '研发部', '2', NULL, 0, NULL, NULL, b'0', NULL, NULL, NULL, 0, NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_company
-- ----------------------------
DROP TABLE IF EXISTS `user_company`;
CREATE TABLE `user_company` (
  `user_id` varchar(40) NOT NULL COMMENT '用户ID',
  `company_id` varchar(255) NOT NULL COMMENT '企业ID',
  `department_id` varchar(255) DEFAULT NULL COMMENT '部门ID',
  `time_of_entry` datetime DEFAULT NULL COMMENT '入职时间',
  `form_of_employment` varchar(255) DEFAULT NULL COMMENT '聘用形式',
  `work_number` varchar(255) DEFAULT NULL COMMENT '工号',
  `form_of_management` varchar(255) DEFAULT NULL COMMENT '管理形式',
  `working_city` varchar(255) DEFAULT NULL COMMENT '工作城市',
  `correction_time` datetime DEFAULT NULL COMMENT '转正时间',
  `in_service_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '在职状态 1.在职  2.离职',
  `state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '启用状态',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_company_jobs
-- ----------------------------
DROP TABLE IF EXISTS `user_company_jobs`;
CREATE TABLE `user_company_jobs` (
  `user_id` varchar(40) NOT NULL COMMENT '员工ID',
  `company_id` varchar(255) DEFAULT NULL COMMENT '企业ID',
  `post` varchar(255) DEFAULT NULL COMMENT '岗位',
  `work_mailbox` varchar(255) DEFAULT NULL COMMENT '工作邮箱',
  `rank` varchar(255) DEFAULT NULL COMMENT '职级',
  `correction_evaluation` text COMMENT '转正评价',
  `report_id` varchar(255) DEFAULT NULL COMMENT '汇报对象',
  `report_name` varchar(255) DEFAULT NULL,
  `state_of_correction` varchar(255) DEFAULT NULL COMMENT '转正状态',
  `hrbp` varchar(255) DEFAULT NULL COMMENT 'hrbp',
  `working_time_for_the_first_time` varchar(255) DEFAULT NULL COMMENT '首次参加工作时间',
  `adjustment_agedays` int(8) DEFAULT NULL COMMENT '调整司龄天',
  `adjustment_of_length_of_service` int(8) DEFAULT NULL COMMENT '调整工龄天',
  `working_city` varchar(255) DEFAULT NULL COMMENT '工作城市',
  `taxable_city` varchar(255) DEFAULT NULL COMMENT '纳税城市',
  `current_contract_start_time` varchar(255) DEFAULT NULL COMMENT '现合同开始时间',
  `closing_time_of_current_contract` varchar(255) DEFAULT NULL COMMENT '现合同结束时间',
  `initial_contract_start_time` varchar(255) DEFAULT NULL COMMENT '首次合同开始时间',
  `first_contract_termination_time` varchar(255) DEFAULT NULL COMMENT '首次合同结束时间',
  `contract_period` varchar(255) DEFAULT NULL COMMENT '合同期限',
  `contract_documents` varchar(255) DEFAULT NULL COMMENT '合同文件',
  `renewal_number` int(8) DEFAULT NULL COMMENT '续签次数',
  `other_recruitment_channels` varchar(255) DEFAULT NULL COMMENT '其他招聘渠道',
  `recruitment_channels` varchar(255) DEFAULT NULL COMMENT '招聘渠道',
  `social_recruitment` varchar(255) DEFAULT NULL COMMENT '社招校招',
  `recommender_business_people` varchar(255) DEFAULT NULL COMMENT '推荐企业人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_company_jobs
-- ----------------------------
BEGIN;
INSERT INTO `user_company_jobs` VALUES ('1063705989926227968', '1', '文员', NULL, NULL, NULL, NULL, NULL, NULL, '1066370498633486336', NULL, 1, NULL, NULL, NULL, '2018-12-25', '2018-12-21', '2018-12-03', '2018-12-04', '6月', NULL, 1, '拉勾网', '猎头', '社招', '百度');
COMMIT;

-- ----------------------------
-- Table structure for user_company_personal
-- ----------------------------
DROP TABLE IF EXISTS `user_company_personal`;
CREATE TABLE `user_company_personal` (
  `user_id` varchar(40) NOT NULL COMMENT '用户ID',
  `username` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `time_of_entry` varchar(255) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) NOT NULL COMMENT '公司ID',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `date_of_birth` varchar(255) DEFAULT NULL COMMENT '出生日期',
  `the_highest_degree_of_education` varchar(255) DEFAULT NULL COMMENT '最高学历',
  `national_area` varchar(255) DEFAULT NULL COMMENT '国家地区',
  `passport_no` varchar(255) DEFAULT NULL COMMENT '护照号',
  `id_number` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `id_card_photo_positive` varchar(255) DEFAULT NULL COMMENT '身份证照片-正面',
  `id_card_photo_back` varchar(255) DEFAULT NULL COMMENT '身份证照片-背面',
  `native_place` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `nation` varchar(255) DEFAULT NULL COMMENT '民族',
  `english_name` varchar(255) DEFAULT NULL COMMENT '英文名',
  `marital_status` varchar(255) DEFAULT NULL COMMENT '婚姻状况',
  `staff_photo` varchar(255) DEFAULT NULL COMMENT '员工照片',
  `birthday` varchar(255) DEFAULT NULL COMMENT '生日',
  `zodiac` varchar(255) DEFAULT NULL COMMENT '属相',
  `age` varchar(255) DEFAULT NULL COMMENT '年龄',
  `constellation` varchar(255) DEFAULT NULL COMMENT '星座',
  `blood_type` varchar(255) DEFAULT NULL COMMENT '血型',
  `domicile` varchar(255) DEFAULT NULL COMMENT '户籍所在地',
  `political_outlook` varchar(255) DEFAULT NULL COMMENT '政治面貌',
  `time_to_join_the_party` varchar(255) DEFAULT NULL COMMENT '入党时间',
  `archiving_organization` varchar(255) DEFAULT NULL COMMENT '存档机构',
  `state_of_children` varchar(255) DEFAULT NULL COMMENT '子女状态',
  `do_children_have_commercial_insurance` varchar(255) DEFAULT NULL COMMENT '子女有无商业保险',
  `is_there_any_violation_of_law_or_discipline` varchar(255) DEFAULT NULL COMMENT '有无违法违纪行为',
  `are_there_any_major_medical_histories` varchar(255) DEFAULT NULL COMMENT '有无重大病史',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信',
  `residence_card_city` varchar(255) DEFAULT NULL COMMENT '居住证城市',
  `date_of_residence_permit` varchar(255) DEFAULT NULL COMMENT '居住证办理日期',
  `residence_permit_deadline` varchar(255) DEFAULT NULL COMMENT '居住证截止日期',
  `place_of_residence` varchar(255) DEFAULT NULL COMMENT '现居住地',
  `postal_address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `contact_the_mobile_phone` varchar(255) DEFAULT NULL,
  `personal_mailbox` varchar(255) DEFAULT NULL,
  `emergency_contact` varchar(255) DEFAULT NULL COMMENT '紧急联系人',
  `emergency_contact_number` varchar(255) DEFAULT NULL COMMENT '紧急联系电话',
  `social_security_computer_number` varchar(255) DEFAULT NULL COMMENT '社保电脑号',
  `provident_fund_account` varchar(255) DEFAULT NULL COMMENT '公积金账号',
  `bank_card_number` varchar(255) DEFAULT NULL COMMENT '银行卡号',
  `opening_bank` varchar(255) DEFAULT NULL COMMENT '开户行',
  `educational_type` varchar(255) DEFAULT NULL COMMENT '学历类型',
  `graduate_school` varchar(255) DEFAULT NULL COMMENT '毕业学校',
  `enrolment_time` varchar(255) DEFAULT NULL COMMENT '入学时间',
  `graduation_time` varchar(255) DEFAULT NULL COMMENT '毕业时间',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `graduation_certificate` varchar(255) DEFAULT NULL COMMENT '毕业证书',
  `certificate_of_academic_degree` varchar(255) DEFAULT NULL COMMENT '学位证书',
  `home_company` varchar(255) DEFAULT NULL COMMENT '上家公司',
  `title` varchar(255) DEFAULT NULL COMMENT '职称',
  `resume` varchar(255) DEFAULT NULL COMMENT '简历',
  `is_there_any_competition_restriction` varchar(255) DEFAULT NULL COMMENT '有无竞业限制',
  `proof_of_departure_of_former_company` varchar(255) DEFAULT NULL COMMENT '前公司离职证明',
  `remarks` text COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_company_personal
-- ----------------------------
BEGIN;
INSERT INTO `user_company_personal` VALUES ('1', 'test1', '13000000001', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('101', 'test101', '13000000101', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('102', 'test102', '13000000102', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('103', 'test103', '13000000103', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('104', 'test104', '13000000104', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('105', 'test105', '13000000105', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('106', 'test106', '13000000106', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('1063705989926227955', 'a02', '13400000002', '2018-02-03', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('1063705989926227961', 'a02', '13400000002', '2018-02-03', '开发部', '1', '', '', '初中', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_company_personal` VALUES ('1063705989926227968', 'aj123', '13800000002', '2018-11-02T00:00:00.000+0000', 'test1', '1', NULL, NULL, '中专', '港澳台国外', '001', '001', NULL, NULL, '香港', NULL, NULL, '未婚', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_company_personal` VALUES ('107', 'test107', '13000000107', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('1074238801402007552', 'a02', '13400000002', '2018-02-01', '开发部', '1', NULL, NULL, '初中', '中国大陆', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_company_personal` VALUES ('1074238801402007555', 'a02', '13400000002', '2018-02-01', '开发部', '1', '', '', '初中', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_company_personal` VALUES ('108', 'test108', '13000000108', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('109', 'test109', '13000000109', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('11', 'test11', '13000000011', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('110', 'test110', '13000000110', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('12', 'test12', '13000000012', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('13', 'test13', '13000000013', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('14', 'test14', '13000000014', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('15', 'test15', '13000000015', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('16', 'test16', '13000000016', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('17', 'test17', '13000000017', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('18', 'test18', '13000000018', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('19', 'test19', '13000000019', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('2', 'test2', '13000000002', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('20', 'test20', '13000000020', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('21', 'test21', '13000000021', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('22', 'test22', '13000000022', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('23', 'test23', '13000000023', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('24', 'test24', '13000000024', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('25', 'test25', '13000000025', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('26', 'test26', '13000000026', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('27', 'test27', '13000000027', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('28', 'test28', '13000000028', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('29', 'test29', '13000000029', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('3', 'test3', '13000000003', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('30', 'test30', '13000000030', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('31', 'test31', '13000000031', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('32', 'test32', '13000000032', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('33', 'test33', '13000000033', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('34', 'test34', '13000000034', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('35', 'test35', '13000000035', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('36', 'test36', '13000000036', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('37', 'test37', '13000000037', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('38', 'test38', '13000000038', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('39', 'test39', '13000000039', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('4', 'test4', '13000000004', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('40', 'test40', '13000000040', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('41', 'test41', '13000000041', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('42', 'test42', '13000000042', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('43', 'test43', '13000000043', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('44', 'test44', '13000000044', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('45', 'test45', '13000000045', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('46', 'test46', '13000000046', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('47', 'test47', '13000000047', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('48', 'test48', '13000000048', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('49', 'test49', '13000000049', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('5', 'test5', '13000000005', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('50', 'test50', '13000000050', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('51', 'test51', '13000000051', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('52', 'test52', '13000000052', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('53', 'test53', '13000000053', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('54', 'test54', '13000000054', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('55', 'test55', '13000000055', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('56', 'test56', '13000000056', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('57', 'test57', '13000000057', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('58', 'test58', '13000000058', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('59', 'test59', '13000000059', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('6', 'test6', '13000000006', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('60', 'test60', '13000000060', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('61', 'test61', '13000000061', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('62', 'test62', '13000000062', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('63', 'test63', '13000000063', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('64', 'test64', '13000000064', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('65', 'test65', '13000000065', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('66', 'test66', '13000000066', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('67', 'test67', '13000000067', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('68', 'test68', '13000000068', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('69', 'test69', '13000000069', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('70', 'test70', '13000000070', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('71', 'test71', '13000000071', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('72', 'test72', '13000000072', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('73', 'test73', '13000000073', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('74', 'test74', '13000000074', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('75', 'test75', '13000000075', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('76', 'test76', '13000000076', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('77', 'test77', '13000000077', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('78', 'test78', '13000000078', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('79', 'test79', '13000000079', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('80', 'test80', '13000000080', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('84', 'test84', '13000000084', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('85', 'test85', '13000000085', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('86', 'test86', '13000000086', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('87', 'test87', '13000000087', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('88', 'test88', '13000000088', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('89', 'test89', '13000000089', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('90', 'test90', '13000000090', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('91', 'test91', '13000000091', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('92', 'test92', '13000000092', '2018-03-01', '开发部', '1', '', '', '初中', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `user_company_personal` VALUES ('93', 'test93', '13000000093', '2018-03-01', '开发部', '1', '', '', '初中', '中国大陆', '1234', '1234', '', '', '1234', '1234', '', '未婚', '', '', '牛', '', '', '', '', '', '', '', '', '', '无', '无', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '统招', '', '', '', '', '', '', '', '', '', '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `role_id` varchar(40) NOT NULL COMMENT '角色ID',
  `user_id` varchar(40) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK74qx7rkbtq2wqms78gljv87a1` (`role_id`),
  KEY `FKee9dk0vg99shvsytflym6egx1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1062944989845262336', '1063705482939731968');
INSERT INTO `user_role` VALUES ('1062944989845262336', '1066370498633486336');
INSERT INTO `user_role` VALUES ('1062944989845262336', '1071632760222810112');
INSERT INTO `user_role` VALUES ('1062944989845262336', '1231579718649249792');
INSERT INTO `user_role` VALUES ('1064098829009293312', '1063705482939731968');
INSERT INTO `user_role` VALUES ('1064098829009293312', '1075383135371350016');
INSERT INTO `user_role` VALUES ('1064098935443951616', '1063705482939731968');
INSERT INTO `user_role` VALUES ('1064098935443951616', '1231579718649249792');
INSERT INTO `user_role` VALUES ('1064099035536822272', '1063705482939731968');
INSERT INTO `user_role` VALUES ('1064099035536822272', '1071632760222810112');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
