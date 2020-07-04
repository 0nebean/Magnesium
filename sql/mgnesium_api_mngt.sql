/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28 : Database - mgnesium_api_mngt_1
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mgnesium_api_mngt` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mgnesium_api_mngt`;

/*Table structure for table `t_api_info` */

DROP TABLE IF EXISTS `t_api_info`;

CREATE TABLE `t_api_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `api_name` varchar(64) NOT NULL DEFAULT '' COMMENT '接口名称',
  `proxy_path` varchar(255) DEFAULT NULL,
  `api_uri` varchar(225) NOT NULL DEFAULT '' COMMENT '接口地址',
  `api_status` char(1) NOT NULL DEFAULT '0' COMMENT '接口状态 0:未启用 1启用',
  `server_id` int(11) NOT NULL DEFAULT '0' COMMENT '服务ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='接口信息';

/*Data for the table `t_api_info` */

insert  into `t_api_info`(`id`,`api_name`,`proxy_path`,`api_uri`,`api_status`,`server_id`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,'发送登录短信','/auth/sendLoginSms','/auth/sendLoginSms','1',1,'2020-06-29 00:54:05','2020-06-29 00:54:05',0,'','0'),(2,'短信注册登录二合一','/auth/smsCodeLoginRegister','/auth/smsCodeLoginRegister','1',1,'2020-06-29 00:56:16','2020-06-29 00:56:16',0,'','0'),(3,'获取当前应用登录方式','/auth/getCurrentAppLoginType','/auth/getCurrentAppLoginType','1',2,'2020-07-02 15:17:48','2020-07-02 15:17:48',0,'','0'),(4,'短信验证码登录','/auth/smsCodeLogin','/auth/smsCodeLogin','1',1,'2020-07-02 15:18:31','2020-07-02 15:18:31',0,'','0'),(5,'短信验证码注册','/auth/smsCodeRegister','/auth/smsCodeRegister','1',1,'2020-07-02 15:18:51','2020-07-02 15:18:51',0,'','0'),(6,'密码登录','/auth/passwordLogin','/auth/passwordLogin','1',1,'2020-07-02 15:19:08','2020-07-02 15:19:08',0,'','0'),(7,'密码注册','/auth/passwordRegister','/auth/passwordRegister','1',1,'2020-07-02 15:19:24','2020-07-02 15:19:24',0,'','0'),(8,'用户API登出操作','/auth/logOut','/auth/logOut','1',1,'2020-07-02 15:19:56','2020-07-02 15:19:56',0,'','0'),(9,'单点登录页面登出操作','/auth/uagSsoLogOut','/auth/uagSsoLogOut','1',1,'2020-07-02 15:20:47','2020-07-02 15:20:47',0,'','0'),(10,'获取当前登录用户信息','/auth/checkUagLoginInfo','/auth/checkUagLoginInfo','1',1,'2020-07-02 15:21:23','2020-07-02 15:21:23',0,'','0');

/*Table structure for table `t_app_api` */

DROP TABLE IF EXISTS `t_app_api`;

CREATE TABLE `t_app_api` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `app_id` int(11) NOT NULL DEFAULT '0' COMMENT '应用ID',
  `api_id` int(11) NOT NULL DEFAULT '0' COMMENT '接口ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='应用接口关联关系';

/*Data for the table `t_app_api` */

insert  into `t_app_api`(`id`,`app_id`,`api_id`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,1,3,'2020-07-02 15:21:44','2020-07-02 15:21:44',0,'','0'),(2,1,1,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(3,1,2,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(4,1,4,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(5,1,5,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(6,1,6,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(7,1,7,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(8,1,8,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(9,1,9,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0'),(10,1,10,'2020-07-02 15:22:01','2020-07-02 15:22:01',0,'','0');

/*Table structure for table `t_app_info` */

DROP TABLE IF EXISTS `t_app_info`;

CREATE TABLE `t_app_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `login_type` char(1) NOT NULL DEFAULT '' COMMENT '登录模式 0:通用验证码登录 1:账号密码登录',
  `auth_type` char(1) NOT NULL DEFAULT '0' COMMENT '授权模式 0:oauth授权码,1:oauth私有令牌,2:ip白名单+通信令牌,3:oauth私有免登陆令牌',
  `inner_api_token` varchar(64) NOT NULL DEFAULT '' COMMENT 'inner api 访问令牌',
  `open_id` varchar(64) NOT NULL DEFAULT '' COMMENT '对外暴露应用ID',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '秘钥',
  `app_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'app名称',
  `app_status` char(1) NOT NULL DEFAULT '0' COMMENT 'app状态 0:连接 1:断开',
  `oauth_base_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'oauth 授权url',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='应用信息';

