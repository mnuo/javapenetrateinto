---
title: 大型分布式网站--3 互联网安全架构(常见的Web攻击手段)
date: 2016-08-05 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 3 互联网安全架构

#### 1 常见的Web攻击手段
##### 1.1 XSS 攻击
XSS攻击的全称跨站脚本攻击(Cross Site Scripting),为不跟层叠样式表(Cascading Style Sheets,CSS)的混淆,故将跨站脚本攻击缩写为XSS.跨站脚本攻击指的是攻击者在网页中嵌入恶意脚本程序,当用户打开网页时,脚本程序便开始在客户端的浏览器上执行,以盗取客户端cookie,用户名密码,下载执行病毒木马程序,甚至是获取客户端admin权限等.

+ XSS攻击原理:
> 例如:当一个恶意用户,在一家知名博客网站上转载了一篇非常火的博文,文章中嵌入了而已的脚本代码,其他人访问这篇文章时,嵌入文章中的脚本代码便会执行,达到恶意攻击用户的目的

+ XSS攻击的防范
> XSS之所以会发生,是因为用户输入的数据变成了代码,因此,我们需要对用户输入的数据进行HTML转义处理,将其中的"尖括号","单引号",等特殊字符进行转义编码.

##### 1.2 CSRF 攻击
CSRF攻击的全称是跨站请求伪造(cross site request forgery),是一种对网站的恶意利用,尽管听起来跟XSS跨站脚本攻击有点相似,但事实上CSRF与XSS差别很大,XSS利用的是站点内的信任用户,而CSRF则是通过伪装来自受信任用户的请求来利用受信任的网站.可以这么理解CSRF攻击:攻击者盗用了你的身份,以你的名义向第三方网站发送而已请求.CRSF能做的事情包括利用你的什么发邮件,发短信,进行交易转账等,甚至盗取你的账号.

 + CSRF 攻击原理
 ![CRSF攻击原理](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.3-crsf.jpg)

 + CSRF 防范
 
     - 将cookie设为HTTPONLY
 	- 使用token

##### 1.3 SQL注入攻击
 SQL注入就是通过把SQL命令伪装成正常的HTTP请求参数,传递到服务端,欺骗服务器最终执行恶意的SQL命令,达到入侵的目的.

+ SQL注入的防范:
 	- 使用预编译语句
 	- 使用ORM框架 mybatis,hibernate
 	- 避免免密码明文存放

##### 1.4 文件上传漏洞
文件上传攻击指的是恶意攻击者,利用一些站点没有对文件的类型做很好的检验,上传了可执行的文件或者脚本,并且通过脚本获得服务器上相应的权利,或者是通过有道外部用户访问,下载上传的病毒或木马文件,达到攻击的目的.

+ 1 通过魔数判断文件上传类型
很多类型的文件,起始的几个字节内容是固定,因此,根据几个字节的内容,就可以确定文件类型,这几个字节也被称为魔数(magic number)

		/**
		 * @author saxon
		 */
		public class MagicNumberDemo {
			/**
			 * @param args
			 * @throws Exception 
			 */
			public static void main(String[] args) throws Exception {
				System.out.println(getType("C:\\Users\\Administrator\\Desktop/0014100a9e7720160406130553890894.jpg"));
				System.out.println(getType("C:\\Users\\Administrator\\Desktop/data.xml"));
				System.out.println(getType("D:\\appserver\\eclipse\\eclipse/eclipse.exe"));
			}
			/**
			 * 读取文件头
			 * @param filePath
			 * @return
			 * @throws Exception
			 */
			private static String getFileHeader(String filePath) throws Exception{
				//在这里需要注意的是,每个文件的魔数的长度都不相同,因此需要使用startwith
				byte[] b = new byte[28];
				InputStream inputStream = new FileInputStream(filePath);
				inputStream.read(b, 0, 28);
				inputStream.close();
				return bytes2hex(b);
			}
			/**
			 * 判断文件类型
			 * @param filePath
			 * @return
			 * @throws Exception
			 */
			public static FileType getType(String filePath) throws Exception{
				String fileHead = getFileHeader(filePath);
				if(fileHead == null || fileHead.length() == 0){
					return null;
				}
				fileHead = fileHead.toUpperCase();
				FileType[] fileTypes = FileType.values();
				for(FileType type : fileTypes){
					if(fileHead.startsWith(type.getValue())){
						return type;
					}
				}
				return null;
			}
			public static String bytes2hex(byte[] bs){
				StringBuffer sBuffer = new StringBuffer(bs.length);
				String stemp;
				for(int i = 0; i < bs.length; i ++){
					stemp = Integer.toHexString(0xFF & bs[i]);
					if(stemp.length() < 2){
						sBuffer.append(0);
					}
					sBuffer.append(stemp.toUpperCase());
				}
				return sBuffer.toString();
			}
		}
		enum FileType{
			JPEG("FFD8FF"),
			PNG("89504E47"),
			GIF("47494638"),
			TIFF("49492A00"),
			BMP("424D"),
			DWG("41433130"),
			PSD("38425053"),
			XML("3C3F786D6C"),
			HTML("68746D6C3E"),
			PDF("255044462D312E"),
			ZIP("504B0304"),
			RAR("52617221"),
			WAV("57415645"),
			AVI("41564920");
			
			private String value="";
			
			private FileType(String value) {
				this.value = value;
			}
			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
		}

