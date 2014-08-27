-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2014-08-27 03:07:24
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
-- 表的结构 `oa_special_query_cache`
--

CREATE TABLE IF NOT EXISTS `oa_special_query_cache` (
`id` int(10) unsigned NOT NULL COMMENT '自增ID',
  `identify` char(50) NOT NULL COMMENT '专题的唯一标识',
  `result` mediumtext NOT NULL COMMENT '根据专题参数查询的结果',
  `lasttime` int(10) unsigned NOT NULL COMMENT '查询时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='OA系统中专题模块查询缓存表' AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_special_query_cache`
--
ALTER TABLE `oa_special_query_cache`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `identify` (`identify`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_special_query_cache`
--
ALTER TABLE `oa_special_query_cache`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
