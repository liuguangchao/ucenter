
SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------
-- Table structure for `token_info`
-- ----------------------------
DROP TABLE IF EXISTS `token_info`;
CREATE TABLE `token_info` (
  `t_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(128) NOT NULL,
  `user_id` int(11) NOT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `unique_idx_token` (`token`),
  UNIQUE KEY `unique_idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of token_info
-- ----------------------------
INSERT INTO `token_info` VALUES ('1', '123456', '1', '2017-08-24 14:24:46');
INSERT INTO `token_info` VALUES ('2', '1qaz', '2', '2017-08-24 15:57:04');
INSERT INTO `token_info` VALUES ('3', '2wsx', '3', '2017-09-06 22:35:03');
INSERT INTO `token_info` VALUES ('4', '3edc', '4', '2017-09-06 22:36:10');
INSERT INTO `token_info` VALUES ('5', '100001', '5', '2017-09-12 14:41:02');
INSERT INTO `token_info` VALUES ('6', '100002', '6', '2017-09-12 14:42:00');
INSERT INTO `token_info` VALUES ('7', '100003', '7', '2017-09-12 14:42:12');
INSERT INTO `token_info` VALUES ('8', '100004', '8', '2017-09-12 14:42:24');
INSERT INTO `token_info` VALUES ('9', '100005', '9', '2017-09-12 14:43:06');
INSERT INTO `token_info` VALUES ('10', '100006', '10', '2017-09-12 14:43:18');
INSERT INTO `token_info` VALUES ('11', '100007', '11', '2017-09-12 14:43:28');
INSERT INTO `token_info` VALUES ('12', '100008', '12', '2017-09-12 14:43:44');
INSERT INTO `token_info` VALUES ('13', '100009', '13', '2017-09-12 14:44:12');
INSERT INTO `token_info` VALUES ('14', '100010', '14', '2017-09-12 14:44:24');

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `dv` varchar(128) DEFAULT NULL COMMENT '设备固件版本号',
  `sd` varchar(128) DEFAULT NULL COMMENT '软件版本号',
  `imei` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bindingtime` timestamp NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'test1', '123456', null, null, '85988233', '2017-09-06 21:26:40', null);
INSERT INTO `user_info` VALUES ('2', 'test2', '123456', null, null, '869758001213076', '2017-09-06 21:27:43', null);
INSERT INTO `user_info` VALUES ('3', 'test3', '123456', null, null, '869758001213084', '2017-09-06 21:28:09', null);
INSERT INTO `user_info` VALUES ('4', 'test4', '123456', null, null, '123456789012380', '2017-09-07 09:36:46', null);
INSERT INTO `user_info` VALUES ('5', 'test5', '123456', null, null, '869758001213076', '2017-09-12 14:36:35', null);
INSERT INTO `user_info` VALUES ('6', 'test6', '123456', null, null, '869758001213084', '2017-09-12 14:36:58', null);
INSERT INTO `user_info` VALUES ('7', 'test7', '123456', null, null, '869758001213092', '2017-09-12 14:37:29', null);
INSERT INTO `user_info` VALUES ('8', 'test8', '123456', null, null, '869758001213100', '2017-09-12 14:37:56', null);
INSERT INTO `user_info` VALUES ('9', 'test9', '123456', null, null, '869758001213118', '2017-09-12 14:38:26', null);
INSERT INTO `user_info` VALUES ('10', 'test10', '123456', null, null, '869758001213126', '2017-09-12 14:38:46', null);
INSERT INTO `user_info` VALUES ('11', 'test11', '123456', null, null, '869758001213134', '2017-09-12 14:39:10', null);
INSERT INTO `user_info` VALUES ('12', 'test12', '123456', null, null, '869758001213142', '2017-09-12 14:39:34', null);
INSERT INTO `user_info` VALUES ('13', 'test13', '123456', null, null, '869758001213159', '2017-09-12 14:39:56', null);
INSERT INTO `user_info` VALUES ('14', 'test14', '123456', null, null, '869758001213167', '2017-09-12 14:40:18', null);



-- ----------------------------
-- Table structure for `authcode`
-- ----------------------------
DROP TABLE IF EXISTS `authcode`;
CREATE TABLE `authcode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tel` varchar(13) NOT NULL,
  `code` varchar(10) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for `apilog`
-- ----------------------------
DROP TABLE IF EXISTS `apilog`;
CREATE TABLE `apilog` (
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
-- Table structure for `sys`
-- ----------------------------
DROP TABLE IF EXISTS `sys`;
CREATE TABLE `sys` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service_content` text NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `smslog`
-- ----------------------------
DROP TABLE IF EXISTS `smslog`;
CREATE TABLE `smslog` (
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


-- ----------------------------
-- Table structure for `conf`
-- ----------------------------
DROP TABLE IF EXISTS `conf`;
CREATE TABLE `conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(32) NOT NULL,
  `value` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

