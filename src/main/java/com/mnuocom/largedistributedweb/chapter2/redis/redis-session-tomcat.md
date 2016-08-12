---
title: 大型分布式网站--2 分布式系统基础设施(5 Tomcat8 + Redis 实现session共享)
date: 2016-08-12 19:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### Tomcat8 + Redis 实现session共享

#### 1 安装Redis
(上一节已经讲过,不在累述)

#### 2 下载需要jar,添加到tomcat lib/下   
下载地址: [http://pan.baidu.com/s/1hrL9bVI]

+ tomcat8-redis-session-manager.jar   
    由于官网没有支持tomcat8,所以根据网上的方法[http://jingyan.baidu.com/album/ac6a9a5e10415f2b653eace8.html?picindex=6],自己编译了一个
+ commons-pool2-2.4.2.jar
+ jedis-2.7.2.jar
+ tomcat-juli-8.0.36.jar

[http://pan.baidu.com/s/1hrL9bVI]: http://pan.baidu.com/s/1hrL9bVI
[http://jingyan.baidu.com/album/ac6a9a5e10415f2b653eace8.html?picindex=6]: http://jingyan.baidu.com/album/ac6a9a5e10415f2b653eace8.html?picindex=6
#### 3 修改tomcat conf/context.xml
+ 1 单节点配置

        <Valve className="com.orangefunction.tomcat.redissessions.RedisSessionHandlerValve" />    
    	<Manager className="com.orangefunction.tomcat.redissessions.RedisSessionManager"    
                 host="127.0.0.1"    
                 port="6379"    
                 database="0"    
                 maxInactiveInterval="60" />  
                 
+ 2 Sentinel集群配置：

    	<!-- Sentinel 配置 -->
    	<Valve className="com.orangefunction.tomcat.redissessions.RedisSessionHandlerValve" />        
    	<Manager className="com.orangefunction.tomcat.redissessions.RedisSessionManager"
    	    maxInactiveInterval="60"
    	    sentinelMaster="mymaster"
    	    sentinels="127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381,127.0.0.1:26382" /> 
 
#### 4 测试,启动两个tomcat,redis

![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributejava-2.4-redis-connection-log.jpg)

