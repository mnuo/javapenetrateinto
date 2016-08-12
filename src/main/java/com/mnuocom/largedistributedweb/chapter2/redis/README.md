### Redis

> Redis 是一个高性能的key-value数据库,与其他很多key-value数据库的不同之处在于,Redis不仅支持简单的键值对类型的存储,还支持其他一系列丰富的数据存储结构,包括strings,hashs,lists,sets,sorted sets等,并在这些数据库结构类型上定义了一套强大的API.通过定义不同的存储结构,Redis可以轻易的完成很多其他key-value数据库难以完成的任务,如排序,去重.

#### 1 Install 

+ 1 下载安装

	$ wget http://download.redis.io/releases/redis-3.2.3.tar.gz
	$ tar xzf redis-3.2.3.tar.gz
	$ cd redis-3.2.3
	$ make

+ 2 启动

	$  src/redis-server
	7779:C 12 Aug 09:44:01.102 # Warning: no config file specified, using the default config. In order to specify a config file use src/redis-server /path/to/redis.conf
	7779:M 12 Aug 09:44:01.313 # Creating Server TCP listening socket *:6379: unable to bind socket
提示没有指定配置文件,改成:
	
	 src/redis-server /usr/download/redis-3.2.3/redis.conf
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributejava-2.4-redis-start.jpg)

+ 3 测试是否成功

	$ redis-3.2.3/src/redis-cli
	127.0.0.1:6379> set foo bar
	OK
	127.0.0.1:6379> get foo
	"bar"
	127.0.0.1:6379>

####



