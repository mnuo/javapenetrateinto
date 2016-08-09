---
title: 大型分布式网站--3 互联网安全架构(常用的安全算法)
date: 2016-08-05 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 3 互联网安全架构--常用的安全算法

#### 1 数字摘要
数字摘要也称为消息摘要,它是一个唯一对应一个消息或文本的固定长度的值,它由一个单向Hash函数对消息进行计算而产生.

+ 消息摘要可以验证消息的完整性
+ 消息摘要采用单项Hash函数,将需要计算的内容"摘要"成固定长度的串,这个串也称为数字指纹

关于消息摘要的特点:

+ 1 无论输入的消息有多长,计算出来的消息摘要的长度总是固定的.MD5 128个比特位,SHA-1 160个比特位
+ 2 一般只要输入的消息不同,对其进行摘要以后产生的摘要消息也不相同,但相同的输入必会产生相同的输出
+ 3 由于消息摘要并不包含原文的完整信息,因此只能进行正向的信息摘要,而无法从摘要中恢复出原来的消息,甚至根本就招不到任何与原信息相关的信息.

##### 1.1 MD5
MD5 即Message Digest Algorithm 5,是数字摘要算法的一种实现,用于确保信息传输完整性和一致性,摘要长度为128位 [md5demo] 

[md5demo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/numbersummer/MD5Demo.java

		String content = "123123";
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] result = md.digest(content.getBytes());
		System.out.println(MD5Util.byteToString(result));//16进制编码后

##### 1.2 SHA-1
SHA的全称是Secure Hash Algorithm,即安全散列算法.SHA-1算法生成的摘要信息的长度为160位,由于生成的摘要信息更长,运算的过程更加复杂,在相同的硬件上SHA-1的运行速度比MD5更慢,但是也更为安全.[shademo] 

[shademo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/numbersummer/SHA1Demo.java

##### 1.3 十六进制编码
[hexdemo]

[hexdemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/numbersummer/HexDemo.java

##### 1.4 base64
很多人认为Base64是一种加密算法,并且将其当做加密算法来使用,而实际情况却并非这样,因为任何人只要得到Base64编码的内容,便可通过固定的方法,逆向得出编码之前的信息,Base64算法仅仅只是一种编码算法而已,他可以将一组二进制信息编码成可打印的字符,在网络上传输与展现
jdk 1.8:

		//加密
		Encoder encode = Base64.getEncoder();
		byte[] bytes = encode.encode("hello".getBytes());
		System.out.println(encode.encodeToString("hello".getBytes()));
		System.out.println(new String(bytes));
		//解密
		Decoder decode = Base64.getDecoder();
		byte[] bb = decode.decode(encode.encodeToString("hello".getBytes()));
		System.out.println(new String(decode.decode(bytes)));
		System.out.println(new String(bb));
