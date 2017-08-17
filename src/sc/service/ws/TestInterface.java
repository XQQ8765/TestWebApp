package sc.service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace="http://testwebappws")
//@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TestInterface {
	@WebMethod
	public String sayHey(@WebParam(targetNamespace="http://testwebappws") String str);
	@WebMethod
	public String getStr(@WebParam(targetNamespace="http://testwebappws") String str);
	@WebMethod
	public String callDB(@WebParam(targetNamespace="http://testwebappws") String dbType);
	@WebMethod
	public String sendMessageToEQueue(@WebParam(targetNamespace="http://testwebappws") String message);
	@WebMethod
	public String publishMessageToETopic(@WebParam(targetNamespace="http://testwebappws") String message);
	@WebMethod
	public String callRemoteWS(@WebParam(targetNamespace="http://testwebappws") String methodName);
	@WebMethod
	public String getCurrentTime();
	@WebMethod
	public String getStr1(@WebParam(targetNamespace="http://testwebappws") String str1, @WebParam(targetNamespace="http://testwebappws") String str2);
}
