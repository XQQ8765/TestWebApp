package sc.servlet.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import sc.service.ws.TestInterface;

public class InvokeEmbeddedWebServiceExecutor implements Runnable {
	private AsyncContext ctx = null;

	public InvokeEmbeddedWebServiceExecutor(AsyncContext ctx){
		this.ctx = ctx;
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


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			PrintWriter out = ctx.getResponse().getWriter();
			TestInterface emService = this.createService((HttpServletRequest) ctx.getRequest());
			out.println("Successfully Invoked Embeded test web service");
			out.println("<br>");
			out.println("Invoked WebMethod 'sayHey' with parameter 'Test Message'");
			out.println("<br>");
			out.println("Return: "+emService.sayHey("Test Message"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ctx.complete();
		}

	}

}
