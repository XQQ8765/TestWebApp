package sc.service.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sc.utils.AppPropertiesConfig;

public class LocalMessageProducerService {
	
	private String destinationType;
	private String qcfactoryJNDIName = AppPropertiesConfig.getQueueConnectionFactoryJNDIName();
	private String tcfactoryJNDIName = AppPropertiesConfig.getTopicConnectionFactoryJNDIName();
	private String queueJNDINameStr = AppPropertiesConfig.getEmbededQueueJNDIName();
	private String topicJNDINameStr = AppPropertiesConfig.getEmbededTopicJNDIName();
	public LocalMessageProducerService(String destinationType){
		this.destinationType = destinationType;
	}

	public LocalMessageProducerService(String destinationType, String destinationJNDINameStr){
		this.destinationType = destinationType;
		if(this.destinationType!=null&&this.destinationType.equalsIgnoreCase("queue")){
			this.queueJNDINameStr = destinationJNDINameStr;
		}
		else if(this.destinationType!=null&&this.destinationType.equalsIgnoreCase("topic")){
			this.topicJNDINameStr = destinationJNDINameStr;
		}
	}
	
	public void produceMessage(String messageStr) throws NamingException, JMSException{
		if(this.destinationType!=null&&this.destinationType.equalsIgnoreCase("queue")){
			this.sendMessageToEmbededQueue(messageStr);
		}
		else if(this.destinationType!=null&&this.destinationType.equalsIgnoreCase("topic")){
			this.publishMessageToEmbededTopic(messageStr);
		}
	}
	
	private void sendMessageToEmbededQueue(String messageStr) throws NamingException, JMSException{
		
			Context ctx = new InitialContext();
			QueueConnectionFactory factory = (QueueConnectionFactory)ctx.lookup(qcfactoryJNDIName);
	        QueueConnection connection = (QueueConnection) factory.createConnection();
	        QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	        Queue queue = (Queue)ctx.lookup(queueJNDINameStr);
	        TextMessage msg = session.createTextMessage(messageStr);
	        QueueSender sender = session.createSender(queue);
	        connection.start();
	        sender.send(msg);
	        session.close();
	        connection.close();

	}
	
	private void publishMessageToEmbededTopic(String messageStr) throws NamingException, JMSException{

			
			Context ctx = new InitialContext();
			TopicConnectionFactory factory = (TopicConnectionFactory)ctx.lookup(tcfactoryJNDIName);
	        TopicConnection connection = factory.createTopicConnection();
	        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	        Topic topic = (Topic)ctx.lookup(topicJNDINameStr);
	        TextMessage msg = session.createTextMessage(messageStr);
	        TopicPublisher publisher = session.createPublisher(topic);
	        connection.start();

	        publisher.publish(msg);

	        session.close();
	        connection.close();   
	       
		
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	public String getQcfactoryJNDIName() {
		return qcfactoryJNDIName;
	}

	public void setQcfactoryJNDIName(String qcfactoryJNDIName) {
		this.qcfactoryJNDIName = qcfactoryJNDIName;
	}

	public String getTcfactoryJNDIName() {
		return tcfactoryJNDIName;
	}

	public void setTcfactoryJNDIName(String tcfactoryJNDIName) {
		this.tcfactoryJNDIName = tcfactoryJNDIName;
	}

	public String getQueueJNDINameStr() {
		return queueJNDINameStr;
	}

	public void setQueueJNDINameStr(String queueJNDINameStr) {
		this.queueJNDINameStr = queueJNDINameStr;
	}

	public String getTopicJNDINameStr() {
		return topicJNDINameStr;
	}

	public void setTopicJNDINameStr(String topicJNDINameStr) {
		this.topicJNDINameStr = topicJNDINameStr;
	}
	
	
}