/*Data for the table `t_app_info` */

insert  into `t_app_info`(`id`,`login_type`,`auth_type`,`inner_api_token`,`open_id`,`secret`,`app_name`,`app_status`,`oauth_base_url`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,'2','1','','93331756853','38bbe9398a1fcecf3f820a0a90d4c110','MNGT-PORTAL','0','https://devmngt.onebean.net','2020-06-28 16:09:17','2020-07-02 15:44:39',0,'','0');

/*Table structure for table `t_ip_white_list` */

DROP TABLE IF EXISTS `t_ip_white_list`;

CREATE TABLE `t_ip_white_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ip_address` varchar(64) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ip白名单';

/*Data for the table `t_ip_white_list` */

/*Table structure for table `t_server_info` */

DROP TABLE IF EXISTS `t_server_info`;

CREATE TABLE `t_server_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `server_name` varchar(64) NOT NULL DEFAULT '' COMMENT '服务名称',
  `deploy_type` char(1) NOT NULL DEFAULT '0' COMMENT '部署类型 0:物理地址部署 1:marathon部署',
  `upsteam_node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称',
  `selected_version` varchar(64) NOT NULL DEFAULT '' COMMENT '选中版本',
  `is_front` char(1) NOT NULL DEFAULT '0' COMMENT '是否是前端项目,0否1是',
  `is_ssl` char(1) NOT NULL DEFAULT '0' COMMENT '是否开启ssl',
  `listen_port` varchar(32) NOT NULL DEFAULT '' COMMENT '监听端口号',
  `ssl_listen_port` varchar(32) NOT NULL DEFAULT '' COMMENT 'ssl监听端口号',
  `server_host` varchar(64) NOT NULL DEFAULT '' COMMENT '域名',
  `ssl_crt_key_path` varchar(64) NOT NULL DEFAULT '' COMMENT '证书路径',
  `ssl_crt_path` varchar(64) NOT NULL DEFAULT '' COMMENT '证书key路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='服务信息';

/*Data for the table `t_server_info` */

insert  into `t_server_info`(`id`,`server_name`,`deploy_type`,`upsteam_node_name`,`selected_version`,`is_front`,`is_ssl`,`listen_port`,`ssl_listen_port`,`server_host`,`ssl_crt_key_path`,`ssl_crt_path`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,'USER-MNGT','0','user-mngt','','0','0','','','','','','2020-06-28 16:05:03','2020-07-04 11:31:10',0,'','0'),(2,'API-MNGT','0','api-mngt','','0','0','','','','','','2020-06-28 16:06:00','2020-07-04 11:31:11',0,'','0'),(3,'MNGT-PORTAL','0','mngt-portal','','0','0','','','','','','2020-06-28 16:18:33','2020-07-04 11:31:12',0,'','0');

/*Table structure for table `t_server_machine_node` */

DROP TABLE IF EXISTS `t_server_machine_node`;

CREATE TABLE `t_server_machine_node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `ip_address` varchar(64) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `access_user` varchar(64) NOT NULL DEFAULT '' COMMENT '访问账户',
  `access_password` varchar(255) NOT NULL DEFAULT '' COMMENT '访问密码',
  `access_rsa_path` varchar(100) NOT NULL DEFAULT '' COMMENT '访问rsa_path',
  `access_port` varchar(32) NOT NULL DEFAULT '' COMMENT '访问端口号',
  `access_auth_type` char(1) NOT NULL DEFAULT '0' COMMENT '访问授权方式 (0: 密码模式,1:公私钥模式)',
  `server_machine_type` char(1) NOT NULL DEFAULT '0' COMMENT '服务节点类型,0：openresty，1：kubernetes-master',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_server_machine_node` */

/*Table structure for table `t_un_login_access_api_white_list` */

DROP TABLE IF EXISTS `t_un_login_access_api_white_list`;

