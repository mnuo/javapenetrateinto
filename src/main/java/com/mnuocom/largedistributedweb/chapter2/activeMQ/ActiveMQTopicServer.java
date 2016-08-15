/**
 * ActiveMQTopicServer.java created at 2016年8月15日 下午4:29:11
 */
package com.mnuocom.largedistributedweb.chapter2.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author saxon
 */
public class ActiveMQTopicServer {

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
		
		MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		TextMessage message = session.createTextMessage();
		message.setText("message_hello_zhangsan");
		producer.send(message);
		//session.commit();
	}

}
