package sc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.ejb.EJB;
import javax.jms.JMSException;
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

import sc.ejb.session.TestStatelessSessionBean;
import sc.utils.AppPropertiesConfig;

import java.io.PrintWriter;
/**
 * Servlet implementation class InvokeEmbededTopic
 */
@WebServlet("/InvokeEmbededTopic")
public class InvokeEmbededTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @EJB TestStatelessSessionBean testStatelessSessionBean;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeEmbededTopic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.publishMessage(request, response);
		this.publishMessageWithEJB(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.publishMessage(request, response);
		this.publishMessageWithEJB(request, response);
	}
	
	private void publishMessageWithEJB(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String messageStr = "Hello,this is a topic message!";
		String factoryJNDIName = AppPropertiesConfig.getTopicConnectionFactoryJNDIName();
		String topicJNDINameStr = AppPropertiesConfig.getEmbededTopicJNDIName();
		String queueTopicMessage = request.getParameter("queueTopicMessage");
		String topicJNDIName = request.getParameter("topicJNDIName");
		if(queueTopicMessage!=null&&queueTopicMessage.length()>0){
			messageStr = queueTopicMessage;
		}
		if(topicJNDIName!=null&&topicJNDIName.length()>0){
			topicJNDINameStr = topicJNDIName;
		}
		try {
			
			testStatelessSessionBean.publishMessageToEmbededTopic(messageStr);
	       
	        out.println("Successfully publish message '"+messageStr+"' to embeded topic '"+topicJNDINameStr+"'");
	       
	       
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			out.println("Failed to publish message to the embeded topic "+topicJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			out.println("Failed to publish message to the embeded topic "+topicJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}
	
	
}	
