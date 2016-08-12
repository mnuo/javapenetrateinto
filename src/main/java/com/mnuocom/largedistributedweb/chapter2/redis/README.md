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

#### 2 Jedis Operate Redis
Jedis 是Redis java client,最常用的一个工具:
	
	public static void main(String[] args) {
		Jedis redis = new Jedis("127.0.0.1", 6379);
		
		redis.set("name", "zhangsan");//设置key-value
		redis.setex("content", 5, "hello");//设置key-value有效期为5秒
		redis.mset("class", "a", "age", "25");//一次设置多个key-value
		redis.append("content", " lucy");//给字符串追加内容
		String content = redis.get("content");//根据key获取value
		List<String> list = redis.mget("class", "age", "name");//一次取多个key
		
		System.out.println("content: " + content);
		for(String str : list){
			System.out.println(str);
		}
		redis.hset("url", "google", "www.google.com");//改hash添加一个key-value
		redis.hset("url", "taobao", "www.taobao.com");//改hash添加一个key-value
		redis.hset("url", "sina", "www.sina.com");//改hash添加一个key-value
		redis.hset("url", "baidu", "www.baidu.com");//改hash添加一个key-value
		Map<String, String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("sex", "man");
		map.put("age", "100");
		redis.hmset("userInfo", map);//批量设置值
		
		//取Hash的多个key的值
		List<String> uslList = redis.hmget("url", "google", "taobao");
		
		//取Hash的所有key的值
		Map<String, String> userInfo = redis.hgetAll("userInfo");
		
		for (int i = 0; i < uslList.size(); i++) {
			System.out.println("usrl" + i + ": " + uslList.get(i));
		}
		
		for(String string : userInfo.keySet()){
			System.out.println(string + ": " + userInfo.get(string));
		}
		//lists
		redis.lpush("charlist", "abc");//在list首部添加元素
		redis.lpush("charlist", "def");//在list首部添加元素
		redis.rpush("charlist", "hij");//在list尾部添加元素
		redis.rpush("charlist", "klm");//在list尾部添加元素
		List<String> charlist = redis.lrange("charlist", 0, 2);
		for(String str : charlist)
			System.out.println(str);
		redis.lpop("charlist");//在list首部删除元素
		redis.rpop("charlist");//在list尾部删除元素
		Long charrlistSize = redis.llen("charlist");//获得list的大小
		System.out.println(charrlistSize);
		for(String str : charlist)
			System.out.println(str);
		
		//sets
		
		redis.sadd("setmem", "s1");//给set添加元素
		redis.sadd("setmem", "s2");//给set添加元素
		redis.sadd("setmem", "s3");//给set添加元素
		redis.sadd("setmem", "s4");//给set添加元素
		redis.sadd("setmem", "s5");//给set添加元素
		redis.srem("setmem", "s5");//给set移除元素
		Set<String> set = redis.smembers("setmem");
		for(String str: set)
			System.out.println(str);
		
		redis.zadd("sortsetmem", 1, "5th");//插入sort set, 并制定元素的序号
		redis.zadd("sortsetmem", 2, "4th");//插入sort set, 并制定元素的序号
		redis.zadd("sortsetmem", 3, "3th");//插入sort set, 并制定元素的序号
		redis.zadd("sortsetmem", 4, "2th");//插入sort set, 并制定元素的序号
		redis.zadd("sortsetmem", 5, "1th");//插入sort set, 并制定元素的序号
		
		Set<String> sortset = redis.zrange("sortsetmem", 0, 4);
		for(String str: sortset)
			System.out.println(str);
		
		//根据范围取反
		Set<String> revsortset = redis.zrevrange("sortsetmem", 0, 4);
		for(String str: revsortset)
			System.out.println(str);
		
		redis.close();
	}
	




