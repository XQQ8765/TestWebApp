package sc.ejb.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: TestQueueMessageBean
 */

@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "queue/test")
		},
		mappedName = "queue/test")
public class TestQueueMessageBean implements MessageListener {

    /**
     * Default constructor.
     */
    public TestQueueMessageBean() {
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
             System.out.println("Received queue Message:" + tmsg.getText());
      }catch(Exception e){
             e.printStackTrace();
      }
    }

}
