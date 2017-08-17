package sc.ejb.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: TestTopicMessageBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic"), @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "topic/test")
		},
		mappedName = "topic/test")
public class TestTopicMessageBean implements MessageListener {

    /**
     * Default constructor.
     */
    public TestTopicMessageBean() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see MessageListener#onMessage(Message)
     */
    @Override
	public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	try{
             TextMessage tmsg = (TextMessage)message;
             System.out.println("Received Topic Message:" + tmsg.getText());
             Thread.sleep(200000);
    	}catch(Exception e){
             e.printStackTrace();
    	}
    }

}
