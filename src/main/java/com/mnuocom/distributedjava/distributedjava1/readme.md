#### 第一章: 分布式Java应用
<img src="http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-%E5%88%86%E5%B8%83%E5%BC%8FJava%E5%BA%94%E7%94%A8%E5%9F%BA%E7%A1%80%E4%B8%8E%E5%AE%9E%E8%B7%B5-%E5%88%86%E5%B8%83%E5%BC%8Fjava%E5%BA%94%E7%94%A81.jpgmnuo-%E5%88%86%E5%B8%83%E5%BC%8FJava%E5%BA%94%E7%94%A8%E5%9F%BA%E7%A1%80%E4%B8%8E%E5%AE%9E%E8%B7%B5-%E5%88%86%E5%B8%83%E5%BC%8Fjava%E5%BA%94%E7%94%A81.jpg" width="400"/>
##### 1 网络通信

1.1 协议

+ TCP/IP 是一种可靠的网络数据传输协议,TCP/IP要求通信双方首先建立连接,之后再进行数据的传输,TCP/IP负责保证数据传输的可靠性,包括数据的可到达,数据到达的顺序等,但是会牺牲一些性能
+ UDP/IP 是一种不保证数据一定到达的网络传输协议,UDP/IP并不直接给通信双方建立连接,而是发送到网络上进行传递,不能保证数据传输的可靠,因此性能上表现比较好,但可能会出现数据的丢失以及数据乱序的现象
+ Multicast
    	
1.2 IO

TCP/UDP可用于完成数据的传输,弹药完成系统间通信,还需要对数据进行处理,例如读取和写入数据,按照POSIX标准分为同步IO和异步IO,其中同步IO中最长用的的是BIO(Blocking IO)和NIO(Non-blocking IO)

+ BIO 当发起IO的读或者写操作时,均是阻塞方式,只有当程序读到了流或者将流写入操作系统后,才会释放资源
+ NIO 是基于时间驱动思想的,实现上通常采用Reactor模式,从程序角度而言,当发起IO的读或者写操作时,是非阻塞的,当socket有流可读或者可写入socket时,操作系统会相应的通知应用程序进行处理,应用再将流读取到缓冲区或写入操作系统
+ AIO 是异步IO方式,同样基于事件驱动思想,实现上通常采用Proactor模式

			
##### 2 基于消息方式实现系统间的通信
1.1 基于Java自身技术实现消息方式的系统   

+ TCP/IP + BIO
> 在Java中可基于Socket,ServerSocket来实现TCP/IP +ＢＩＯ的系统间通信，Socket主要用于实现建立连接及网络IO的操作,ServerSocket主要用于实现服务器端端口的监听及Socket对象的获取
+ TCP/IP + NIO
> 在Java中可基于java.nio.channels中的Channel和Selector的相关类来实现,TCP/IP + NIO的系统间通信.Channel有SocketChannel和ServerSocketChannel两种,SocketChannel用于建立连接监听事件及操作读写,ServerSocketChannel用于监听端口及监听连接事件;程序通过Selector来获取是否有要处理的事件
+ UDP/IP + BIO
> UDP/IP + BIO 的网络数据传输同样采用Socket机制,只是UDP/IP下的Socket没有建立连接的要求,由于UDP/IP是无连接的,因此
无法进行双向通信.这也就要求如果实现双向通信的话,必须两端都成为UDP server
在java中可基于DatagramSocket和DatagramPacket来实现UDP/IP + BIO方式的通信,DatagramSocket负责监听端口及读写数据DatagramPacket作为数据流对象进行传输
+ UDP/IP + NIO
> 在JAVA中可通过DatagramChannel和byteBuffer来实现UDP/IP + NIO方式的系统间通信,DatagramChannel负责监听端口及进行读写,ByteBuffer则用于数据流传输
+ MulticastSocket + DatagramPacket  
> 在Java中可基于MulticastSocket 和DatagramPacket来实现多播网络通信,MulticastSocket是基于
DataGramSocket派生出来的类,其作用即为基于UDP/IP实现多播方式的网络通信。在多播通信中,不同的地方在于接收数据端通过加入到多
播组来进行数据的接收,同样发送数据也要求加入到多播组进行发送。多播的目标地址具有指定的地址范围,在244.0.0.0和239.255.255.255之间

