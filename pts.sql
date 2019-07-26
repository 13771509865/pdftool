/*
Navicat MySQL Data Transfer

Source Server         : dcsUserCenter
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : pts

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-26 16:50:16
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pts_summ
-- ----------------------------
DROP TABLE IF EXISTS `pts_summ`;
CREATE TABLE `pts_summ` (
  `ID` int(11) NOT NULL,
  `ZERO_THREE` int(11) DEFAULT NULL,
  `THREE_FIVE` int(11) DEFAULT NULL,
  `FIVE_TEN` int(11) DEFAULT NULL,
  `TEN_FIFTEEN` int(11) DEFAULT NULL,
  `FIFTEEN_TWENTY` int(11) DEFAULT NULL,
  `TWENTY_THIRTY` int(11) DEFAULT NULL,
  `THIRTY_FOURTY` int(11) DEFAULT NULL,
  `FOURTY_FIFTY` int(11) DEFAULT NULL,
  `FIFTY_MORE` int(11) DEFAULT NULL,
  `IP_ADDRESS` varchar(64) DEFAULT NULL,
  `IS_SUCCESS` int(2) DEFAULT NULL,
  `APP_TYPE` int(2) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `CREATE_TIME` time DEFAULT NULL,
  `MODIFIED_DATE` date DEFAULT NULL,
  `MODIFIED_TIME` time DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
