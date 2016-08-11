---
title: 大型分布式网站--2 分布式系统基础设施(3 memcached-session)
date: 2016-08-11 18:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### Nginx + tomcat + memcached 实现session共享

#### 1 安装memcached
参考上一章节[http://mnuo.github.io/2016/08/11/%E5%A4%A7%E5%9E%8B%E5%88%86%E5%B8%83%E5%BC%8F%E7%BD%91%E7%AB%99--2%20%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F%E5%9F%BA%E7%A1%80%E8%AE%BE%E6%96%BD(2memcached)/]

[http://mnuo.github.io/2016/08/11/%E5%A4%A7%E5%9E%8B%E5%88%86%E5%B8%83%E5%BC%8F%E7%BD%91%E7%AB%99--2%20%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F%E5%9F%BA%E7%A1%80%E8%AE%BE%E6%96%BD(2memcached)/]: http://mnuo.github.io/2016/08/11/%E5%A4%A7%E5%9E%8B%E5%88%86%E5%B8%83%E5%BC%8F%E7%BD%91%E7%AB%99--2%20%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F%E5%9F%BA%E7%A1%80%E8%AE%BE%E6%96%BD(2memcached)/

#### 2 配置tomcat

+ 1 在每个tomcat的conf/content.xml中添加配置:

        <Manager className="de.javakaffee.web.msm.MemcachedBackupSessionManager"
        	memcachedNodes="n1:127.0.0.1:11211"
    	    sticky="false"  
    		sessionBackupAsync="false"  
    		lockingMode="auto"  
    		requestUriIgnorePattern=".*\.(ico|png|gif|jpg|css|js)$"  
    		transcoderFactoryClass="de.javakaffee.web.msm.JavaSerializationTranscoderFactory"/> 

    如果是多个memcached配置如下:
	 
         <Manager className="de.javakaffee.web.msm.MemcachedBackupSessionManager"  
            memcachedNodes="n1:192.168.0.101:11211,n2:192.168.0.102:11211"  
            sticky="false"  
            sessionBackupAsync="false"  
            lockingMode="auto"  
            requestUriIgnorePattern=".*\.(ico|png|gif|jpg|css|js)$"  
            transcoderFactoryClass="de.javakaffee.web.msm.JavaSerializationTranscoderFactory"  
            />  

+ 2 在tomcat 的lib/下添加jar [下载链接] 
    - 1 asm-3.2.jar
    - 2 kryo-1.04.jar
    - 3 kryo-serializers-0.11.jar
    - 4 memcached-session-manager-1.8.1.jar
    - 5 memcached-session-manager-tc8-1.8.1.jar
    - 6 minlog-1.2.jar
    - 7 msm-kryo-serializer-1.8.1.jar
    - 8 reflectasm-1.01.jar
    - 9 spymemcached-2.11.1.jar

[下载链接]: http://pan.baidu.com/s/1bo4On5H

+ 3 下载和配置nginx   
见文章[http://mnuo.github.io/2016/07/19/JavaWeb--1.nginx-1.9.9%20+%20tomcat-8.0.32%20%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1/]
		upstream mnuo {
	        #配置两台tomcat负载均衡,ip:port weight是权重
	        server 120.76.131.4:8080 weight=10;  
	        server 120.76.131.4:8081 weight=10; 
	    }
	    server {
	        listen       80;
	        server_name  localhost;
	
	        #charset koi8-r;
	
	        #access_log  logs/host.access.log  main;
	
	        location / {
	            #root   html;  
	            #index  index.jsp index.htm;  
	            #mnuo是 upstream 后面的名字 mnuo  
	            proxy_pass http://mnuo;  
	            #localhost是nginx服务器的主机地址，如果不写此句，会导致静态文件访问路径为http://mnuo，导致找不到地址  
	            proxy_set_header Host localhost;    
	            #forwarded信息，用于告诉后端服务器终端用户的ip地址，否则后端服务器只能获取前端代理服务器的ip地址。  
	            proxy_set_header Forwarded $remote_addr; 
	        }
		...

[http://mnuo.github.io/2016/07/19/JavaWeb--1.nginx-1.9.9%20+%20tomcat-8.0.32%20%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1/]: http://mnuo.github.io/2016/07/19/JavaWeb--1.nginx-1.9.9%20+%20tomcat-8.0.32%20%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1/
