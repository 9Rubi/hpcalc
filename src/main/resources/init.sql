DROP TABLE IF EXISTS `combination`;
CREATE TABLE `combination` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shield_level` int(11) DEFAULT NULL,
  `beta` varchar(255) DEFAULT NULL,
  `alpha` varchar(255) DEFAULT NULL,
  `gamma` varchar(255) DEFAULT NULL,
  `reality` varchar(255) DEFAULT NULL,
  `module_level` int(11) DEFAULT NULL,
  `module_beta` varchar(255) DEFAULT NULL,
  `max_hp_decrease` decimal(10,0) DEFAULT NULL,
  `max_hp_increase` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;