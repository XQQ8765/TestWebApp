package sc.service.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;

import javax.jms.JMSException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import sc.service.db.DSService;
import sc.service.jms.LocalMessageProducerService;
import sc.utils.AppPropertiesConfig;


@WebService(name = "TestWebService", serviceName="TestWebService", targetNamespace = "http://testwebappws")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TestService implements TestInterface {
	@Override
	@WebMethod
	public String sayHey(@WebParam(targetNamespace="http://testwebappws") String str){
		return "Hey "+str;
	}

	@Override
	@WebMethod
	public String getStr(@WebParam(targetNamespace="http://testwebappws") String str) {
		// TODO Auto-generated method stub
		return str;
	}

	@Override
	@WebMethod
	public String getCurrentTime() {
		// TODO Auto-generated method stub
		Date d = new Date();
		//System.out.println("Invoke getCurrentTime of TestWebService");
		return d.toString();
	}

	@Override
	@WebMethod
	public String getStr1(@WebParam(targetNamespace="http://testwebappws") String str1, @WebParam(targetNamespace="http://testwebappws") String str2) {
		// TODO Auto-generated method stub
		return str1+" "+str2;
	}

	@Override
	@SuppressWarnings("finally")
	@WebMethod
	public String callDB(@WebParam(targetNamespace="http://testwebappws") String dbType) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		//String result = null;
		try {
			DSService dss = new DSService(dbType);
			String[][] ret = dss.getDBTablesPS();
			result.append("Connected to data source "+dss.getDataSourceJNDIName()+"<br> executed SQL: "+ dss.getSQLPS()+"<br>");
			int rows = ret.length;
			int columns = ret[0].length;
			result.append("<br>");
			result.append("<table>");
			for(int i=0; i<rows; i++){
				result.append("<tr>");
				for(int j=0; j<columns; j++){
					result.append("<td>");
					result.append(ret[i][j]);
					result.append("</td>");
				}
				result.append("</tr>");
			}
			result.append("</table>");
			result.append("<br>");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            result = new StringBuffer();
			result.append(sw.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            result = new StringBuffer();
            result.append(sw.toString());
		}
		finally{
			return result.toString();
		}
	}

	@Override
	@WebMethod
	public String sendMessageToEQueue(@WebParam(targetNamespace="http://testwebappws")  String message) {
		// TODO Auto-generated method stub
		LocalMessageProducerService lmps = new LocalMessageProducerService("queue");
		String result = "Successfully published message '"+message+"' to Queue "+lmps.getQueueJNDINameStr();
		try {
			lmps.produceMessage(message);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
			result = sw.toString();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
			result = sw.toString();
		}
		return result;
	}

	@Override
	@WebMethod
	public String publishMessageToETopic(@WebParam(targetNamespace="http://testwebappws") String message) {
		// TODO Auto-generated method stub

		LocalMessageProducerService lmps = new LocalMessageProducerService("topic");
		String result = "Successfully published message '"+message+"' to Topic "+lmps.getTopicJNDINameStr();
		try {
			lmps.produceMessage(message);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
			result = sw.toString();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
			result = sw.toString();
		}
		return result;
	}

	@Override
	@SuppressWarnings("finally")
	@WebMethod
	public String callRemoteWS(@WebParam(targetNamespace="http://testwebappws") String methodName) {
		// TODO Auto-generated method stub
		URL wsdlURL = null;
		String wsdlURLStr = AppPropertiesConfig.getRemoteWebServiceEndpointAddress();
		String nameSpace = AppPropertiesConfig.getremoteWebServiceNameSpace();
		String mtdName = methodName;
		Object[] params = null;
		Object ret = null;
		Service service = new Service();
		Call call;
		StringBuffer result = new StringBuffer();;
		try {
			call = (Call) service.createCall();
			wsdlURL = new URL(wsdlURLStr);
			call.setTargetEndpointAddress(wsdlURL);
			call.setOperationName(new QName(nameSpace,mtdName));
			ret = call.invoke(params);

			result.append("<br>Successfully Invoked Remote web service<br>");
			result.append("Web Service Endpoint: "+wsdlURLStr);
			result.append("<br>");
			result.append("Invoked WebMethod '"+mtdName+"'");
			result.append("<br>");
			result.append("Return: "+ret.toString());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            result = new StringBuffer();
			result.append(sw.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            result = new StringBuffer();
			result.append(sw.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            result = new StringBuffer();
			result.append(sw.toString());
		}
		finally{
			return result.toString();
		}
	}



}
