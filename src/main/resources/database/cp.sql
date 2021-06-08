/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25-log : Database - cp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cp`;

/*Table structure for table `cp_t_income` */

DROP TABLE IF EXISTS `cp_t_income`;

CREATE TABLE `cp_t_income` (
  `id` char(36) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `income_type` int(11) NOT NULL COMMENT '收入类型',
  `income_date` datetime NOT NULL COMMENT '收入日期',
  `amount` decimal(10,2) NOT NULL COMMENT '发票金额',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发票编号',
  `last_update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开票日期',
  `delete_status` char(1) DEFAULT '0' COMMENT '是否删除0-否/1-是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cp_t_passowrd` */

DROP TABLE IF EXISTS `cp_t_passowrd`;

CREATE TABLE `cp_t_passowrd` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `project_type` varchar(50) DEFAULT NULL COMMENT '项目类型',
  `project_name` varchar(255) NOT NULL COMMENT '项目名称',
  `user_password` varchar(255) DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cp_t_score` */

DROP TABLE IF EXISTS `cp_t_score`;

CREATE TABLE `cp_t_score` (
  `id` char(36) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `score_type_id` char(36) NOT NULL COMMENT '积分类型id',
  `score_type` varchar(50) NOT NULL COMMENT '积分类型',
  `score_date` datetime NOT NULL COMMENT '积分日期',
  `score_value` int(11) NOT NULL COMMENT '积分金额',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发票编号',
  `last_update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开票日期',
  `delete_status` char(1) DEFAULT '0' COMMENT '是否删除0-否/1-是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cp_t_score_type` */

DROP TABLE IF EXISTS `cp_t_score_type`;

CREATE TABLE `cp_t_score_type` (
  `id` char(36) NOT NULL,
  `score_type` varchar(50) NOT NULL COMMENT '积分类型',
  `score_value` int(11) NOT NULL COMMENT '积分金额',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发票编号',
  `last_update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开票日期',
  `delete_status` char(1) DEFAULT '0' COMMENT '是否删除0-否/1-是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `cp_t_user` */

DROP TABLE IF EXISTS `cp_t_user`;

CREATE TABLE `cp_t_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `credential` varchar(255) DEFAULT NULL,
  `user_status` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59140368271 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
