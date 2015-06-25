
# 舆情监控服务项目

> 基于Java实现。

## 项目内容

- [项目简介](项目简介)
- [项目架构](项目架构)
- [开发人员](开发人员)

----

## 项目简介

### 项目起因

本项目主要服务于舆情监控底层架构。

### 项目框架

`sentiment-parent`: jar和插件依赖工程

`sentiment-algorithm`: 基础数据结构和数据挖掘算法

`sentiment-utils`: 工具类

`sentiment-dao`: 数据接口层

`sentiment-cache`: 数据缓存层

`sentiment-hdfs`: HDFS服务层

`sentiment-solr`: solr索引搜索工程

`sentiment-control`: 多服务控制层

`sentiment-web`: API接口服务层


> **备注:** 框架持续更新中。

### API文档
[项目wiki](http://192.168.3.23/wiki)

> **备注:** API文档统一放在公司的wiki上。

----

## 项目架构

1. MySQL： 用于存储基本爬虫数据。
2. Redis： 用于数据去重，基本思想是存储每条数据的md5(key)，根据该值进行数据插入更新判断。
3. SolrCloud： 用于提供分布式搜索服务。

### 常见约束词
Item      | Value
--------- | -----
Dao  | Interface接口层
Domain    | 数据
Constant  | 常量
Util      |  工具

### 示例代码

```java
    ** Redis层调用 **      
        // 对象申明      
	RedisCache redisCache = new RedisCache("hdp321", 6379, "zxsoft");
	String key = "record_key_md5";
	String[] members = { "v1", "v2", "v3", "v4", "v5", "v3" };
	redisCache.sadd(key, members);
	System.out.println(redisCache.scard(key));
	System.out.println(redisCache.sismember(key, "v3"));
	System.out.println(redisCache.sismember(key, "v6"));
	redisCache.sadd(key, "v5", "v7");
	System.out.println(redisCache.scard(key));
	System.out.println(redisCache.smembers(key));
	
    ** 启动脚本 ** 

```

### 注意事项

> 将與情数据写入索引时，同样的查询条件，在不同时刻查询不一样（短期内），是由于爬虫在段时间内（1～3天）写入了相同的数据，导致先写入的数据被覆盖，随后lasttime更新，因而根据时间段查询会变化的。

## 开发人员

WeChat: wgybzb

QQ: 1010437118

E-mail: wgybzb@sina.cn

