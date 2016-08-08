---
title: 大型分布式网站--2 分布式系统基础设施
date: 2016-08-05 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 分布式系统基础设施
> 一个大型,稳健,成熟的分布式胸膛的背后,往往会设计众多的支撑系统,我们将这些支撑系统称为分布式系统的基础设施,除了前面的分布式协作及配置管理系统ZooKeeper,我们进行系统的架构设计所依赖的基础设施,还包括,分布式缓存,持久化存储,分布式消息系统,搜索引擎,以及CMN系统,负载均衡系统,运维自动化系统等,还有后面章节的实时计算系统,离线计算系统,分布式文件系统,日志收集系统,监控系统,数据仓库等.

#### 1 分布式缓存   
> 在高并发环境下,大量的读写请求涌向数据库,磁盘的处理速度与内存显然不在一个量级,从减轻数据库的压力和提高系统响应速度两个角度来考虑,一般都会在数据库之前加一层缓存,由于单台机器的内存资源和承载能力有限,并且如果大量使用本地缓存,也会使相同的数据被不同的节点存储多分,对内存资源造成较大的浪费,因此才催生出了分布式缓存.

##### 1.1 memcache简介及安装   
+ 1安装libevennt:
	 wget https://github.com/libevent/libevent/archive/release-1.4.15-stable.tar.gz




##### 1.2 memcache API与分布式

##### 1.3 分布式session

![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.2-memcache-session-manager.jpg)
如图:前端用户请求经过随机分发之后,可能会命中后端任意的Web Server,并且Web Server也可能会因为各种不确定的原因宕机,在这种
情况下,session是很难再集群间同步的,而通过将session以sessionId作为key,保存到后端的缓存集群中,使得不管请求如何分配,即便是WebServer宕机,也不会影响其他WebServer通过sessionId从Cache Server中获得session,这样实现了集群间的session同步,又提高了Web Server的容错性


#### 2 持久化存储

##### 2.1 MySQL扩展

##### 2.2 HBase

##### 2.3 Redis



#### 3 消息系统


##### 3.1 ActiveMQ & JMS


#### 4 垂直化搜索引擎

##### 4.1 lucene简介

##### 4.2 Lucene的使用

##### 4.3 Solr

#### 5 其他基础设施



