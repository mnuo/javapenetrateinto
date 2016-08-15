/**
 * ActiveMQReceiveMessage.java created at 2016年8月15日 下午4:20:45
 */
package com.mnuocom.largedistributedweb.chapter2.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author saxon
 */
public class ActiveMQReceiveMessage {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("MessageQueue");
		MessageConsumer consumer = session.createConsumer(destination);
		
		while(true){
			//取出消息
			ObjectMessage message = (ObjectMessage) consumer.receive(10000);
			if(null != message){
				String messageContent = (String) message.getObject();
				System.out.println("接收消息: " + messageContent);
			}else{
				break;
			}
			
		}

	}

}
