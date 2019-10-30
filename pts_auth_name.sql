/*
Navicat MySQL Data Transfer

Source Server         : openapi
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : pts

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-30 18:22:22
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of pts_auth_name
-- ----------------------------
INSERT INTO `pts_auth_name` VALUES ('1', '2019-10-28 15:26:19', '2019-10-28 15:26:27', '1', 'pdf2word', 'convert001', 'Boolean', null, null, null, null, 'pdf转word功能', null);
INSERT INTO `pts_auth_name` VALUES ('2', '2019-10-28 15:39:29', '2019-10-28 15:39:29', '1', 'word2pdf', 'convert002', 'Boolean', null, null, null, null, 'word转pdf功能', null);
INSERT INTO `pts_auth_name` VALUES ('3', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'pdf2ppt', 'convert003', 'Boolean', null, null, null, null, 'pdf转ppt功能', null);
INSERT INTO `pts_auth_name` VALUES ('4', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'ppt2pdf', 'convert004', 'Boolean', null, null, null, null, 'ppt转pdf功能', null);
INSERT INTO `pts_auth_name` VALUES ('5', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'pdf2excel', 'convert005', 'Boolean', null, null, null, null, 'pdf转excel功能', null);
INSERT INTO `pts_auth_name` VALUES ('6', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'excel2pdf', 'convert006', 'Boolean', null, null, null, null, 'excel转pdf功能', null);
INSERT INTO `pts_auth_name` VALUES ('7', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'pdf2img', 'convert007', 'Boolean', null, null, null, null, 'pdf转图片', null);
INSERT INTO `pts_auth_name` VALUES ('8', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'pdf2html', 'convert008', 'Boolean', null, null, null, null, 'pdf在线预览', null);
INSERT INTO `pts_auth_name` VALUES ('9', '2019-10-28 16:08:09', '2019-10-28 16:08:09', '1', 'pdf2watermark', 'convert009', 'Boolean', null, null, null, null, 'pdf加水印', null);
INSERT INTO `pts_auth_name` VALUES ('10', '2019-10-28 16:08:10', '2019-10-28 16:08:10', '1', 'pdf2sign', 'convert010', 'Boolean', null, null, null, null, 'pdf签批', null);
INSERT INTO `pts_auth_name` VALUES ('11', '2019-10-28 16:12:57', '2019-10-28 16:12:57', '1', 'pdf2merge', 'convert011', 'Boolean', null, null, null, null, 'pdf合并', null);
INSERT INTO `pts_auth_name` VALUES ('12', '2019-10-28 16:12:57', '2019-10-28 16:12:57', '1', 'pdf2split', 'convert012', 'Boolean', null, null, null, null, 'pdf拆分', null);
INSERT INTO `pts_auth_name` VALUES ('13', '2019-10-28 16:12:57', '2019-10-28 16:12:57', '1', '每日转换次数', 'convertNum', 'Number', null, null, null, null, '每日允许转换次数', null);
INSERT INTO `pts_auth_name` VALUES ('14', '2019-10-28 16:12:57', '2019-10-28 16:12:57', '1', '上传文件大小', 'uploadSize', 'Number', 'MB', null, null, null, '允许上传文件大小', null);
INSERT INTO `pts_auth_name` VALUES ('15', '2019-10-30 16:22:05', '2019-10-30 16:22:08', '1', '权益有效期', 'validityTime', 'Number', 'Month', null, null, null, 'pdf工具集有效期', null);
