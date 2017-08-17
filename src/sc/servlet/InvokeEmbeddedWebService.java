package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import sc.service.ws.TestInterface;
import sc.servlet.async.InvokeEmbeddedWebServiceExecutor;

/**
 * Servlet implementation class InvokeEmbeddedWebService
 */
@WebServlet(urlPatterns = "/InvokeEmbeddedWebService", asyncSupported = true)
public class InvokeEmbeddedWebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Service service = null;
	private static TestInterface ti = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeEmbeddedWebService() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static void initService(HttpServletRequest request) throws MalformedURLException{
    	String URLstr = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/TestWebService?wsdl";
    	URL wsdlURL = new URL(URLstr);
    	QName serviceQName = new QName("http://testwebappws", "TestWebService");
    	QName portQName = new QName("http://testwebappws", "TestWebServicePort");
		service = Service.create(wsdlURL, serviceQName);
		ti = service.getPort(portQName, TestInterface.class);
    }

    private TestInterface createService(HttpServletRequest request) throws MalformedURLException{
    	String URLstr = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/TestWebService?wsdl";
    	URL wsdlURL = new URL(URLstr);
    	QName serviceQName = new QName("http://testwebappws", "TestWebService");
    	QName portQName = new QName("http://testwebappws", "TestWebServicePort");
    	Service service = Service.create(wsdlURL, serviceQName);
    	TestInterface ti = service.getPort(portQName, TestInterface.class);
    	return ti;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.invokeEmbeddedWebServiceMethod(request, response);
		this.invokeEmbeddedWebServiceMethodAsync(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.invokeEmbeddedWebServiceMethod(request, response);
		//this.invokeEmbeddedWebServiceMethodAsync(request, response);
	}


	private void invokeEmbeddedWebServiceMethod(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			//if(ti == null){
			//	initService(request);
			//}
			TestInterface emService = this.createService(request);
			out.println("Successfully Invoked Embeded test web service");
			out.println("<br>");
			out.println("Invoked WebMethod 'sayHey' with parameter 'Test Message'");
			out.println("<br>");
			out.println("Return: "+emService.sayHey("Test Message"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Embeded web service 'TestWebService'"+"<br>");
			out.println(e.toString()+"<br>");
			e.printStackTrace(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Embeded web service 'TestWebService'"+"<br>");
			out.println(e.toString()+"<br>");
			e.printStackTrace(out);
		}
	}

	private void invokeEmbeddedWebServiceMethodAsync(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
		AsyncContext ctx = request.startAsync();
		new Thread(new InvokeEmbeddedWebServiceExecutor(ctx)).start();
	}

}
