package sc.ejb.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: TestQueueMessageBean
 */
/*@MessageDriven(
		activationConfig = {
				//@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "TestRemoteConnectionFactory"),
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic"), 
				@ActivationConfigProperty(
				propertyName = "destination", propertyValue = "testTopic"),
				//@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
				@ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.hornetq.core.remoting.impl.netty.NettyConnectorFactory"),
				@ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=10.30.146.108;port=5445")
		}, 
		name = "TestRemoteTopicMessageBean")
public class TestRemoteTopicMessageBean implements MessageListener {

    public TestRemoteTopicMessageBean() {
        // TODO Auto-generated constructor stub
    }
	

    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    try{
            TextMessage tmsg = (TextMessage)message;
             System.out.println("Received remote topic Message:" + tmsg.getText());
      }catch(Exception e){
             e.printStackTrace();
      }
    }

}*/
