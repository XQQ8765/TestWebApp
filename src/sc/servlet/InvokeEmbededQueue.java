package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.ejb.session.TestStatelessSessionBean;
import sc.utils.AppPropertiesConfig;

import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * Servlet implementation class InvokeEmbededQueue
 */
@WebServlet("/InvokeEmbededQueue")
public class InvokeEmbededQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB TestStatelessSessionBean testStatelessSessionBean;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeEmbededQueue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.sendMessage(request, response);
		this.sendMessageWithEJB(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.sendMessage(request, response);
		this.sendMessageWithEJB(request, response);
	}

	private void sendMessageWithEJB(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String messageStr = "Hello,this is a queue message!";
		
		//String factoryJNDIName = AppPropertiesConfig.getQueueConnectionFactoryJNDIName();
		String queueJNDINameStr = AppPropertiesConfig.getEmbededQueueJNDIName();
		String queueTopicMessage = request.getParameter("queueTopicMessage");
		String queueJNDIName = request.getParameter("queueJNDIName");
		if(queueTopicMessage!=null&&queueTopicMessage.length()>0){
			messageStr = queueTopicMessage;
		}
		if(queueJNDIName!=null&&queueJNDIName.length()>0){
			queueJNDINameStr = queueJNDIName;
		}
		
		try {
			
			testStatelessSessionBean.sendMessageToEmbededQueue(messageStr);
	       
	        out.println("Successfully send message '"+messageStr+"' to embeded queue '"+queueJNDINameStr+"'");
	       
	       
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			out.println("Failed to send message to the embeded queue "+queueJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			out.println("Failed to send message to the embeded queue "+queueJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}
	
	
}
