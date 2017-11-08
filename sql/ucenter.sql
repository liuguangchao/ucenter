
SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------
-- Table structure for `uc_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_token`;
CREATE TABLE `uc_user_token` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uc_user_token
-- ----------------------------
INSERT INTO `uc_user_token` VALUES ('1', '123456', '1', '2017-08-24 14:24:46');
INSERT INTO `uc_user_token` VALUES ('2', '1qaz', '2', '2017-08-24 15:57:04');
INSERT INTO `uc_user_token` VALUES ('3', '2wsx', '3', '2017-09-06 22:35:03');
INSERT INTO `uc_user_token` VALUES ('4', '3edc', '4', '2017-09-06 22:36:10');
INSERT INTO `uc_user_token` VALUES ('5', '100001', '5', '2017-09-12 14:41:02');
INSERT INTO `uc_user_token` VALUES ('6', '100002', '6', '2017-09-12 14:42:00');
INSERT INTO `uc_user_token` VALUES ('7', '100003', '7', '2017-09-12 14:42:12');
INSERT INTO `uc_user_token` VALUES ('8', '100004', '8', '2017-09-12 14:42:24');
INSERT INTO `uc_user_token` VALUES ('9', '100005', '9', '2017-09-12 14:43:06');
INSERT INTO `uc_user_token` VALUES ('10', '100006', '10', '2017-09-12 14:43:18');
INSERT INTO `uc_user_token` VALUES ('11', '100007', '11', '2017-09-12 14:43:28');
INSERT INTO `uc_user_token` VALUES ('12', '100008', '12', '2017-09-12 14:43:44');
INSERT INTO `uc_user_token` VALUES ('13', '100009', '13', '2017-09-12 14:44:12');
INSERT INTO `uc_user_token` VALUES ('14', '100010', '14', '2017-09-12 14:44:24');

-- ----------------------------
-- Table structure for `uc_user`
-- ----------------------------
DROP TABLE IF EXISTS `uc_user`;
CREATE TABLE `uc_user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uc_user
-- ----------------------------
INSERT INTO `uc_user` VALUES ('1', 'test1', '123456', '2017-09-06 21:26:40');
INSERT INTO `uc_user` VALUES ('2', 'test2', '123456', '2017-09-06 21:27:43');
INSERT INTO `uc_user` VALUES ('3', 'test3', '123456', '2017-09-06 21:28:09');
INSERT INTO `uc_user` VALUES ('4', 'test4', '123456', '2017-09-07 09:36:46');
INSERT INTO `uc_user` VALUES ('5', 'test5', '123456', '2017-09-12 14:36:35');
INSERT INTO `uc_user` VALUES ('6', 'test6', '123456', '2017-09-12 14:36:58');
INSERT INTO `uc_user` VALUES ('7', 'test7', '123456', '2017-09-12 14:37:29');
INSERT INTO `uc_user` VALUES ('8', 'test8', '123456', '2017-09-12 14:37:56');
INSERT INTO `uc_user` VALUES ('9', 'test9', '123456', '2017-09-12 14:38:26');
INSERT INTO `uc_user` VALUES ('10', 'test10', '123456', '2017-09-12 14:38:46');
INSERT INTO `uc_user` VALUES ('11', 'test11', '123456', '2017-09-12 14:39:10');
INSERT INTO `uc_user` VALUES ('12', 'test12', '123456', '2017-09-12 14:39:34');
INSERT INTO `uc_user` VALUES ('13', 'test13', '123456', '2017-09-12 14:39:56');
INSERT INTO `uc_user` VALUES ('14', 'test14', '123456', '2017-09-12 14:40:18');



-- ----------------------------
-- Table structure for `uc_authcode`
-- ----------------------------
DROP TABLE IF EXISTS `uc_authcode`;
CREATE TABLE `uc_authcode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tel` varchar(13) NOT NULL,
  `code` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for `biz_apilog`
-- ----------------------------
DROP TABLE IF EXISTS `biz_apilog`;
CREATE TABLE `biz_apilog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `req` text DEFAULT NULL,
  `resp` text DEFAULT NULL,
  `imei` varchar(128) DEFAULT NULL,
  `rstatus` int(4) NOT NULL DEFAULT '0',
  `rmsg` text DEFAULT NULL,
  `time` int(11) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





-- ----------------------------
-- Table structure for `biz_smslog`
-- ----------------------------
DROP TABLE IF EXISTS `biz_smslog`;
CREATE TABLE `biz_smslog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `mobile` varchar(13) NOT NULL,
  `tpl_code` varchar(20) NOT NULL,
  `tpl_param` text NOT NULL,
  `rstatus` int(4) NOT NULL DEFAULT '0',
  `rmsg` text DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



