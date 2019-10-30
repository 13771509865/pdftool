/*
Navicat MySQL Data Transfer

Source Server         : openapi
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : pts

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-30 18:22:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pts_apply
-- ----------------------------
DROP TABLE IF EXISTS `pts_apply`;
CREATE TABLE `pts_apply` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `MODULE` int(11) DEFAULT NULL,
  `ADDRESS` varchar(64) DEFAULT NULL,
  `FILE_NAME` varchar(64) DEFAULT NULL,
  `FILE_SIZE` bigint(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_auth
-- ----------------------------
DROP TABLE IF EXISTS `pts_auth`;
CREATE TABLE `pts_auth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `GMT_EXPIRE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
  `STATUS` int(11) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `AUTH` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户权限，例(convert001:true,)',
  `REMARK` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_auth_name
-- ----------------------------
DROP TABLE IF EXISTS `pts_auth_name`;
CREATE TABLE `pts_auth_name` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `AUTH_NAME` varchar(2000) NOT NULL,
  `AUTH_CODE` varchar(255) NOT NULL,
  `VALUE_TYPE` varchar(255) NOT NULL,
  `VALUE_UNIT` varchar(255) DEFAULT NULL,
  `DEFAULT_VALUE` varchar(255) DEFAULT NULL,
  `MIN_VALUE` varchar(255) DEFAULT NULL,
  `MAX_VALUE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `OPTIONALS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_convert
-- ----------------------------
DROP TABLE IF EXISTS `pts_convert`;
CREATE TABLE `pts_convert` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IP_ADDRESS` varchar(255) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `FILE_HASH` varchar(255) DEFAULT NULL,
  `RESULT_CODE` int(11) DEFAULT NULL,
  `DEST_FILE_NAME` varchar(255) DEFAULT NULL,
  `SRC_FILE_NAME` varchar(255) DEFAULT NULL,
  `DEST_FILE_SIZE` bigint(20) DEFAULT NULL,
  `SRC_FILE_SIZE` bigint(20) DEFAULT NULL,
  `CONVERT_TYPE` int(2) DEFAULT NULL,
  `SRC_STORAGE_PATH` varchar(255) DEFAULT NULL,
  `DEST_STORAGE_PATH` varchar(255) DEFAULT NULL,
  `DOWNLOAD_URL` varchar(1024) DEFAULT NULL,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_summ
-- ----------------------------
DROP TABLE IF EXISTS `pts_summ`;
CREATE TABLE `pts_summ` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ZERO_TWO` int(11) DEFAULT '0' COMMENT '文件大小范围（0-3M）',
  `TWO_FIVE` int(11) DEFAULT '0',
  `FIVE_TEN` int(11) DEFAULT '0',
  `TEN_FIFTEEN` int(11) DEFAULT '0',
  `FIFTEEN_TWENTY` int(11) DEFAULT '0',
  `TWENTY_THIRTY` int(11) DEFAULT '0',
  `THIRTY_FOURTY` int(11) DEFAULT '0',
  `FOURTY_FIFTY` int(11) DEFAULT '0',
  `FIFTY_MORE` int(11) DEFAULT '0',
  `IP_ADDRESS` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `IS_SUCCESS` int(2) DEFAULT NULL COMMENT '0成功1失败',
  `APP_TYPE` int(2) DEFAULT NULL COMMENT '0手机端1PC端',
  `MODULE` int(11) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `CREATE_TIME` time DEFAULT NULL,
  `MODIFIED_DATE` date DEFAULT NULL,
  `MODIFIED_TIME` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_vote
-- ----------------------------
DROP TABLE IF EXISTS `pts_vote`;
CREATE TABLE `pts_vote` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `IP_ADDRESS` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `VOTE` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '投票结果',
  `OTHER_CONTENT` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户选择其他，输入的内容',
  `REMARK` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
