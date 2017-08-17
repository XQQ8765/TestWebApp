package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.ejb.session.TestStatelessSessionBeanRemote;
import sc.utils.AppPropertiesConfig;

/**
 * Servlet implementation class InvokeRemoteEJB
 */
@WebServlet("/InvokeRemoteEJB")
public class InvokeRemoteEJB extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeRemoteEJB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response){
Properties props = new Properties();


		//JBoss 7.1.1 properties
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, AppPropertiesConfig.getProperty("EJB_PROVIDER_URL"));
        props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put(Context.SECURITY_PRINCIPAL, AppPropertiesConfig.getProperty("EJB_SECURITY_PRINCIPAL"));
        props.put(Context.SECURITY_CREDENTIALS, AppPropertiesConfig.getProperty("EJB_SECURITY_CREDENTIALS"));
        props.put("jboss.naming.client.ejb.context", false);
		try {
			InitialContext ctx = new InitialContext(props);
			TestStatelessSessionBeanRemote bean = (TestStatelessSessionBeanRemote)ctx.lookup(AppPropertiesConfig.getProperty("remoteStatelessSessionBeanJNDIName"));
			//System.out.println(bean.getStr("asddd"));
			PrintWriter out = response.getWriter();

			//out.println(bean.getStr("asddd"));
			out.println("Invoked Remote TestStatelessSessionBean "+AppPropertiesConfig.getProperty("remoteStatelessSessionBeanJNDIName")+" Successfully, getStr: "+ bean.getStr("Test")+"<br><br>");

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