+ imagemagick + jmagick
imagemagick是一套功能强大,稳定并且开源的针对图片处理的开发工具包,能够处理多种格式的图片文件,可以利用imagemagick来对图片进行缩放处理.    
jmagick 由于imagemagick没有提供jni对应的头文件,如果要在Java环境中使用imagemagick还需要安装jmagick,通过jmagick来对imagemagick进行调用

##### 3.1.5 DDoS攻击
DDos(Distributed Denial of Service)即分布式拒绝服务攻击,是目前最为强大,最难以防御的攻击方式之一.
DoS攻击就是利用合理的客户端请求来占用过多的服务器资源,从而使合法用户无法 得到服务器的相应.DDoS攻击手段是在传统的DoS攻击基础之上产生的一了诶攻击方式.DDoS攻击手段是在传统的DoS攻击基础之上产生的一类攻击方式,传统的DoS攻击一般是采用一对一的方式,当攻击目标的CPU速度,内存或者网络宽带等各项性能指标不高的情况下,他的效果是明显的.   
DDoS的原理就非常简单了,它指的是攻击者借助公共网络,将数量庞大的计算机设备联合起来作为攻击平台,对一个或多个目标发动攻击,从而达到瘫痪目标主机的目的.

方式:  

+ 1 SYN Food是互联网最经典的攻击方式之一,它利用了TCP协议三次握手的过程来达成攻击的目的.攻击者伪造大量的IP地址给服务器发送SYN报文,但是由于伪造的IP地址几乎不可能存在,也就不可能从客户端得到任何回应,服务端将维护一个非常大的半连接等待队列,并且不断对这个列表中的IP地址进行遍历和重试,占用了大量的系统资源,更为重要的是,由于服务器资源有限,大量的恶意客户端信息沾满了服务器的等待队列,导致服务器不再接收新的SYN请求,正常用户无法完成三次握手与服务器进行通信,这便是SYN Flood攻击.   
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.3-ddos-tcp.jpg)

+ 2 DNS Query Flood
DNS Query Flood 实际上是UDP Flood 攻击的一种变形,由于DNS服务在互联网中具有不可替代的作用,一旦DNS服务器瘫痪,影响甚大.DNS Query Flood攻击采用的方法是向被攻击的服务器发送海量的域名解析请求,通常,请求解析的域名是随机生成的,大部分根本就不存在,并且通过伪造端口和客户端IP 方式查询请求被ACL过滤

+ 3 CC攻击
CC(Challenge Collapsar)攻击属于DDos的一种,是基于应用层HTTP协议发起的DDos攻击,也被称为HTTP Flood. CC攻击的原理是这样的,攻击者通过控制的大量"肉鸡"或者利用从互联网上搜寻的大量匿名的HTTP代理,模拟正常的用户给网站发起请求,知道该网站拒绝服务为止.
