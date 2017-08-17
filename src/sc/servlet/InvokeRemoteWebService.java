package sc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import sc.servlet.async.InvokeEmbeddedWebServiceExecutor;
import sc.servlet.async.InvokeRemoteWebServiceExecutor;

/**
 * Servlet implementation class InvokeRemoteWebService
 */
@WebServlet(urlPatterns = "/InvokeRemoteWebService",  asyncSupported = true)
public class InvokeRemoteWebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvokeRemoteWebService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.invokeRemoteWebServiceMethodAsync(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//this.invokeRemoteWebServiceMethodAsync(request, response);
		this.invokeRemoteWebServiceMethod(request, response);
	}
	
	private void invokeRemoteWebServiceMethod(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		
		String remoteWebServiceEndpointAddress = request.getParameter("remoteWebServiceEndpointAddress");
		String wsMethod = request.getParameter("wsMethod");
		String remoteWebServiceNameSpace = request.getParameter("remoteWebServiceNameSpace");
		String rwsParam = request.getParameter("rwsParam");
		Object[] params = null;
		if(wsMethod!=null){
			if(!wsMethod.equalsIgnoreCase("getCurrentTime")){
				params = new Object[]{rwsParam};
				
			}
			
			
			Object ret = null;
			Service service = new Service();
			Call call;
			PrintWriter out = response.getWriter();
			try {
				
				call = (Call) service.createCall();
			
				URL wsdlURL = new URL(remoteWebServiceEndpointAddress);
				
				call.setTargetEndpointAddress(wsdlURL); 
				
				//System.out.println(this.nameSpace+" "+this.mtdName);
				call.setOperationName(new QName(remoteWebServiceNameSpace,wsMethod));
				//call.addParameter(new QName(this.nameSpace, "str"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
				//call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 
				//call.setUseSOAPAction(true); 
				//call.invoke(params);
				ret = call.invoke(params);
				
				out.println("Successfully Invoked Remote web service<br>");
				out.println("Web Service Endpoint: "+remoteWebServiceEndpointAddress);
				out.println("<br>");
				out.println("Invoked WebMethod '"+wsMethod+"'");
				out.println("<br>");
				out.println("Return: "+ret.toString());
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.println("Failed to invoke Remote web service<br>");
				e.printStackTrace(out);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.println("Failed to invoke Remote web service<br>");
				e.printStackTrace(out);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.println("Failed to invoke Remote web service<br>");
				e.printStackTrace(out);
			}
		}
	}
	
	private void invokeRemoteWebServiceMethodAsync(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
		AsyncContext ctx = request.startAsync();
		String remoteWebServiceEndpointAddress = request.getParameter("remoteWebServiceEndpointAddress");
		String wsMethod = request.getParameter("wsMethod");
		String remoteWebServiceNameSpace = request.getParameter("remoteWebServiceNameSpace");
		String rwsParam = request.getParameter("rwsParam");
		Object[] params = null;
		if(wsMethod!=null){
			if(!wsMethod.equalsIgnoreCase("getCurrentTime")){
				params = new Object[]{rwsParam};
				
			}
			new Thread(
					new InvokeRemoteWebServiceExecutor(
							ctx, 
							remoteWebServiceEndpointAddress,
							remoteWebServiceNameSpace,
							wsMethod,
							params,
							String.class
					)).start();
		}
	}

}
