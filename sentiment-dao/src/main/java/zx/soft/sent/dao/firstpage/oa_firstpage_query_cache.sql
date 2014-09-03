-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2014-09-03 07:35:33
-- 服务器版本： 5.5.37-MariaDB-log
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sentiment_records`
--

-- --------------------------------------------------------

--
-- 表的结构 `oa_firstpage_query_cache`
--

CREATE TABLE IF NOT EXISTS `oa_firstpage_query_cache` (
`id` int(10) unsigned NOT NULL COMMENT '自增ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '首页展现类型，如：1-今日概况、2-饼状图、3-舆情聚焦、4-微博当天数据趋势、5-重点关注',
  `timestr` char(20) NOT NULL COMMENT '记录时间字符串，如：2014-09-03 12:23:01',
  `result` text NOT NULL COMMENT '查询结果',
  `lasttime` int(11) NOT NULL COMMENT '记录时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='OA首页数据查询缓存表' AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_firstpage_query_cache`
--
ALTER TABLE `oa_firstpage_query_cache`
 ADD PRIMARY KEY (`id`,`lasttime`), ADD UNIQUE KEY `type_timestr` (`type`,`timestr`), ADD KEY `type` (`type`,`timestr`), ADD KEY `timestr` (`timestr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_firstpage_query_cache`
--
ALTER TABLE `oa_firstpage_query_cache`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
