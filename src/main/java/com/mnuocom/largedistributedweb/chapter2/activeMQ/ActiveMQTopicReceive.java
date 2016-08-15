/**
 * ActiveMQTopicReceive.java created at 2016年8月15日 下午4:33:06
 */
package com.mnuocom.largedistributedweb.chapter2.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author saxon
 */
public class ActiveMQTopicReceive {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://127.0.0.1:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("MessageTopic");
		
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
		@Override
		public void onMessage(Message message) {
			TextMessage tm = (TextMessage) message;
			System.out.println("topic message: " + tm);
		}
		});

	}

}
