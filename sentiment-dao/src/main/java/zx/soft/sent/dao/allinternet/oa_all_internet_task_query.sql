-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-03-11 08:19:19
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
  `query_result` mediumtext NOT NULL COMMENT '查询结果',
  `lasttime` datetime NOT NULL COMMENT '记录时间'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='OA全网搜索任务缓存数据表' AUTO_INCREMENT=4 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oa_all_internet_task_query`
--
ALTER TABLE `oa_all_internet_task_query`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `identify` (`identify`), ADD KEY `lasttime` (`lasttime`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oa_all_internet_task_query`
--
ALTER TABLE `oa_all_internet_task_query`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
