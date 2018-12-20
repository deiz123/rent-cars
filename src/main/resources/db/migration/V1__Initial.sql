SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `client_id` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `car_fk_1` (`client_id`),
  CONSTRAINT `car_fk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1', 'mazda 3', '2011', null);
INSERT INTO `car` VALUES ('2', 'toyota corolla', '2012', null);
INSERT INTO `car` VALUES ('3', 'kia rio', '2014', null);
INSERT INTO `car` VALUES ('4', 'hyundai solaris', '2014', null);
INSERT INTO `car` VALUES ('5', 'bmw 5', '2013', null);

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
