/*
 Navicat Premium Data Transfer

 Source Server         : sunflow
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : sunflow

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 08/07/2019 22:20:19
*/


-- ----------------------------
-- Table structure for flow_studentapply
-- ----------------------------
DROP TABLE IF EXISTS `flow_studentapply`;
CREATE TABLE `flow_studentapply`  (
  `recordid` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '记录主键',
  `name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学校名称',
  `region` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '年级',
  `dateday` date DEFAULT NULL COMMENT '报名日期',
  `datetime` datetime(0) DEFAULT NULL COMMENT '时间',
  `bmtype` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '报名性质',
  `desc` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_dept
-- ----------------------------
DROP TABLE IF EXISTS `supms_dept`;
CREATE TABLE `supms_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `orders` int(11) DEFAULT NULL COMMENT '序号',
  `description` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1371 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_dictionary_catalog
-- ----------------------------
DROP TABLE IF EXISTS `supms_dictionary_catalog`;
CREATE TABLE `supms_dictionary_catalog`  (
  `catalog_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `catalog_name` varchar(64) CHARACTER SET gbk COLLATE gbk_bin DEFAULT NULL COMMENT '字典名称',
  `catalog_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典描述',
  `catalog_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典类型(S-系统  U-用户)',
  `catalog_struct` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典结构(T-树形   L-列表)',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`catalog_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_dictionary_detail
-- ----------------------------
DROP TABLE IF EXISTS `supms_dictionary_detail`;
CREATE TABLE `supms_dictionary_detail`  (
  `catalog_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `data_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据编码',
  `data_value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据值',
  `data_parent` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级数据编码',
  `data_value2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展值',
  `data_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据描述',
  `data_order` int(11) DEFAULT NULL COMMENT '排序号',
  `create_date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`catalog_code`, `data_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_log
-- ----------------------------
DROP TABLE IF EXISTS `supms_log`;
CREATE TABLE `supms_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) DEFAULT NULL COMMENT '日志创建时间',
  `description` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日志描述',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '异常内容',
  `log_type` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异常类型info/error',
  `method` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作方法',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '操作参数',
  `request_ip` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求IP',
  `time` datetime(0) DEFAULT NULL COMMENT '请求时间',
  `username` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作用户',
  `opt_system` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标记日志来源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8234 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_permission
-- ----------------------------
DROP TABLE IF EXISTS `supms_permission`;
CREATE TABLE `supms_permission`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'api地址',
  `name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `parentRouter` bigint(190) DEFAULT NULL COMMENT '所属菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '权限信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_role_info
-- ----------------------------
DROP TABLE IF EXISTS `supms_role_info`;
CREATE TABLE `supms_role_info`  (
  `ROLE_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编码',
  `ROLE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `ROLE_TYPE` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'F 为系统 固有的 G 全局的 P 公用的 D 部门的 I 为项目角色 W工作量角色',
  `UNIT_CODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色类型',
  `IS_VALID` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ROLE_DESC` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '是否启用',
  `update_Date` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `Create_Date` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建者',
  `updator` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `supms_role_permission`;
CREATE TABLE `supms_role_permission`  (
  `role_code` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编码',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_code`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_role_router
-- ----------------------------
DROP TABLE IF EXISTS `supms_role_router`;
CREATE TABLE `supms_role_router`  (
  `router_id` int(11) NOT NULL COMMENT '路由id',
  `role_code` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`router_id`, `role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_router_info
-- ----------------------------
DROP TABLE IF EXISTS `supms_router_info`;
CREATE TABLE `supms_router_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级id',
  `orders` int(11) DEFAULT NULL COMMENT '序号',
  `path` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '路由地址',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '展示名称',
  `component` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '路由组件',
  `redirect` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '重定向地址',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_third_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `supms_third_auth_user`;
CREATE TABLE `supms_third_auth_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `login_name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联用户表(supms用户)',
  `source` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `access_token` varchar(3072) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆令牌',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `blog` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '博客',
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '绰号',
  `company` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司',
  `location` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '位置',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮件',
  `remark` varchar(3072) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `CREATED_TIME` datetime(0) DEFAULT NULL COMMENT '首次登陆时间',
  `UPDATED_TIME` datetime(0) DEFAULT NULL COMMENT '最近一次登陆时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '第三方权限登陆信息表 用于记录第三方系统登陆本系统中记录和自己用户关联的信息。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `supms_user_dept`;
CREATE TABLE `supms_user_dept`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  `post_id` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '岗位id',
  PRIMARY KEY (`user_id`, `dept_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_user_info
-- ----------------------------
DROP TABLE IF EXISTS `supms_user_info`;
CREATE TABLE `supms_user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `login_name` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登陆名',
  `pwd` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `introduction` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '介绍',
  `avatar` varchar(190) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supms_user_role
-- ----------------------------
DROP TABLE IF EXISTS `supms_user_role`;
CREATE TABLE `supms_user_role`  (
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_code` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编码'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
