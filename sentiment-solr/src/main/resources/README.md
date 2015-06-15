
### Redis作用说明

#### 主从配置集群

> 两台机器，其中，

`master`:hefei09,用户读数据

`slave`: hefei10,用于读数据

> 主要用途：

`用于站点信息存储K-V`: sent.site.groups

`用于站点Id和站点名存储K-V`: sent:site:map

`索引缓存数据消息队列List`: sent.cache.records

#### 缓存节点

> 一台机器，hefei08，

`用于缓存首次抓取时间firsttime`：时效性为7天

