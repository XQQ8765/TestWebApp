package sc.ejb.session;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;


/**
 * Session Bean implementation class TestSingletonSessionBean
 */
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER) 
@Singleton
@LocalBean

public class TestSingletonSessionBean extends BaseSessionBean{
	private String state;
	
	@Resource  
	SessionContext ctx;  
    /**
     * Default constructor. 
     */
    public TestSingletonSessionBean() {
        // TODO Auto-generated constructor stub
    	System.out.println("TestSingletonSessionBean initiated");
    }
    
    
    @Lock(LockType.READ) 
    public String getState() { 
    	return state; 
    } 
     
    @Lock(LockType.WRITE) 
    @AccessTimeout(10000) 
    public void setState(String newState) { 
    	state = newState; 
    } 

    @Asynchronous
    public void getDSTables(){
    	
    }
    
    
    @PostConstruct  
    public void initByeEJB() {   
        System.out.println("TestSingletonSessionBean is being initialized...");   
         
    }  
    
    @PreDestroy  
    public void destroyByeEJB() {   
    	System.out.println("TestSingletonSessionBean is being destroyed...");   

    } 
}
