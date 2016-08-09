---
title: 大型分布式网站--3 互联网安全架构(2常用的安全算法)
date: 2016-08-09 16:12:08 
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

#### 2 对称加密算法
对称加密算法中,数据发送方将明文(原始数据) 和加密密钥一起经过特殊加密算法处理后,生成复杂的加密密文进行发送,数据接收方收到密文后,弱项读取原文,则需要使用加密使用的密钥及相同算法的逆算法对加密的密文进行解密,才能使其恢复成可读明文.在对称加密算法中,使用的密钥只要一个,发送和接受双方都使用这个密钥对数据进行加密和解密,这就要求加密和解密方事先都必须知道加密的密钥.

##### 2.1 DES算法
DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，24小时内即可被破解。[desdemo]

[desdemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/symmetricencryption/DESDemo.java


##### 2.2 AES 算法
AES (Advanced Encryption Standard) 高级加密标准,作为新一代的数据加密标准,汇聚了强安全性,高性能,高效率,易用和灵活等优点.

#### 3 非对称加密算法
非对称加密算法又称为公开密钥加密算法,他需要两个密钥,一个称为公开密钥(public key)即公钥;另一个称为私有密钥(private key),即密钥.公钥加密,私钥解密;私钥加密,公钥解密.

##### 3.1 RSA 算法
RSA公钥加密算法是1977年由罗纳德·李维斯特（Ron Rivest）、阿迪·萨莫尔（Adi Shamir）和伦纳德·阿德曼（Leonard Adleman）一起提出的。1987年首次公布，当时他们三人都在麻省理工学院工作。RSA就是他们三人姓氏开头字母拼在一起组成的。RSA是目前最有影响力的公钥加密算法，它能够抵抗到目前为止已知的绝大多数密码攻击，已被ISO推荐为公钥数据加密标准。[RSAdemo]

[RSAdemo]: https://github.com/mnuo/javapenetrateinto/tree/master/src/main/java/com/mnuocom/largedistributedweb/chapter3/rsa/RSADemo.java

#### 4 数字签名
将报文按双方约定的HASH算法计算得到一个固定位数的报文摘要。在数学上保证：只要改动报文中任何一位，重新计算出的报文摘要值就会与原先的值不相符。这样就保证了报文的不可更改性。将该报文摘要值用发送者的私人密钥加密，然后连同原报文一起发送给接收者，而产生的报文即称数字签名

##### 4.1 MD5withRSA
很容易理解,MD5withRSA 算法表示采用MD5算法生成需要发送正文的数字摘要,并使用RSA算法来对正文进行加密和解密.
	
	/**
	 * 签名2
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign1(byte[] content, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(content);
		return signature.sign();
	}
	/**
	 * 验证2
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify1(byte[] content, byte[] sign, PublicKey publicKey) throws Exception{
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey);
		signature.update(content);
		return signature.verify(sign);
	}

##### 4.2 SHA1withRSA
	
	/**
	 * 签名2
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign1(byte[] content, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privateKey);
		signature.update(content);
		return signature.sign();
	}
	/**
	 * 验证2
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify1(byte[] content, byte[] sign, PublicKey publicKey) throws Exception{
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(publicKey);
		signature.update(content);
		return signature.verify(sign);
	}

#### 5 数字证书
数字证书就是互联网通讯中标志通讯各方身份信息的一系列数据，提供了一种在Internet上验证您身份的方式，其作用类似于司机的驾驶执照或日常生活中的身份证。它是由一个由权威机构-----CA机构，又称为证书授权（Certificate Authority）中心发行的，人们可以在网上用它来识别对方的身份。数字证书是一个经证书授权中心数字签名的包含公开密钥拥有者信息以及公开密钥的文件。最简单的证书包含一个公开密钥、名称以及证书授权中心的数字签名。

具体的说明,参考文章[http://blog.csdn.net/oscar999/article/details/9364101]

[http://blog.csdn.net/oscar999/article/details/9364101]: http://blog.csdn.net/oscar999/article/details/9364101

证书管理工具: 

+ keytool  
+ openSSL 

