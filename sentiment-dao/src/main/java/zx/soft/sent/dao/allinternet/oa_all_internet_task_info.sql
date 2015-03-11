-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-03-11 08:19:13
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
-- 表的结构 `oa_all_internet_task_info`
--

CREATE TABLE IF NOT EXISTS `oa_all_internet_task_info` (
`id` int(10) unsigned NOT NULL COMMENT '自增ID',
  `identify` char(32) NOT NULL COMMENT '任务MD5i唯一标识',
  `keywords` char(250) NOT NULL COMMENT '关键词',
  `start_time` char(20) NOT NULL COMMENT '任务开始时间',
  `end_time` char(20) NOT NULL COMMENT '任务结束时间',
  `source_id` varchar(500) NOT NULL COMMENT '来源ID',
  `is_over` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务是否结束',
  `lasttime` datetime NOT NULL COMMENT '任务最后的更新时间'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='OA全网搜索任务信息表' AUTO_INCREMENT=10 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_all_internet_task_info`
--
ALTER TABLE `oa_all_internet_task_info`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `identify` (`identify`), ADD KEY `is_over` (`is_over`), ADD KEY `lasttime` (`lasttime`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_all_internet_task_info`
--
ALTER TABLE `oa_all_internet_task_info`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
