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
-- Table structure for table `sys_file_transfer`
--

DROP TABLE IF EXISTS `sys_file_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_file_transfer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dir` varchar(1024) DEFAULT NULL COMMENT '所在文件夹',
  `name` varchar(1024) DEFAULT NULL COMMENT '文件名',
  `create_time` datetime DEFAULT NULL COMMENT '(tcn-发送/rsw-接受)时间',
  `size` varchar(32) DEFAULT NULL COMMENT '大小',
  `note` text DEFAULT NULL COMMENT '状态',
  `col1` varchar(1024) DEFAULT NULL COMMENT '附加列1',
  `col2` varchar(1024) DEFAULT NULL COMMENT '附加列2',
  `col3` varchar(1024) DEFAULT NULL COMMENT '附加列3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文件传输查询';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file_transfer`
--

LOCK TABLES `sys_file_transfer` WRITE;
/*!40000 ALTER TABLE `sys_file_transfer` DISABLE KEYS */;
INSERT INTO `sys_file_transfer` VALUES (1,'/var/','1.txt','2020-04-21 17:54:41','1000',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_file_transfer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


LOCK TABLES `sys_menu` WRITE;

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('文件传输', '0', '9', '#', 'M', '0', '', 'fa fa-exchange', 'admin', '2018-03-01', 'ry', '2018-03-01', '文件传输目录');

SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('文件传输-查询', @parentId, '2', '/module/fileTransfer', 'C', '0', 'module:fileTransfer:list', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '文件传输总图'),
('文件传输-统计', @parentId, '1', '/module/fileTransferpik', 'C', '0', 'module:fileTransfer:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '文件传输总图');

UNLOCK TABLES;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-22 14:34:16
