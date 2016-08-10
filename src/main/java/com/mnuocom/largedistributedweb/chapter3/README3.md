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
> 摘要认证与基础认证的工作原理很相似，用户先发出一个没有认证证书的请求，Web服务器回复一个带有WWW-Authenticate头的响应，指明访问所请求的资源需要证书。但是和基础认证发送以Base 64编码的用户名和密码不同，在摘要认证中服务器让客户选一个随机数（称作”nonce“），然后浏览器使用一个单向的加密函数生成一个消息摘要（message digest），该摘要是关于用户名、密码、给定的nonce值、HTTP方法，以及所请求的URL。消息摘要函数也被称为散列算法，是一种在一个方向上很容易计算，反方向却不可行的加密算法。与基础认证对比，解码基础认证中的Base 64是很容易办到的。在服务器口令中，可以指定任意的散列算法。在RFC 2617中，描述了以MD5散列函数作为默认算法。[digestauthenticationdemo] 

[digestauthenticationdemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/digestauthentication/DigestAuthenticationdemoDemo.java

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

#### 2 签名认证
签名认证的优势在于加密时使用的是私钥,而解密时使用的对外公开的公钥,私钥又私钥持有者保管,不需要泄露和传输给第三方,安全性大大提高,但相较于摘要认证,签名认证所使用的非对称加密算法将消耗更多的时间和硬件资源.[signauthenticationdemo] 

[signauthenticationdemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/signauthentication/SignAuthenticationdemoDemo.java

	public static KeyPair keyPair = null;
	public static PublicKey pubKey = null;
	public static PrivateKey priKey = null;
	static {
		try {
			keyPair = RSAUtil.getKeyPair();//共同的keyPair
			String pubStr = RSAUtil.getPublicKey(keyPair);//生成公钥
			String priStr = RSAUtil.getPrivateKey(keyPair);
			

			pubKey = RSAUtil.string2PublicKey(pubStr);
			priKey = RSAUtil.string2PrivateKey(priStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//1 客户端参数签名生成
		Map<String, String> params = new HashMap<>();
		params.put("username", "zhangsan");
		params.put("password", "123123");
		String sign = getSign(params);//生成的摘要
		System.out.println("客户端生成的签名: " + sign);
		
		//2  服务端参数签名校验
		boolean serverResult = validate(params, sign);
		System.out.println("服务端参数签名校验结果: " + serverResult);
		
		//3  服务器端响应签名生成
		String content = "登录成功";
		String serverdigest = getSign(content);
		System.out.println(" 服务器端响应签名: " + serverdigest);
		
		//4 客户端响应签名检验
		boolean clientResult = validate(content, serverdigest);
		System.out.println("客户端响应签名检验结果: "+ clientResult);

	}


#### 3 HTTPS

##### 3.1 HTTPS协议原理
> HTTPS全称是Hypertext Transfer Protocol over Secure Socket Layer 即基于SSL的HTTP协议,简单的说就是HTTP的安全版.HTTPS协议在HTTP协议与T
CP协议之间增加了一层安全层,所有请求和响应数据在经过网络传输之前,都会先进行加密,然后再进行传输.SSL及其继任者TLS是为网络通信提供安全与数据完整性保障的一种安全协议,利用加密技术,以维护互联网数据传输的安全,验证通信双方的身份,防止数据在网络传输的过程中被拦截和窃听.
> HTTPS既支持单项认证,也支持双向认证,所谓的单向认证即只校验服务端证书的有效性,而双向认证则表示既校验服务端证书的有效性,同时也需要校验客户端证书的有效性.

##### 3.2 SSL
SSL全称Secure Socket Layer,即安全套接层.它是一种网络安全协议,是网景在其推出的Web浏览器中同时提出的,目的是为了保障网络通信的安全,校验通信双方的身份,加密传输的数据,如图:

![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributejava-3.5-ssl.jpg)

![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributejava-3.5-ssl2.jpg)


参考网址[http://www.iteye.com/topic/1125183] [ssldemo]

[http://www.iteye.com/topic/1125183]: http://www.iteye.com/topic/1125183
[ssldemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/ssl/

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -genkey -v -alias bluedash-ssl-demo-server -keystore ./server_ks -dname "CN=localhost,OU=cn,O=cn,L=cn,ST=cn,C=cn"
		正在为以下对象生成 1,024 位DSA密钥对和自签名证书 (SHA1withDSA) (有效期为 90 天):
		         CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn
		[正在存储./server_ks]

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -genkey -v -alias bluedash-ssl-demo-client -keyalg RSA -keystore ./client_ks -dname "CN=localhost,OU=cn,O=cn,L=cn,
		正在为以下对象生成 2,048 位RSA密钥对和自签名证书 (SHA256withRSA) (有效期为 90 天):
		         CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn
		[正在存储./client_ks]


		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -export -alias bluedash-ssl-demo-server -keystore ./server_ks -file server_key.cer
		输入密钥库口令: server
		存储在文件 <server_key.cer> 中的证书

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -import -trustcacerts -alias bluedash-ssl-demo-server -file ./server_key.cer -keystore ./client_ks
		输入密钥库口令: client
		所有者: CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn
		发布者: CN=localhost, OU=cn, O=cn, L=cn, ST=cn, C=cn
		序列号: 474422ed
		有效期开始日期: Wed Aug 10 14:21:17 CST 2016, 截止日期: Tue Nov 08 14:21:17 CST 2016
		证书指纹:
		         MD5: 4B:82:C3:EF:38:74:42:77:C7:74:CF:25:1C:3D:2E:A5
		         SHA1: 03:41:51:E8:65:F3:D7:33:D6:93:34:F5:FE:47:B6:7D:98:8F:97:2B
		         SHA256: 1C:E0:9E:5C:F4:CD:1D:63:48:D0:D0:F5:F1:5F:A8:7A:88:27:B6:41:A6:24:FB:64:E3:78:E4:25:EE:61:56:D2
		         签名算法名称: SHA1withDSA
		         版本: 3

		扩展:

		#1: ObjectId: 2.5.29.14 Criticality=false
		SubjectKeyIdentifier [
		KeyIdentifier [
		0000: 9A 4C 9C 0C 73 80 48 1E   B7 AD A4 B2 83 76 3C 49  .L..s.H......v<I
		0010: 96 CF 2F DC                                        ../.
		]
		]

		是否信任此证书? [否]:  y
		证书已添加到密钥库中

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -export -alias bluedash-ssl-demo-client -keystore ./client_ks -file client_key.cer
		输入密钥库口令: client
		存储在文件 <client_key.cer> 中的证书


##### 3.3 HTTPS WEB

+ 第一步:

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -genkey -alias sawade -keyalg RSA -keystore d:/keys/apachejava
		输入密钥库口令: 123123
		您的名字与姓氏是什么?
		  [Unknown]:  wade
		您的组织单位名称是什么?
		  [Unknown]:  saxon
		您的组织名称是什么?
		  [Unknown]:  saxon
		您所在的城市或区域名称是什么?
		  [Unknown]:  suzhou
		您所在的省/市/自治区名称是什么?
		  [Unknown]:  jiangsu
		该单位的双字母国家/地区代码是什么?
		  [Unknown]:  cn
		CN=wade, OU=saxon, O=saxon, L=suzhou, ST=jiangsu, C=cn是否正确?
		  [否]:  y

		输入 <sawade> 的密钥口令
		        (如果和密钥库口令相同, 按回车):

+ 第二步: 

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -export -file d:/keys/sawade.crt -alias sawade -keystore d:/keys/apachejava
		输入密钥库口令: 123123
		存储在文件 <d:/keys/sawade.crt> 中的证书

+ 第三步:
命令中不能有路径不能有空格 Program Files 换成: Progra~1

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -import -keystore C:\Progra~1\Java\jdk1.8.0_71\jre\lib\security\cacerts -file D:/keys/sawade.crt -alias sawade
		输入密钥库口令:123123
		keytool 错误: java.io.IOException: Keystore was tampered with, or password was incorrect

		C:\Program Files\Java\jdk1.8.0_71\bin>keytool -import -keystore C:\Progra~1\Java\jdk1.8.0_71\jre\lib\security\cacerts -file D:/keys/sawade.crt -alias sawade
		输入密钥库口令:
		所有者: CN=wade, OU=saxon, O=saxon, L=suzhou, ST=jiangsu, C=cn
		发布者: CN=wade, OU=saxon, O=saxon, L=suzhou, ST=jiangsu, C=cn
		序列号: ba31a53
		有效期开始日期: Wed Aug 10 15:05:13 CST 2016, 截止日期: Tue Nov 08 15:05:13 CST 2016
		证书指纹:
		         MD5: 31:DE:75:FE:A0:6C:94:A9:52:0A:E4:F1:5B:44:B1:2F
		         SHA1: BF:2C:32:A5:AA:36:25:33:64:BC:26:65:6F:DB:00:1A:89:83:B3:84
		         SHA256: 24:FE:5D:8E:A7:D3:08:D6:9E:8A:A9:42:5B:4C:D7:84:92:C4:52:71:ED:4C:91:C2:17:4E:8C:73:63:95:2D:E2
		         签名算法名称: SHA256withRSA
		         版本: 3

		扩展:

		#1: ObjectId: 2.5.29.14 Criticality=false
		SubjectKeyIdentifier [
		KeyIdentifier [
		0000: 55 91 32 AB 23 15 B9 EA   AE 49 21 EE 17 33 AC 29  U.2.#....I!..3.)
		0010: 98 F7 44 FA                                        ..D.
		]
		]

		是否信任此证书? [否]:  y
		证书已添加到密钥库中

+ 第四步:
修改: tomcat conf/server.xml 

		<connector port="443" protocol="HTTP/1.1" SSLEnabled="true" 
			maxThreads="150" scheme="https" secure="true"
			clientAuth="false" sslProtocol="TLS"
			keystoreFile="D:/keys/apachejava"
			keystorePass="123123"/>
