/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28 : Database - mgnesium_user_mngt
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mgnesium_user_mngt` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mgnesium_user_mngt`;

/*Table structure for table `uag_operator_log` */

DROP TABLE IF EXISTS `uag_operator_log`;

CREATE TABLE `uag_operator_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `app_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '应用名称',
  `operator_description` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `is_deleted` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志';

/*Data for the table `uag_operator_log` */

/*Table structure for table `uag_user_info_` */

DROP TABLE IF EXISTS `uag_user_info_`;

CREATE TABLE `uag_user_info_` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `is_lock` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否锁定,0否1是',
  `nick_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `uag_user_info_` */

/*Table structure for table `uag_user_info_93331756853` */

DROP TABLE IF EXISTS `uag_user_info_93331756853`;

CREATE TABLE `uag_user_info_93331756853` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `is_lock` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否锁定,0否1是',
  `nick_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `uag_user_info_93331756853` */

/*Table structure for table `uag_user_private_auth_app_info` */

DROP TABLE IF EXISTS `uag_user_private_auth_app_info`;

CREATE TABLE `uag_user_private_auth_app_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,自增',
  `app_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应用名称',
  `app_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应用的openId',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operator_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `is_deleted` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除,0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `uag_user_private_auth_app_info` */

insert  into `uag_user_private_auth_app_info`(`id`,`app_name`,`app_id`,`create_time`,`update_time`,`operator_id`,`operator_name`,`is_deleted`) values (1,'MNGT-PORTAL','93331756853','2020-07-03 22:28:51','2020-07-03 22:28:51',0,'','0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


/*==============================================================*/
/* Table: uag_user_sms_record                                   */
/*==============================================================*/
create table uag_user_sms_record
(
   id                   int unsigned not null auto_increment comment '主键,自增',
   uag_id               int not null default 0 comment '网关用户ID',
   phone_number         varchar(64) not null default '' comment '手机号',
   context              varchar(255) not null default '' comment '内容',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   operator_id          int not null default 0 comment '操作人ID',
   operator_name        varchar(64) not null default '' comment '操作人姓名',
   is_deleted           char(1) not null default '0' comment '逻辑删除,0否1是',
   primary key (id)
);

alter table uag_user_sms_record comment '短信发送记录';

