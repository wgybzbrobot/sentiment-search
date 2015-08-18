
### Redis作用说明

#### 主从配置集群

> 两台机器，其中，redis.rp.master和redis.rp.slave

`master`:hefei09,用于读数据

`slave`: hefei10,用于读数据

> 主要用途：

`用于站点信息存储K-V`: sent.site.groups

`用于站点Id和站点名存储K-V`: sent:site:map

#### 消息队列节点

> 一台机器，hefei10，redis.mq.server

`索引缓存数据消息队列List`: sent.cache.records

`用于数据库写入去重`: sent:key:inserted

#### 有效期缓存节点

> 一台机器，hefei08，redis.ft.server

`用于缓存首次抓取时间firsttime`：时效性为7天

