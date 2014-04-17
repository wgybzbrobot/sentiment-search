CREATE DATABASE IF NOT EXISTS `pp_fenxi` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pp_fenxi`;

set FOREIGN_KEY_CHECKS = 0;

--
-- 表的结构 `bozhu`
--

DROP TABLE IF EXISTS `bozhu`;
CREATE TABLE IF NOT EXISTS `bozhu` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '全公司统一的微博账号ID',
  `username` bigint(32) unsigned NOT NULL COMMENT '微博平台上的用户名或ID',
  `ptype` enum('sina','tencent') NOT NULL COMMENT '微博平台类型',
  `default_price_source` int(10) unsigned DEFAULT NULL COMMENT '博主默认渠道的ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_ptype` (`username`,`ptype`),
  KEY `default_price_source` (`default_price_source`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='博主账号表' AUTO_INCREMENT=3005414 ;

INSERT INTO `bozhu` (`id`, `username`, `ptype`, `default_price_source`) VALUES
	(1, 1772605247, 'sina', 1),
	(2, 1699668020, 'sina', 2),
	(3, 8888888888, 'sina', null);

-- --------------------------------------------------------

--
-- 表的结构 `bozhu_detail`
--

DROP TABLE IF EXISTS `bozhu_detail`;
CREATE TABLE IF NOT EXISTS `bozhu_detail` (
  `bzid` bigint(11) unsigned NOT NULL COMMENT 'bozhu表ID',
  `influence` int(10) NOT NULL COMMENT '影响力',
  `active` tinyint(2) NOT NULL COMMENT '活跃度',
  `wbnum` int(10) NOT NULL COMMENT '微博数',
  `fannum` int(10) NOT NULL COMMENT '粉丝数',
  `malerate` float NOT NULL COMMENT '男性比例（需要用1',
  `vrate` float NOT NULL COMMENT '加V比例',
  `exsit_fan_rate` float NOT NULL COMMENT '粉丝存在比例',
  `act_fan` int(10) NOT NULL COMMENT '活跃粉丝数',
  `act_fan_rate` float NOT NULL COMMENT '活跃粉丝比例',
  `fan_fans` bigint(11) NOT NULL COMMENT '粉丝及粉丝的粉丝数',
  `act_fan_fans` bigint(11) NOT NULL COMMENT '活跃粉及活跃粉的粉丝数',
  `wb_avg_daily` float NOT NULL COMMENT '每日平均微博数',
  `wb_avg_repost_lastweek` float NOT NULL COMMENT '最近一周微博平均转评数',
  `wb_avg_repost_lastmonth` float NOT NULL COMMENT '最近一月微博平均转评数',
  `wb_avg_repost` float NOT NULL COMMENT '微博平均转评数',
  `orig_wb_rate` float NOT NULL COMMENT '原创微博比例',
  `orig_wb_avg_repost` float NOT NULL COMMENT '原创微博平均转评数',
  `wb_avg_valid_repost_lastweek` float NOT NULL COMMENT '最近一周内平均每条微博的有效转评量',
  `wb_avg_valid_repost_lastmonth` float NOT NULL COMMENT '最近一个月内平均每条微博的有效转评量',
  `rt_user_avg_quality` float NOT NULL COMMENT '平均转发用户质量',
  `avg_valid_fan_cover_last100` bigint(11) NOT NULL COMMENT '最近100条微博的平均有效粉丝覆盖量',
  `identity_type` varchar(50) NOT NULL COMMENT '身份分类，如：草根大号，音乐达人',
  `industry_type` varchar(50) NOT NULL COMMENT '行业分类，如：IT，汽车',
  `fans_age` char(250) NOT NULL COMMENT '粉丝年龄分布',
  `fans_tags` varchar(500) NOT NULL COMMENT '粉丝标签数据',
  `top5provinces` char(250) NOT NULL COMMENT '粉丝前5区域分布',
  `wbsource` char(250) NOT NULL COMMENT '微博终端来源分布',
  `usertags` char(250) NOT NULL COMMENT '用户标签数据',
  PRIMARY KEY (`bzid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博主详细信息数据库';

-- --------------------------------------------------------

--
-- 表的结构 `bozhu_price`
--

DROP TABLE IF EXISTS `bozhu_price`;
CREATE TABLE IF NOT EXISTS `bozhu_price` (
  `username` bigint(32) unsigned NOT NULL COMMENT '用户id',
  `sourceid` int(10) unsigned NOT NULL COMMENT 'bz_price_source表ID',
  `typeid` tinyint(2) unsigned NOT NULL COMMENT '价格类型，见价格类型表',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '价格',
  `update_time` datetime NOT NULL,
  UNIQUE KEY `username_sourceid_typeid_unique` (`username`,`sourceid`,`typeid`),
  KEY `sourceid` (`sourceid`),
  KEY `typeid` (`typeid`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微博账号报价、分析价表';

INSERT INTO `bozhu_price` (`username`, `sourceid`, `typeid`, `price`, `update_time`) VALUES
	(1699668020, 1, 1, 5.00, '2013-10-12 01:02:03'),
	(1699668020, 1, 2, 5.00, '2013-10-12 01:02:03'),
	(1699668020, 2, 1, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 1, 1, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 1, 2, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 1, 3, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 1, 4, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 2, 1, 5.00, '2013-10-12 01:02:03'),
	(1772605247, 2, 3, 5.00, '2013-10-12 01:02:03');

-- --------------------------------------------------------

--
-- 表的结构 `bozhu_price_source`
--

DROP TABLE IF EXISTS `bozhu_price_source`;
CREATE TABLE IF NOT EXISTS `bozhu_price_source` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '价格来源ID',
  `name` varchar(50) NOT NULL COMMENT '价格来源名称',
  `qq` varchar(50) NOT NULL COMMENT 'QQ',
  `telephone` varchar(50) NOT NULL COMMENT '电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='博主价格渠道表' AUTO_INCREMENT=18 ;

INSERT INTO `bozhu_price_source` (`id`, `name`, `qq`, `telephone`) VALUES
	(1, '博主报价', '', '18888888888'),
	(2, '分析价', '123456', ''),
	(3, '微博易价', '', ''),
	(4, '微任务价', '', '');

DROP TABLE IF EXISTS `bozhu_price_type`;
CREATE TABLE IF NOT EXISTS `bozhu_price_type` (
  `id` tinyint(2) unsigned NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '价格类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `bozhu_price_type` (`id`, `name`) VALUES
        (1, '软广转发'),
        (2, '软广直发'),
        (3, '硬广转发'),
        (4, '硬广直发'),
        (5, '带号(@)价');

--
-- 限制导出的表
--

--
-- 限制表 `bozhu`
--
ALTER TABLE `bozhu`
  ADD CONSTRAINT `bozhu_ibfk_1` FOREIGN KEY (`default_price_source`) REFERENCES `bozhu_price_source` (`id`);

--
-- 限制表 `bozhu_price`
--
ALTER TABLE `bozhu_price`
  ADD CONSTRAINT `FK_bozhu_price_bozhu` FOREIGN KEY (`username`) REFERENCES `bozhu` (`username`),
  ADD CONSTRAINT `FK_bozhu_price_bozhu_price_source` FOREIGN KEY (`sourceid`) REFERENCES `bozhu_price_source` (`id`),
  ADD CONSTRAINT `FK_bozhu_price_bozhu_price_type` FOREIGN KEY (`typeid`) REFERENCES `bozhu_price_type` (`id`);

set FOREIGN_KEY_CHECKS = 1;
