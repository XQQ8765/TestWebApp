package sc.ejb.session;



import javax.jms.JMSException;
import javax.naming.NamingException;

import sc.service.jms.LocalMessageProducerService;


public class BaseSessionBean {

	public String getStr(String s) {
		// TODO Auto-generated method stub
		return s;
	}
	
	
	public void sendMessageToEmbededQueue(String messageStr) throws NamingException, JMSException{
		
		LocalMessageProducerService lmps = new LocalMessageProducerService("queue");
		lmps.produceMessage(messageStr);

	}
	
	public void publishMessageToEmbededTopic(String messageStr) throws NamingException, JMSException{

		LocalMessageProducerService lmps = new LocalMessageProducerService("topic"); 
		lmps.produceMessage(messageStr);
		
	}
	
	
}