CREATE TABLE `t_un_login_access_api_white_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `api_id` int(11) NOT NULL DEFAULT '0' COMMENT '接口ID',
  `app_id` int(11) NOT NULL DEFAULT '0' COMMENT '应用ID',
  `api_name` varchar(64) NOT NULL DEFAULT '' COMMENT '应用名',
  `api_path` varchar(255) NOT NULL DEFAULT '' COMMENT 'API接口',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='易开伙伴未登录访问接口白名单';

/*Data for the table `t_un_login_access_api_white_list` */

insert  into `t_un_login_access_api_white_list`(`id`,`api_id`,`app_id`,`api_name`,`api_path`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,2,1,'短信注册登录二合一','/auth/smsCodeLoginRegister','2020-07-02 15:28:16','2020-07-02 15:28:16',0,'','0'),(2,5,1,'短信验证码注册','/auth/smsCodeRegister','2020-07-02 15:28:21','2020-07-02 15:28:21',0,'','0'),(3,7,1,'密码注册','/auth/passwordRegister','2020-07-02 15:28:26','2020-07-02 15:28:26',0,'','0'),(4,3,1,'获取当前应用登录方式','/auth/getCurrentAppLoginType','2020-07-02 15:28:36','2020-07-02 15:28:36',0,'','0'),(5,1,1,'发送登录短信','/auth/sendLoginSms','2020-07-02 15:28:41','2020-07-02 15:28:41',0,'','0'),(6,4,1,'短信验证码登录','/auth/smsCodeLogin','2020-07-02 15:28:53','2020-07-02 15:28:53',0,'','0'),(7,6,1,'密码登录','/auth/passwordLogin','2020-07-02 15:28:58','2020-07-02 15:28:58',0,'','0'),(8,10,1,'获取当前登录用户信息','/auth/checkUagLoginInfo','2020-07-02 19:40:22','2020-07-02 19:40:22',0,'','0');

/*Table structure for table `t_upsteam_name` */

DROP TABLE IF EXISTS `t_upsteam_name`;

CREATE TABLE `t_upsteam_name` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `deploy_type` char(1) NOT NULL DEFAULT '0' COMMENT '部署类型 0:物理地址部署 1:kubernetes部署',
  `upsteam_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `t_upsteam_name` */

insert  into `t_upsteam_name`(`id`,`deploy_type`,`upsteam_name`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (6,'0','user-mngt','2020-07-03 11:12:46','2020-07-03 11:12:46',0,'','0'),(7,'0','api-mngt','2020-07-03 11:14:07','2020-07-03 11:14:07',0,'','0'),(8,'0','mngt-portal','2020-07-03 11:32:58','2020-07-03 11:32:58',0,'','0');

/*Table structure for table `t_upsteam_node` */

DROP TABLE IF EXISTS `t_upsteam_node`;

CREATE TABLE `t_upsteam_node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称',
  `node_namespace` varchar(64) NOT NULL DEFAULT '' COMMENT '节点命名空间',
  `selected_version` varchar(64) NOT NULL DEFAULT '' COMMENT '选中版本',
  `current_version` varchar(64) NOT NULL DEFAULT '' COMMENT '当前版本',
  `deploy_type` char(1) NOT NULL DEFAULT '0' COMMENT '部署类型 0:物理地址部署 1:kubernetes部署',
  `physical_path` varchar(255) NOT NULL DEFAULT '' COMMENT '物理地址',
  `running_status` char(1) NOT NULL DEFAULT '0' COMMENT '运行状态，0运行中，1已停止',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deprecated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '弃用时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_upsteam_node` */

insert  into `t_upsteam_node`(`id`,`node_name`,`node_namespace`,`selected_version`,`current_version`,`deploy_type`,`physical_path`,`running_status`,`create_time`,`update_time`,`deprecated_time`,`operator_id`,`operator_name`,`is_deleted`) values (10,'user-mngt','','','','0','','0','2020-07-03 12:44:39','2020-07-04 11:32:03','2020-07-03 12:45:34',0,'','0'),(17,'api-mngt','','','','0','','0','2020-07-03 22:26:51','2020-07-04 11:32:04','2020-07-03 22:27:34',0,'','0'),(18,'mngt-portal','','','','0','','0','2020-07-03 22:47:14','2020-07-04 11:32:05','2020-07-01 22:48:41',0,'','0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
