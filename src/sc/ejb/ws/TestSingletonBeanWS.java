package sc.ejb.ws;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
/**
 * Session Bean implementation class TestSingletonBeanWS
 */
@Singleton
@LocalBean
@WebService(name = "TestSingletonBeanWS", serviceName="TestSingletonBeanWS", targetNamespace = "http://testwebappejbws")
public class TestSingletonBeanWS {
	private String state;
    /**
     * Default constructor. 
     */
    public TestSingletonBeanWS() {
        // TODO Auto-generated constructor stub
    	
    }

    @WebMethod
	public String getCurrentTime() {
		// TODO Auto-generated method stub
		Date d = new Date();
		//System.out.println("Invoke getCurrentTime of TestWebService");
		return d.toString();
	}
    
    @WebMethod
    @Lock(LockType.READ) 
    public String getState() { 
    	return state; 
    } 
     
    @WebMethod
    @Lock(LockType.WRITE) 
    @AccessTimeout(10000) 
    public void setState(String newState) { 
    	state = newState; 
    } 

    @PostConstruct  
    public void initByeEJB() {   
        System.out.println("TestSingletonBeanWS is being initialized...");   
         
    }  
    
    @PreDestroy  
    public void destroyByeEJB() {   
    	System.out.println("TestSingletonBeanWS is being destroyed...");   

    } 
    
}
