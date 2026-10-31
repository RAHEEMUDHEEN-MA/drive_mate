/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - vehicle_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vehicle_app` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vehicle_app`;

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `feedback` varchar(500) DEFAULT NULL,
  `rating` int(5) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`f_id`,`u_id`,`feedback`,`rating`,`date`) values 
(1,9,'good',5,'2023-01-27');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`user_name`,`password`,`type`) values 
(1,'admin','admin','admin'),
(8,'sharun','sharun9','mechanic'),
(9,'ammu','ammu','user'),
(11,'admfffffffin','jjjjjj','mechanic');

/*Table structure for table `mechanic` */

DROP TABLE IF EXISTS `mechanic`;

CREATE TABLE `mechanic` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT,
  `l_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(6) DEFAULT NULL,
  `phone` int(10) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `mechanic` */

insert  into `mechanic`(`m_id`,`l_id`,`name`,`place`,`post`,`pin`,`phone`,`email`) values 
(4,8,'sharun','payyoli','payyoli',678909,2147483647,'asdds@dfs.mm'),
(6,11,'dfdsf','zfds','dsfds',123456,2147483647,'ra3@gmail.com');

/*Table structure for table `u_request` */

DROP TABLE IF EXISTS `u_request`;

CREATE TABLE `u_request` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `m_id` int(11) DEFAULT NULL,
  `u_id` int(11) DEFAULT NULL,
  `work` varchar(500) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `latitude` decimal(60,0) DEFAULT NULL,
  `longitude` decimal(60,0) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `u_request` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `l_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int(6) DEFAULT NULL,
  `phone` int(10) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`u_id`,`l_id`,`name`,`place`,`post`,`pin`,`phone`,`email`) values 
(1,9,'ammu','kk','kk',678909,99999,'ammu@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
