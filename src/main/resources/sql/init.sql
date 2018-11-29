/*
Navicat MySQL Data Transfer

Source Server         : myDb
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : test_db

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-11-28 14:45:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for live_user_info
-- ----------------------------
DROP TABLE IF EXISTS `live_user_info`;
CREATE TABLE `live_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '注册用户登录名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '注册用户真实姓名',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '注册用户手机号码',
  `idcard` varchar(18) DEFAULT NULL COMMENT '注册用户身份证号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `age` int(11) DEFAULT NULL COMMENT '注册用户年龄',
  `gender` char(1) DEFAULT NULL COMMENT '性别 1:男 2:女',
  `hobby` varchar(64) DEFAULT '' COMMENT '个人爱好',
  `locate_province` varchar(32) DEFAULT NULL COMMENT '所在省',
  `locate_city` varchar(32) DEFAULT NULL COMMENT '所在市',
  `locate_area` varchar(32) DEFAULT NULL COMMENT '所在区',
  `address` varchar(128) DEFAULT NULL COMMENT '所在详细地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `head_url` varchar(255) DEFAULT NULL COMMENT '用户头像照片url',
  `photo_url` varchar(255) DEFAULT NULL COMMENT '用户照片url',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最新更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='注册用户信息表';

-- ----------------------------
-- Records of live_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统日志主键',
  `content` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `uri` varchar(128) DEFAULT NULL,
  `parameter` varchar(2048) DEFAULT NULL COMMENT '操作参数',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `operator_name` varchar(16) DEFAULT NULL COMMENT '操作人',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `perm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `perm_name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `perm_desc` varchar(64) DEFAULT NULL COMMENT '权限描述',
  `perm_pid` int(11) DEFAULT NULL COMMENT '父权限id',
  `perm_url` varchar(64) DEFAULT NULL COMMENT '权限路径',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `is_show` varchar(1) DEFAULT '1' COMMENT '是否显示 0不显示 1显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '系统管理', '系统管理', null, null, '1', '1', '2018-11-07 12:19:37');
INSERT INTO `sys_permission` VALUES ('2', '系统用户管理', '系统用户管理', '1', '/admin/sysUser', '1', '1', '2018-11-07 12:22:07');
INSERT INTO `sys_permission` VALUES ('3', '角色管理', '角色管理', '1', '/admin/sysRole', '2', '1', '2018-11-07 12:26:34');
INSERT INTO `sys_permission` VALUES ('4', '权限管理', '权限管理', '1', '/admin/sysPermission', '3', '1', '2018-11-07 12:27:34');
INSERT INTO `sys_permission` VALUES ('5', '注册用户管理', '注册用户管理', null, null, '2', '1', '2018-11-07 12:23:27');
INSERT INTO `sys_permission` VALUES ('6', '其他管理', '其他管理', null, null, '3', '1', '2018-11-07 12:25:34');
INSERT INTO `sys_permission` VALUES ('7', 'demo', 'demo', '6', '/admin/demo/page', '1', '1', '2018-11-07 12:26:05');
INSERT INTO `sys_permission` VALUES ('8', '待开发', '待开发', '6', '/admin/undo/page', '2', '1', '2018-11-07 12:26:34');
INSERT INTO `sys_permission` VALUES ('9', '系统日志', '系统日志', '1', '/admin/sysLog', '4', '1', '2018-11-07 12:26:34');
INSERT INTO `sys_permission` VALUES ('10', '注册用户管理', '', '5', '/admin/userInfo', '1', '1', '2018-11-22 12:01:34');
INSERT INTO `sys_permission` VALUES ('11', 'CURD实例', '增删改查实例', '6', '', '3', '1', '2018-11-23 10:08:25');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '拥有所有权限', '2018-11-07 12:16:50');
INSERT INTO `sys_role` VALUES ('2', '普通管理员', '拥有普通权限', '2018-11-07 17:43:05');
INSERT INTO `sys_role` VALUES ('3', '注册用户管理员', '管理用户的系列事务', '2018-11-12 20:17:46');
INSERT INTO `sys_role` VALUES ('4', '待开发', '待开发用', '2018-11-13 17:26:03');
INSERT INTO `sys_role` VALUES ('5', '测试', '测试用', '2018-11-13 23:24:15');

-- ----------------------------
-- Table structure for sys_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `perm_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='角色-权限映射表';

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES ('1', '1', '1', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('2', '1', '2', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('3', '1', '3', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('4', '1', '4', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('6', '1', '6', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('7', '1', '7', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('8', '1', '8', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('9', '1', '9', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('10', '1', '10', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('11', '1', '11', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('12', '1', '12', '2018-11-14 14:14:00');
INSERT INTO `sys_role_perm` VALUES ('13', '1', '13', '2018-11-14 14:14:00');


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '所属角色id',
  `role_name` varchar(50) DEFAULT NULL COMMENT '所属角色 (冗余设计)',
  `username` varchar(50) NOT NULL COMMENT '系統用户名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '系统用户真实姓名',
  `password` varchar(50) NOT NULL COMMENT '系统用户密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '系统用户手机号码',
  `valid` varchar(1) DEFAULT '1' COMMENT '帐号是否有效 0 无效 1有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '超级管理员', 'admin', '系统管理员', '123456', '', '1', '2018-11-07 12:18:23');
INSERT INTO `sys_user` VALUES ('2', '2', '普通管理员', 'jvxb', '抓娃小兵', '123456', '', '1', '2018-11-07 21:27:42');
INSERT INTO `sys_user` VALUES ('3', '5', '测试', 'test', 'test', 'test', '', '1', '2018-11-13 15:28:09');
