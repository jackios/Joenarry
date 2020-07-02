
DROP TABLE IF EXISTS `proc_proxy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proc_proxy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` varchar(128) NOT NULL COMMENT '唯一标识',
  `service_name` varchar(128) NOT NULL COMMENT '服务名称',
  `method` varchar(128) NOT NULL DEFAULT 'GET' COMMENT '支持的操作方法',
  `protocol` varchar(64) NOT NULL DEFAULT 'HTTP/1.1' COMMENT '支持的访问协议',
  `host` varchar(512) NOT NULL DEFAULT '' COMMENT '服务主机名',
  `allows` varchar(512) NOT NULL DEFAULT '' COMMENT '允许的主机IP列表',
  `path` varchar(512) NOT NULL DEFAULT '' COMMENT '服务地址路径',
  `port` int(32) NOT NULL DEFAULT 80 COMMENT '服务端口',

  `element_type` varchar(512) NOT NULL DEFAULT '' COMMENT '访问元素类型',
  `element_type_file_in` varchar(512) DEFAULT '' COMMENT 'IN文件路径',
  `element_type_check_in` varchar(512) DEFAULT '' COMMENT '启用IN文件',
  `element_type_file_out` varchar(512) DEFAULT '' COMMENT 'OUT文件路径',
  `element_type_check_out` varchar(512) DEFAULT '' COMMENT '启用OUT文件',

  `attachment` varchar(6) NOT NULL DEFAULT 'no' COMMENT '是否有附件',
  `attachment_fn` varchar(512) NOT NULL DEFAULT '' COMMENT '访问元素文件名字段',
  `attachment_con` varchar(512) NOT NULL DEFAULT '' COMMENT '附件内容字段',

  `req_backgate` varchar(6) NOT NULL DEFAULT 'no' COMMENT '回传可视化',

  `req_header` varchar(512) NOT NULL DEFAULT '' COMMENT '请求头信息',
  `req_header_c1` varchar(512) NOT NULL DEFAULT '' COMMENT '筛选头信息',
  `req_header_c2` varchar(512) NOT NULL DEFAULT '' COMMENT '筛选头信息',

  `req_urllock` varchar(24) NOT NULL DEFAULT 'no' COMMENT 'URL锁定开关',
  `req_path` varchar(512) NOT NULL DEFAULT '' COMMENT 'URL锁定地址',

  `col1` varchar(50) DEFAULT '' COMMENT '附加列1',
  `col2` varchar(50) DEFAULT '' COMMENT '附加列2',
  `col3` varchar(50) DEFAULT '' COMMENT '附加列3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SERVICE_ID_UNIQ` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='代理配置表';


LOCK TABLES `proc_proxy` WRITE;
UNLOCK TABLES;


update sys_menu set perms="module:oditsec:view" where url= "/module/oditsec";