1.2 基于开源框架实现消息方式的系统间通信  

+ Mina

    + Mina是Apache的顶级项目,基于Java NIO构建,同时支持TCP/IP和UDP/IP两种协议.Mina对外屏蔽了 JAVA NIO使用的复杂性,并在性能上做了不少优化
    
	+ 在使用Mina时,关键的类为IoConnector,IoAcceptor,IoHandler及IoSession,Mina采用Filter Chain的方式封装消息发送和接收的流程,在这个Filter Chain过程中可进行消息的处理,消息的发送和接收
	
    > + IoConnector 负责配置客户端的消息处理器,IO事件处理线程池,消息发送/接收的Filter Chain等
    > + IoAcceptor 负责配置服务器的IO事件处理线程池,消息发送/接收的Filter Chain等
    > + IoHandler 作为Mina和应用的接口,当发生了连接事件,IO事件或异常事件时,Mina都会通知应用所实现的IoHandler
    > + IoSession 有点类似SocketChannel的封装,不过Mina对连接做了进一步的抽象,因此可进行更多连接的控制及流信息的输出

##### 3  基于远程调用方式实现系统间的通信
2.1 基于Java自身技术实现远程调用方式的系统间通信

+ RMI
> RMI服务器端通过启动RMI注册对象在一个端口上监听对外提供的接口,其实现实例以字符串的方式绑定到RMI注册对象上。RMI客户端通过proxy的方式
代理了对服务器端接口的访问,RMI客户端将要访问的服务器端对象字符串,方法和和参数封装个一个对象,序列化成流后通过TCP/IP + BIO 传输到RMI服务器端
RMI服务器端接收到客户端的请求对象后,解析其中的对象字符串,方法及参数,通过对象字符串从RMI注册对象上找到提供业务功能的实例,之后结合要访问的方法来反射获取到方法实例对象,
传入参数完成对服务器端对象实例的调用,返回的结果则序列化为流以TCP/IP + BIO 方式返回给客户端,客户端再接受到此流后反序列化为对象,并返回给调用者
    
    服务器端关键代码:
        /**
         * RMI 要求服务器端的接口继承Remote接口,
         * 接口上的美中方法必须抛出RemoteException,服务器端业务类通过实现此接口
         * 提供业务功能,然后通过调用UnicastRemoteObject.exportObject
         * 来将此对象绑定到某端口上,最后将此对象注册到本地的LocatRegistry上,
         * 此时形成一个字符串对应于对象实例的映射关系
    	 * @param args
    	 * @throws Exception
    	 */
    	public static void main(String[] args) throws Exception {
    		int port = 9527;
    		String name = "BusinessDemo";
    		Business business = new BusinessImpl();
    		UnicastRemoteObject.exportObject(business, port);
    		Registry registry = LocateRegistry.createRegistry(1099);
    		registry.rebind(name, business);
    	}
    
    客户端关键代码:
        /**
         * RMI 的客户端首先通过LocateRegistry.getRegistry来获取Registry对象,
         * 然后通过Registry.lookup 字符串获取要调用的服务器端接口的实例对象,
         * 最后以接口的方式透明的调用远程对象的方法
    	 * @param args
    	 * @throws NotBoundException 
    	 * @throws RemoteException 
    	 * @throws AccessException 
    	 */
    	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
    		Registry registry = LocateRegistry.getRegistry("127.0.0.1");
    		String name = "BusinessDemo";
    		//创建BusinessDemo类的代理类,当调用时,则调用127.0.0.1:1099上
            //名称为BisinessDemo的对象,如服务器端没有对应名称的绑定,
            //则抛出NotBoundException;如通信出现错误,则抛出RemoteException
    		Business business = (Business) registry.lookup(name);
    		System.out.println(business.echo("hello word"));
    		System.out.println(business.echo("quit"));
    	}
