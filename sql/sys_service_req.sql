-- MySQL dump 10.17  Distrib 10.3.22-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: rsw
-- ------------------------------------------------------
-- Server version	10.3.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_service_req`
--

DROP TABLE IF EXISTS `sys_service_req`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_service_req` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `host` varchar(1024) DEFAULT NULL COMMENT 'host地址',
  `url` varchar(1024) DEFAULT NULL COMMENT '请求url',
  `time` datetime DEFAULT NULL COMMENT '请求时间',
  `r_size` varchar(32) DEFAULT NULL COMMENT '请求大小',
  `r_file` varchar(512) DEFAULT NULL COMMENT '请求文件',
  `pid` varchar(32) DEFAULT NULL COMMENT '进程号',
  `method` varchar(32) DEFAULT NULL COMMENT '请求方法',
  `col1` varchar(1024) DEFAULT NULL COMMENT '附加列1',
  `col2` varchar(1024) DEFAULT NULL COMMENT '附加列2',
  `col3` varchar(1024) DEFAULT NULL COMMENT '附加列3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务请求查询';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_service_req`
--

LOCK TABLES `sys_service_req` WRITE;
/*!40000 ALTER TABLE `sys_service_req` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_service_req` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


LOCK TABLES `sys_menu` WRITE;

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('服务请求', '0', '9', '#', 'M', '0', '', 'fa fa-link', 'admin', '2020-04-30', 'ry', '2020-04-30', '系统服务目录');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('服务请求-统计', @parentId, '1', '/module/serviceReqpik', 'C', '0', 'module:serviceReq:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '服务请求总图');

UNLOCK TABLES;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-22 14:34:16
