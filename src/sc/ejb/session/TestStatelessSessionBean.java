package sc.ejb.session;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class TestStatelessSessionBean
 */
@Stateless(mappedName = "TestStatelessSessionBean")
@LocalBean
@Remote(TestStatelessSessionBeanRemote.class)
public class TestStatelessSessionBean extends BaseSessionBean implements TestStatelessSessionBeanRemote {

	//private Date date = new Date();

	@EJB
	TestStatefulSessionBean testStatefulSessionBean;
    /**
     * Default constructor.
     */
    public TestStatelessSessionBean() {
        // TODO Auto-generated constructor stub
    }


    //@Schedule(dayOfWeek="*",hour="*")
    //public void showCurrentTime(){
    	//System.out.println("Session Bean timer triggered, showCurrentTime:"+date.toString());
    	//testStatefulSessionBean.showCurrentTime();
    //}

    @Asynchronous
    public void testAsynchronous(){

    }


	@Override
	public String getStr(String s) {
		// TODO Auto-generated method stub
		return s;
	}
}