+ WebService
> WebService 是一种跨语言的系统间交互标准,在这个标准中,对外提供功能的一方以HTTP
的方式提供服务,该服务,采用WSDL(web service description language)描述,在这个文件中描述
服务所使用的协议,所期望的参数,返回的参数格式等,调用端和服务端通过SOAP(Simple Object Access Protocol)
方式进行交互

    服务端代码:
        public interface Business {
        /**
    	 * 显示客户端提供的信息,并返回
    	 * @return
    	 */
        	public String echo(String message);
        }
        
        @WebService(name="Business",serviceName="BusinessService",targetNamespace="http://"
    	+ "distributedjava.mnuocom.com/distributedjava1/webservice/client")
        @SOAPBinding(style=SOAPBinding.Style.RPC)
        public class BusinessImpl implements Business {
        	@Override
        	public String echo(String message) {
        		if("quit".equalsIgnoreCase(message.toString())){
        			System.out.println("Server will be shutdown!");
        			System.exit(0);
        		}
        		System.out.println("Message from client: " + message);
        		return "Server response: " + message;
        	}
        
        }
        
        public class WebServiceServer {
        
            /**
        	 * @param args
        	 */
        	public static void main(String[] args) {
        		Endpoint.publish("http://localhost:9527/BusinessService", new BusinessImpl());
        		System.out.println("Server has been started");
        	}
        
        }
    
    通过jdk/bin 下的命令生成文件
        wsimport -keep http://localhost:9527/BusinessService?wsdl
        
    客户端代码:
        public class Client {
            /**
        	 * @param args
        	 */
        	public static void main(String[] args) {
        		BusinessService businessService = new BusinessService();
        		Business business = businessService.getBusinessPort();
        		System.out.println(business.echo("hello world"));
        		System.out.println(business.echo("quit"));
        	}
        }
        
2.2 基于开源框架实现远程调用方式的系统间通信

+ Spring RMI 

    <img src="http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-1.2.0.jpg" width="500" />  
        
    代码:
    + server.xml
            <!--服务端--> 
            <bean id="businessService" class="com.mnuocom.distributedjava.distributedjava1.springrmi.server.BusinessImpl"/>
            <!-- 将类为一个RMI服务 -->
            <bean id="rmiBusinessService" class="org.springframework.remoting.rmi.RmiServiceExporter">
                <!-- 服务类 -->
            	<property name="service" ref="businessService"/>
            	<!-- 服务名 -->
            	<property name="serviceName" value="BusinessService"/>
            	<!-- 服务接口 -->
            	<property name="serviceInterface" value="com.mnuocom.distributedjava.distributedjava1.springrmi.server.Business"/>
            	<!-- 服务端口
            	<property name="registryPort" value="9999" />
            	 其他属性自己查看org.springframework.remoting.rmi.RmiServiceExporter的类,就知道支持的属性了-->
            </bean>
    + Server.java
            public static void main(String[] args) {
            	new ClassPathXmlApplicationContext("com/mnuocom/distributedjava/distributedjava1/springrmi/server/server.xml");
        		System.out.println("Server has been started!");
        	}
    + client.xml 
            <bean id="businessService" class="org.springframework.remoting.rmi.RmiProxyFactoryBean">
              
    + Client.java
            ApplicationContext ac = new ClassPathXmlApplicationContext("com/mnuocom/distributedjava/distributedjava1/springrmi/client/client.xml");
        	Business business = (Business) ac.getBean("businessService");
    		
    		System.out.println(business.echo("hello springRMI."));
    		System.out.println(business.echo("quit"));

+ CXF

<img src="http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-1.2.1.jpg" width="500" />

###### [源码地址]
[源码地址]: https://github.com/mnuo/javapenetrateinto/tree/master/src/com/mnuocom/distributedjava/distributedjava1
