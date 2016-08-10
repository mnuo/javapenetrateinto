---
title: 大型分布式网站--3 互联网安全架构(3认证和协议)
date: 2016-08-10 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 3 互联网安全架构--认证和协议

##### 1 摘要认证
这是一种常用的技术，用于证明某人知道某个秘密，而不要求他以容易被窃听的明文形式发送该秘密。

原理:
> 摘要认证与基础认证的工作原理很相似，用户先发出一个没有认证证书的请求，Web服务器回复一个带有WWW-Authenticate头的响应，指明访问所请求的资源需要证书。但是和基础认证发送以Base 64编码的用户名和密码不同，在摘要认证中服务器让客户选一个随机数（称作”nonce“），然后浏览器使用一个单向的加密函数生成一个消息摘要（message digest），该摘要是关于用户名、密码、给定的nonce值、HTTP方法，以及所请求的URL。消息摘要函数也被称为散列算法，是一种在一个方向上很容易计算，反方向却不可行的加密算法。与基础认证对比，解码基础认证中的Base 64是很容易办到的。在服务器口令中，可以指定任意的散列算法。在RFC 2617中，描述了以MD5散列函数作为默认算法。

+ 1 客户端参数摘要生成
> 请求参数需要先排好序,服务端与客户端需要事先约定好排序的方式,否则生成的摘要可能就相差了,排好序后,将参数名称与参数值串起来,加上约定好的secret,生成带摘要的字符串,最后使用如MD5之类的摘要算法生成摘要串,当然摘要算法也需要事先约定好   
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributejava-3.3-digest1.jpg)

	Map<String, String> params = new HashMap<>();
	params.put("username", "zhangsan");
	params.put("password", "123123");
	String digest = getDigest(params);//生成的摘要
	System.out.println("客户端生成的摘要: " + digest);
		
+ 2  服务端参数摘要校验
> 服务端接收到请求的参数后,按照与客户端相同的方式将参数排序,然后将参数的名称与参数的值串起来,生成待摘要字符串,并且使用与客户端相同的摘要算法生成摘要串,最后将服务端生成的摘要串与客户端通过header或者其他形式传递过来的摘要串进行比较,如果一致,表示参数没有遭到篡改,反之,则说明参数遭到篡改.

	boolean serverResult = validate(params, digest);
	System.out.println("服务端参数摘要校验结果: " + serverResult);
		
+ 3  服务器端响应摘要生成
> 服务端生成响应内容以后,在响应内容的后面加上secret,便是待摘要串,然后MD5等摘要算法生成响应的摘要串

	String content = "登录成功";
	String serverdigest = getDigest(content);
	System.out.println(" 服务器端响应摘要: " + serverdigest);
		
+ 4 客户端响应摘要检验   

	boolean clientResult = validate(content, serverdigest);
	System.out.println("客户端响应摘要检验结果: "+ clientResult);


