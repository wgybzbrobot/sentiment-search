-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-03-12 08:41:02
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
-- 表的结构 `oa_all_internet_task_query`
--

CREATE TABLE IF NOT EXISTS `oa_all_internet_task_query` (
`id` int(11) NOT NULL COMMENT '自增ID',
  `identify` char(32) NOT NULL COMMENT '任务MD5唯一ID',
  `keywords` char(250) NOT NULL COMMENT '关键词组',
  `start_time` char(20) NOT NULL COMMENT '监测开始时间',
  `end_time` char(20) NOT NULL COMMENT '监测结束时间',
  `source_ids` varchar(1000) NOT NULL COMMENT '来源ID列表',
  `local_count` int(10) unsigned NOT NULL COMMENT '本地搜索量',
  `autm_count` int(10) unsigned NOT NULL COMMENT '元搜索量',
  `all_count` int(10) unsigned NOT NULL COMMENT '总量',
  `query_time` datetime NOT NULL COMMENT '最后查询时间',
  `lasttime` datetime NOT NULL COMMENT '最新更新时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='OA全网搜索任务缓存数据表' AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_all_internet_task_query`
--
ALTER TABLE `oa_all_internet_task_query`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `identify` (`identify`), ADD KEY `query_time` (`query_time`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_all_internet_task_query`
--
ALTER TABLE `oa_all_internet_task_query`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
