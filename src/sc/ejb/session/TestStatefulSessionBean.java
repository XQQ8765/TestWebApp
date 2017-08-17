package sc.ejb.session;

import java.util.Date;

import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Asynchronous;
import javax.ejb.BeforeCompletion;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;


/**
 * Session Bean implementation class TestStatefulSessionBean
 */
@Stateful(mappedName = "TestStatefulSessionBean")
@LocalBean
@Remote(TestStatefulSessionBeanRemote.class)
public class TestStatefulSessionBean extends BaseSessionBean implements TestStatefulSessionBeanRemote{
	
	
    /**
     * Default constructor. 
     */
    public TestStatefulSessionBean() {
        // TODO Auto-generated constructor stub
    }

    
    public void showCurrentTime(){
    	Date date = new Date();
    	System.out.println(date.toString());
    }
    
    @Asynchronous
    public void testAsynchronous(){
    	
    }
    
    @AfterBegin
    public void afterBegin(){
    	System.out.println("TestStatefulSessionBean began");
    }
    
    @AfterCompletion
    public void afterCompletion(boolean committed){
    	
    }
    
    @BeforeCompletion
    public void beforeCompletion(){
    	
    }
    
    @Remove
    public void remove() {
        
    }


	@Override
	public String getStr(String s) {
		// TODO Auto-generated method stub
		return s;
	}
}
