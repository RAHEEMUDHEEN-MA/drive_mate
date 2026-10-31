/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 8.0.30 : Database - vehicle_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vehicle_app` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `vehicle_app`;

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `f_id` int NOT NULL AUTO_INCREMENT,
  `l_id` int DEFAULT NULL,
  `feedbacknote` varchar(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `fdate` date DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`f_id`,`l_id`,`feedbacknote`,`rating`,`fdate`) values 
(1,2,'nice one',3,'2023-05-06'),
(2,22,'',5,'2023-05-07'),
(3,21,'nice app ',5,'2023-05-08');

/*Table structure for table `fpumbrequest` */

DROP TABLE IF EXISTS `fpumbrequest`;

CREATE TABLE `fpumbrequest` (
  `fr_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `fpumbrequest` */

insert  into `fpumbrequest`(`fr_id`,`name`,`phone`,`email`,`place`,`username`,`password`) values 
(1,'abcfuel',987456321,'abcfuel@gamila','omassery','abcfuel','abc'),
(2,'nayarakkd',9999,'788','9898989899','nayarakkd','123'),
(3,'nayara',9898989898,'nayara@gmail.com','kattangal','nayarak','123');

/*Table structure for table `fueldelivaryreq` */

DROP TABLE IF EXISTS `fueldelivaryreq`;

CREATE TABLE `fueldelivaryreq` (
  `fdelv_id` int NOT NULL AUTO_INCREMENT,
  `f_id` int DEFAULT NULL,
  `l_id` int DEFAULT NULL,
  `fuel_type` char(10) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date` varchar(400) DEFAULT NULL,
  `status` char(100) DEFAULT NULL,
  PRIMARY KEY (`fdelv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `fueldelivaryreq` */

insert  into `fueldelivaryreq`(`fdelv_id`,`f_id`,`l_id`,`fuel_type`,`quantity`,`date`,`status`) values 
(1,11,20,'diesel',11,'2023-05-07','pending'),
(2,11,21,'diesel',11,'2023-05-07','pending'),
(3,10,21,'petrol',11,'2023-05-07','pending'),
(4,11,21,'petrol',11,'2023-05-07','pending');

/*Table structure for table `fuelpump` */

DROP TABLE IF EXISTS `fuelpump`;

CREATE TABLE `fuelpump` (
  `f_id` int NOT NULL AUTO_INCREMENT,
  `l_id` int DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `fuelpump` */

insert  into `fuelpump`(`f_id`,`l_id`,`name`,`place`,`phone`,`email`) values 
(1,8,'bharath tdy','Thiruvambady ',9878654321,'bharath@Gmail.com'),
(2,10,'indianoil tdy','Thiruvambady ',7890654432,'abc@gmail'),
(3,11,'reliancetdy','tdy',9876543210,'reliance @gmail'),
(4,12,'fuel1','abc',8768906541,'hqhq@gmail'),
(5,13,'hpmukkam','mukkam',9876543211,'haa@gmail.com');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `loc_id` int NOT NULL AUTO_INCREMENT,
  `lid` int DEFAULT NULL,
  `latitude` varchar(300) DEFAULT NULL,
  `longitude` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`loc_id`,`lid`,`latitude`,`longitude`) values 
(1,2,'11.376029','76.0208763'),
(2,3,'11.36763204','76.02503577'),
(3,4,'41.36769773','71.02498409'),
(4,5,'11.376029','76.0208763'),
(5,6,'11.3676953','76.02499177'),
(6,7,'11.36769343','76.02499223'),
(7,10,'11.36768431','76.02500566'),
(8,8,'11.376029','76.0208763'),
(9,11,'12.36768693','78.02500513'),
(10,20,'11.376029','76.0208763'),
(11,21,'11.2580535','75.7834772'),
(12,22,'11.2557588','75.7860203'),
(13,23,'11.2577892','75.7845342'),
(14,12,'11.376029','76.0208763'),
(15,25,'11.3676721','76.02515965'),
(16,27,'11.3761163','76.0226897'),
(17,28,'11.376029','76.0208763');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`user_name`,`password`,`type`) values 
(1,'admin','admin123','admin'),
(2,'raheem','123','user'),
(3,'raheemmec','123','mechanic'),
(4,'yadhumec','123','mechanic'),
(5,'anas','123','user'),
(6,'siyasmec','123','mechanic'),
(7,'sanu','123','user'),
(8,'bharath','123','fuelpump'),
(10,'indian','123','fuelpump'),
(11,'reliance','123','fuelpump'),
(12,'fuel1','123','fuelpump'),
(13,'hp','123','fuelpump'),
(14,'hp','123','fuelpump'),
(20,'user2','123','user'),
(21,'user1','123','user'),
(22,'Shan','123','user'),
(23,'sanuuser','123','user'),
(25,'user','123','user'),
(27,'ramesh','ramesh123','mechanic'),
(28,'amal','123','mechanic');

