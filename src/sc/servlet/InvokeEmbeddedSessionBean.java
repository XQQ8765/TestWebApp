package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.ejb.session.TestSingletonSessionBean;
import sc.ejb.session.TestStatefulSessionBean;
import sc.ejb.session.TestStatelessSessionBean;
import sc.servlet.async.InvokeEmbeddedSessionBeanExecutor;
import sc.utils.AppPropertiesConfig;

/**
 * Servlet implementation class InvokeEmbeddedSessionBean
 */
@WebServlet(urlPatterns = "/InvokeEmbeddedSessionBean", asyncSupported = true)
public class InvokeEmbeddedSessionBean extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String sessionBeanJNDINameStr;

    @EJB
    TestSingletonSessionBean testSingletonSessionBean;
    @EJB
    TestStatefulSessionBean testStatefulSessionBean;
    @EJB
    TestStatelessSessionBean testStatelessSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeEmbeddedSessionBean() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.invokeEmbeddedSessionBeanMethod(request, response);
		this.invokeEmbeddedSessionBeanMethodAsync(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.invokeEmbeddedSessionBeanMethod(request, response);
		this.invokeEmbeddedSessionBeanMethodAsync(request, response);
	}

	
	private void invokeEmbeddedSessionBeanMethodAsync(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		String seesionBeanType = request.getParameter("seesionBeanType");
		String sessionBeanJNDIName = request.getParameter("sessionBeanJNDIName");
		sessionBeanJNDINameStr = null;
		String defaultJNDIName = null;
		if(seesionBeanType.equalsIgnoreCase("stateful")){
			defaultJNDIName = AppPropertiesConfig.getEmbeddedStatefulSessionBeanJNDIName();
		}
		else if(seesionBeanType.equalsIgnoreCase("stateless")){
			defaultJNDIName = AppPropertiesConfig.getEmbeddedStatelessSessionBeanJNDIName();
		}else{
			defaultJNDIName = AppPropertiesConfig.getEmbeddedSingletonSessionBeanJNDIName();
		}
		if(sessionBeanJNDIName == null || sessionBeanJNDIName.length() == 0){
			sessionBeanJNDINameStr = defaultJNDIName;
		}
		else{
			sessionBeanJNDINameStr = sessionBeanJNDIName;
		}
		
		AsyncContext ctx = request.startAsync();
		new Thread(new InvokeEmbeddedSessionBeanExecutor(ctx,sessionBeanJNDINameStr, seesionBeanType)).start();	
		
	}
	
	private void invokeEmbeddedSessionBeanMethod(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String seesionBeanType = request.getParameter("seesionBeanType");
		String sessionBeanJNDIName = request.getParameter("sessionBeanJNDIName");
		sessionBeanJNDINameStr = null;
		String defaultJNDIName = null;
		if(seesionBeanType.equalsIgnoreCase("stateful")){
			defaultJNDIName = AppPropertiesConfig.getEmbeddedStatefulSessionBeanJNDIName();
		}
		else if(seesionBeanType.equalsIgnoreCase("stateless")){
			defaultJNDIName = AppPropertiesConfig.getEmbeddedStatelessSessionBeanJNDIName();
		}else{
			defaultJNDIName = AppPropertiesConfig.getEmbeddedSingletonSessionBeanJNDIName();
		}
		if(sessionBeanJNDIName == null || sessionBeanJNDIName.length() == 0){
			sessionBeanJNDINameStr = defaultJNDIName;
		}
		else{
			sessionBeanJNDINameStr = sessionBeanJNDIName;
		}
		
		if(seesionBeanType.equalsIgnoreCase("stateful")){
			this.invokeEmbeddedStateful(request, response);
		}
		else if(seesionBeanType.equalsIgnoreCase("stateless")){
			this.invokeEmbeddedStateless(request, response);
		}else{
			this.invokeEmbeddedSingleton(request, response);
		}
		
	}
	
	private void invokeEmbeddedStateful(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		try {
			out.println("Invoked TestStatefulSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testStatefulSessionBean.getStr("Test")+"<br><br>");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Stateful Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}
	
	private void invokeEmbeddedStateless(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		try {
			out.println("Invoked TestStatelessSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testStatelessSessionBean.getStr("Test")+"<br><br>");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Stateless Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}
	
	private void invokeEmbeddedSingleton(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		try {
			out.println("Invoked TestSingletonSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testSingletonSessionBean.getStr("Test")+"<br><br>");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Singleton Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}
}
