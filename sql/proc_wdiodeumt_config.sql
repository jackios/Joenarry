/*
 Navicat Premium Data Transfer

 Source Server         : tcn1
 Source Server Type    : MySQL
 Source Server Version : 100314
 Source Host           : 192.168.10.130:3306
 Source Schema         : tcn

 Target Server Type    : MySQL
 Target Server Version : 100314
 File Encoding         : 65001

 Date: 15/05/2020 09:56:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for proc_wdiodeumt_config
-- ----------------------------
DROP TABLE IF EXISTS `proc_wdiodeumt_config`;
CREATE TABLE `proc_wdiodeumt_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contents` varchar(8192) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置文件内容',
  `data_time` datetime(0) NULL DEFAULT NULL COMMENT '保存日期',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `col1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加列1',
  `col2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加列2',
  `col3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加列3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '端口转发配置文件' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


DROP TABLE IF EXISTS `proc_wdiodeumt_service`;
CREATE TABLE `proc_wdiodeumt_service`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册端口标识，唯一',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `data_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `wid` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'wid号，不能重复，支持w001-w0100',
  `wiport` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开放的端口',
  `woport` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开放的端口',
  `protocol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议tcp或udp',
  `allow_ip` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'tcn1允许接如的IP地址，每个地址之间都好隔开，最大支持10个',
  `in_parmsa` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入口端的参数，端口与上面一直，TCP为TCP-LISTEN:端口，udp为UPD-LISTEN:端口',
  `in_parmsb` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip是rsw1单向光纤网卡的IP，这里端口必须跟上面3个一致，只有TCP',
  `out_parms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '这里的IP和端口值得是要映射的服务器地址和端口，TCP/UDP注意',
  `log_enable` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志是否开启',
  `logfile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志文件位置',
  `is_enable` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'no' COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '端口注册服务' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;




LOCK TABLES `sys_menu` WRITE;

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('端口映射配置', '2003', '0', '#', 'M', '0', '', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '端口映射配置');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('wdiodeumt配置', @parentId, '0', '/module/wdiodeumtConfig', 'C', '0', 'module:wdiodeumtConfig:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', 'wdiodeumt配置'),
('端口注册服务', @parentId, '1', 'module/wdiodeumtService', 'C', '0', 'module:wdiodeumtService:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '端口注册服务');

UNLOCK TABLES;


insert into  sys_config 
values (100,'文件摆渡路径','sys.file.directory','/var/wdiode/','Y','admin','2015-09-02 02:36:57','admin','2015-09-03 06:59:24','文件摆渡路径参数配置');


