-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2014 年 09 月 09 日 10:01
-- 服务器版本: 5.1.73
-- PHP 版本: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `sentiment_records`
--

-- --------------------------------------------------------

--
-- 表的结构 `oa_firstpage_query_cache`
--

CREATE TABLE IF NOT EXISTS `oa_firstpage_query_cache` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '首页展现类型，如：1-今日概况、2-饼状图、3-舆情聚焦、4-微博当天数据趋势、5-重点关注',
  `timestr` char(20) CHARACTER SET utf8 NOT NULL COMMENT '记录时间字符串，如：2014-09-03 12:23:01',
  `result` mediumtext CHARACTER SET utf8 NOT NULL COMMENT '查询结果',
  `lasttime` int(11) NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`,`lasttime`),
  UNIQUE KEY `type_timestr` (`type`,`timestr`),
  KEY `type` (`type`,`timestr`),
  KEY `timestr` (`timestr`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COMMENT='OA首页数据查询缓存表' AUTO_INCREMENT=1 ;

--
-- 导出表中的数据 `oa_firstpage_query_cache`
--

