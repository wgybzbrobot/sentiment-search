-- phpMyAdmin SQL Dump
-- version 4.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-07-07 07:31:09
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
-- 表的结构 `task_keywords`
--

CREATE TABLE IF NOT EXISTS `task_keywords` (
`kid` int(10) unsigned NOT NULL COMMENT '自增ID',
  `keyword_id` char(50) NOT NULL COMMENT '关键词ID',
  `first_type` tinyint(3) unsigned NOT NULL COMMENT '一级分类类型',
  `second_type` tinyint(3) unsigned NOT NULL COMMENT '二级分类类型',
  `third_type` tinyint(3) unsigned NOT NULL COMMENT '三级分类类型',
  `keyword_name` char(30) NOT NULL COMMENT '关键词名称',
  `lasttime` datetime NOT NULL COMMENT '最后插入时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='全网自动任务关键词数据表' AUTO_INCREMENT=1 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `task_keywords`
--
ALTER TABLE `task_keywords`
 ADD PRIMARY KEY (`kid`), ADD UNIQUE KEY `keyword_type` (`first_type`,`second_type`,`third_type`) COMMENT '关键词类型联合索引', ADD UNIQUE KEY `keyword_id` (`keyword_id`), ADD KEY `third_type` (`third_type`), ADD KEY `second_type` (`second_type`), ADD KEY `first_type` (`first_type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `task_keywords`
--
ALTER TABLE `task_keywords`
MODIFY `kid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
