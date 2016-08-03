---
title: 大型分布式网站--1面向服务的体系架构(SOA)
date: 2016-08-02 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 1 面向服务的体系架构(SOA)

#### 1 基于TCP协议的RPC

+ 1 PRC
> Remote Process Call,即远程过程调用,他应用广泛实现方式也很多,拥有RMI,WebService等诸多成熟的方案,在业界得到了广泛的使用

+ 2 对象序列化
	- 将对象转换成二进制流的过程称为对象的序列化
	- 将二进制转成对象的过程称为对象的反序列化
方案:
	- Google的Protocal Buffers: 性能优异,支持跨平台,无法直接使用Java等面向对象编程语言
	- java内置序列化方式,Hessian:Hessian相对Protocal Buffers,效率稍低,但对各种语言有着良好的支持,且性能稳定,比java内置反式效率要高很多
	- JSON和XML

例子:
	java内置对象:
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//序列化,将对象存入ByteArrayOutputStream,即写入内存中,也可以存入文件中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		oos.writeObject(new Persion("张三"));
		byte[] zhangsan = bos.toByteArray();
		
		//反序列化,将对象从内存中,读取出来
		ByteArrayInputStream bis = new ByteArrayInputStream(zhangsan);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Persion object = (Persion) ois.readObject();
		System.out.println(object.getName());
	}
	Hessian:
	public static void main(String[] args) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//Hessian的序列化输出
		HessianOutput ho = new HessianOutput(bos);
		ho.writeObject(new Persion("李四"));
		byte[] data = bos.toByteArray();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		//Hessian反序列化读取对象
		HessianInput hi = new HessianInput(bis);
		Persion lizi = (Persion) hi.readObject();
		
		System.out.println(lizi.getName());
	}

+ 3 基于TCP的RPC例子

#### 2 基于HTTP协议的RPC

##### 2.1 HTTP协议栈
HTTP是Hypertext Transfer Protocol(超文本传输协议)的缩写
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largeDistributed-1.2-http-req-resp.jpg)
例子: 客户端会向服务端发送一条命令,服务端收到命令后,会判断命令是否为HELLO 如果是"HELLO",则返回给客户端的相应为"hello",否则返回个客户端的相应为"bye".

##### 2.2 HTTP请求与响应
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.2-http-comunite.jpg)











