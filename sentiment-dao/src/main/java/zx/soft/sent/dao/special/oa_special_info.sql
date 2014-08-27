-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2014-08-27 03:07:19
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
-- 表的结构 `oa_special_info`
--

CREATE TABLE IF NOT EXISTS `oa_special_info` (
`id` int(10) unsigned NOT NULL COMMENT '自增',
  `identify` char(50) NOT NULL COMMENT '唯一标识',
  `keywords` char(100) NOT NULL COMMENT '关键词',
  `start` char(20) NOT NULL COMMENT '起始查询时间',
  `end` char(20) NOT NULL COMMENT '结束查询时间',
  `hometype` tinyint(3) unsigned NOT NULL COMMENT '境内外参数',
  `lasttime` int(10) unsigned NOT NULL COMMENT '记录时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='OA专题信息表' AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_special_info`
--
ALTER TABLE `oa_special_info`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `identify` (`identify`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_special_info`
--
ALTER TABLE `oa_special_info`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
