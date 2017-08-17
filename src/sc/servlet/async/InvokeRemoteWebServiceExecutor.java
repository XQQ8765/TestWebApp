package sc.servlet.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;



import java.rmi.RemoteException;

import javax.servlet.AsyncContext;
import javax.xml.rpc.ServiceException;
import javax.xml.namespace.QName;

import org.apache.axis.client.*;


public class InvokeRemoteWebServiceExecutor implements Runnable {
	private AsyncContext ctx = null;
	private URL wsdlURL = null;
	private String wsdlURLStr = null;
	private String nameSpace = null;
	private String uri = null ;
	private String mtdName = null;
	private Object[] params = null;
	private Class returnClass = null;
	public InvokeRemoteWebServiceExecutor(AsyncContext ctx, String wsdlURL, String nameSpace,
		 String mtdName,  Object[] params, Class returnClass){
		this.ctx = ctx;
		this.wsdlURLStr = wsdlURL;
		this.nameSpace =  nameSpace;
		this.mtdName = mtdName;
		//this.uri = uri;
		this.params = params;
		this.returnClass =  returnClass;
	}
	
	private Object callService() throws ServiceException, MalformedURLException, RemoteException{
		Object ret = null;
		Service service = new Service();
		Call call = (Call) service.createCall();
		this.wsdlURL = new URL(this.wsdlURLStr);
		call.setTargetEndpointAddress(this.wsdlURL); 
		
		//System.out.println(this.nameSpace+" "+this.mtdName);
		call.setOperationName(new QName(this.nameSpace,this.mtdName));
		//call.addParameter(new QName(this.nameSpace, "str"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
		//call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 
		//call.setUseSOAPAction(true); 
		ret = call.invoke(this.params);
		return ret;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		PrintWriter out = null;
		try {
			out = ctx.getResponse().getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {	
			//TestInterface emService = this.createService(); 
			Object returnValue = this.callService();
			out.println("Successfully Invoked Remote web service<br>");
			out.println("Web Service Endpoint: "+this.wsdlURLStr);
			out.println("<br>");
			out.println("Invoked WebMethod '"+mtdName+"'");
			out.println("<br>");
			out.println("Return: "+returnValue.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Remote web service <br>");
			out.println("Web Service Endpoint: "+this.wsdlURLStr);
			out.println(e.toString()+"<br>");
			e.printStackTrace(out);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Remote web service<br>");
			out.println("Web Service Endpoint: "+this.wsdlURLStr);
			out.println(e.toString()+"<br>");
			e.printStackTrace(out);
		}finally{
			ctx.complete();
		}
	
	}

}