/*Table structure for table `mecfeedback` */

DROP TABLE IF EXISTS `mecfeedback`;

CREATE TABLE `mecfeedback` (
  `mf_id` int NOT NULL AUTO_INCREMENT,
  `m_id` int DEFAULT NULL,
  `u_id` int DEFAULT NULL,
  `feedback` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`mf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `mecfeedback` */

insert  into `mecfeedback`(`mf_id`,`m_id`,`u_id`,`feedback`,`rating`,`date`) values 
(1,3,2,'nice job',3.5,'2023-05-06'),
(2,4,5,'yfyf',4.5,'2023-05-06'),
(3,6,7,'good',3,'2023-05-06'),
(4,6,20,'nice',4,'2023-05-07'),
(5,6,20,'good',5,'2023-05-07'),
(6,3,22,'Super ',5,'2023-05-07');

/*Table structure for table `mechanic` */

DROP TABLE IF EXISTS `mechanic`;

CREATE TABLE `mechanic` (
  `m_id` int NOT NULL AUTO_INCREMENT,
  `l_id` int DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `mechanic` */

insert  into `mechanic`(`m_id`,`l_id`,`name`,`place`,`post`,`pin`,`phone`,`email`) values 
(1,3,'RAHEEMUDHEENmec','koodaranji','thiruvambady ',673603,8113044223,'raheemudheenma1182@gmail.com'),
(2,4,'YADHU mec','thikkodi','thikkodi',765433,9013044223,'yadh@gmai'),
(3,6,'siyasmec','tdy','tdy',765456,98776543321,'hahaja@gmaol'),
(4,27,'ramesh','kadav','omassery',87654,9876543210,'rajesg@gamail.com'),
(5,28,'amal','kkakyam','kakkayam',687656,9876567899,'amal@');

/*Table structure for table `mecrequest` */

DROP TABLE IF EXISTS `mecrequest`;

CREATE TABLE `mecrequest` (
  `r_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` int DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;

/*Data for the table `mecrequest` */

insert  into `mecrequest`(`r_id`,`name`,`place`,`post`,`pin`,`phone`,`email`,`username`,`password`) values 
(100,'abc','cde','ert',123456,9744934823,'sub@gam','abc','abc'),
(102,'abdhu','koduvally','kuduvally',765456,9876554321,'kdy@','abdhu','123');

/*Table structure for table `u_request` */

DROP TABLE IF EXISTS `u_request`;

CREATE TABLE `u_request` (
  `r_id` int NOT NULL AUTO_INCREMENT,
  `ml_id` int DEFAULT NULL,
  `ul_id` int DEFAULT NULL,
  `work` varchar(500) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `u_request` */

insert  into `u_request`(`r_id`,`ml_id`,`ul_id`,`work`,`date`,`status`) values 
(1,3,2,'my pologt is over heating','2023-05-06','pending'),
(2,3,2,'access down','2023-05-06','pending'),
(3,4,5,'maruthi went down','2023-05-06','pending'),
(4,6,7,'completely pett','2023-05-06','pending'),
(5,3,20,'come here','2023-05-07','accepted'),
(6,6,20,'come pls ','2023-05-07','pending'),
(7,3,20,'come here pls','2023-05-07','pending'),
(8,3,21,'1234555','2023-05-07','pending'),
(9,3,22,'Engine sound ','2023-05-07','pending');

/*Table structure for table `userlist` */

DROP TABLE IF EXISTS `userlist`;

CREATE TABLE `userlist` (
  `u_id` int NOT NULL AUTO_INCREMENT,
  `l_id` int DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `post` varchar(50) DEFAULT NULL,
  `pin` bigint DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `userlist` */

insert  into `userlist`(`u_id`,`l_id`,`name`,`place`,`post`,`pin`,`phone`,`email`) values 
(1,2,'RAHEEMUDHEEN','pambizhanjapara ','Thiruvambady ',673603,8113044223,'raheemudheenma118@gmail.com'),
(2,5,'anas','mukkam','mukkam',765432,1234567899,'ans@gmail.com'),
(3,7,'sanu','abc','abvcs',765789,8313044223,'Raheem@gmall'),
(4,19,'user2','abc','avvc',76621,9876543321,'gagh@gmaol'),
(5,20,'user2','abc','bcde',765432,8113044223,'abc@gmai'),
(6,21,'user1','abc','abcx',765432,9876543321,'anja@haha'),
(7,22,'Shahan ','Iringath ','Iringath ',673523,9400213369,'Muhammedshahan3690@gmail.com '),
(8,23,'sanu','kakkodi','kakkdi',654322,8113044266,'sanu@gmail'),
(9,25,'bha','bah','jeje',1233,82882,'hshab');

/*Table structure for table `vehicles` */

DROP TABLE IF EXISTS `vehicles`;

CREATE TABLE `vehicles` (
  `v_id` int NOT NULL AUTO_INCREMENT,
  `l_id` int DEFAULT NULL,
  `vname` varchar(30) DEFAULT NULL,
  `vnumber` varchar(20) DEFAULT NULL,
  `model` int DEFAULT NULL,
  `rcbook` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `ins` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `polution` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`v_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `vehicles` */

insert  into `vehicles`(`v_id`,`l_id`,`vname`,`vnumber`,`model`,`rcbook`,`ins`,`polution`) values 
(1,2,'bajajns160','kl57u3327',2019,'storage_emulated_0_DCIM_Camera_PXL_20230506_172452095.jpg','storage_emulated_0_DCIM_Camera_PXL_20230506_172500420.jpg','storage_emulated_0_DCIM_Camera_PXL_20230506_172503118.jpg'),
(3,2,'swift','kl7666ag',2099,'storage_emulated_0_Pictures_Telegram_IMG_20230421_231356_308.jpg','storage_emulated_0_Pictures_Telegram_IMG_20230421_231356_308.jpg','storage_emulated_0_Pictures_Telegram_IMG_20230421_231356_308.jpg'),
(4,2,'polo','kl57t443',2099,'storage_emulated_0_Pictures_Screenshots_Screenshot_20230507-110927.png','storage_emulated_0_Pictures_Telegram_IMG_20230421_231356_308.jpg','storage_emulated_0_Pictures_Telegram_IMG_20230421_231356_308.jpg'),
(5,2,'maruthi800','kl77t6262',2000,'storage_emulated_0_Pictures_Screenshots_Screenshot_20230507-110938.png','storage_emulated_0_Pictures_Screenshots_Screenshot_20230507-110938.png','storage_emulated_0_Pictures_Screenshots_Screenshot_20230507-110938.png'),
(7,5,'thar','kl67111',2020,'storage_emulated_0_DCIM_PhotosEditor_1d2a63b6f148c36184bdecfd782e0b032.jpg','storage_emulated_0_DCIM_PhotosEditor_PXL_20201126_1225109452.jpg','storage_emulated_0_DCIM_PhotosEditor_bb2f74908dc1db86226af736e152f3a2-012.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
