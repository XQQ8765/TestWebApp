package sc.servlet.async;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Method;

//import javax.annotation.ManagedBean;
//import javax.ejb.EJB;
//import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.servlet.AsyncContext;

import sc.ejb.session.TestSingletonSessionBean;
import sc.ejb.session.TestStatefulSessionBean;
import sc.ejb.session.TestStatelessSessionBean;

//@ManagedBean
//@ApplicationScoped
public class InvokeEmbeddedSessionBeanExecutor implements Runnable {

	private AsyncContext ctx = null;
	private String sessionBeanJNDINameStr = null;
	private PrintWriter out;
	private String beanType = null;
	/*@EJB
    TestSingletonSessionBean testSingletonSessionBean;
    @EJB
    TestStatefulSessionBean testStatefulSessionBean;
    @EJB
    TestStatelessSessionBean testStatelessSessionBean;*/
    
    //public InvokeEmbeddedSessionBeanExecutor(){
    	
    //}
    
    
	 public InvokeEmbeddedSessionBeanExecutor(AsyncContext ctx, String sessionBeanJNDINameStr, String beanType){
	        this.ctx = ctx;
	        this.sessionBeanJNDINameStr = sessionBeanJNDINameStr;
	        this.beanType = beanType;
	        //System.out.println("sessionBeanJNDINameStr: "+ sessionBeanJNDINameStr);
	        try {
				this.out = ctx.getResponse().getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	 }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("InvokeEmbeddedSessionBeanExecutor started");
		/*if(beanType.equalsIgnoreCase("stateful")){
			this.invokeEmbeddedStateful();
		}
		else if(beanType.equalsIgnoreCase("stateless")){
			this.invokeEmbeddedStateless();
		}else if(beanType.equalsIgnoreCase("singleton")){
			this.invokeEmbeddedSingleton();
		}*/
		this.invokeEmbeddedSessionBean();
		
		out.flush();
		ctx.complete();
		//System.out.println("InvokeEmbeddedSessionBeanExecutor ended");
	}

	private void invokeEmbeddedSessionBean(){
		try {
			InitialContext ctx = new InitialContext();
			Object bean  =  ctx.lookup(sessionBeanJNDINameStr);
			Method m = bean.getClass().getMethod("getStr", String.class);
			String str = "Test";
			out.println("Invoked "+beanType+" Session Bean "+sessionBeanJNDINameStr+" Successfully, invoked bean method getStr: "+ m.invoke(bean, str)+"<br><br>");
			

		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke "+beanType+" Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
		
			//e.printStackTrace();
			e.printStackTrace(out);
		}
	}
	
	private void invokeEmbeddedStateful(){
		
		try {
			InitialContext ctx = new InitialContext();
			TestStatefulSessionBean testStatefulSessionBean = 
			(TestStatefulSessionBean)ctx.lookup(sessionBeanJNDINameStr);
			out.println("Invoked TestStatefulSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testStatefulSessionBean.getStr("Test")+"<br><br>");
			

		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Stateful Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
		
			e.printStackTrace();
			//e.printStackTrace(out);
		}
	}
	
	private void invokeEmbeddedStateless(){

		try {
			InitialContext ctx = new InitialContext();
			TestStatelessSessionBean testStatelessSessionBean = 
			(TestStatelessSessionBean)ctx.lookup(sessionBeanJNDINameStr);
			out.println("Invoked TestStatelessSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testStatelessSessionBean.getStr("Test")+"<br><br>");
		

		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Stateless Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
		
			e.printStackTrace(out);
		}
	}
	
	private void invokeEmbeddedSingleton(){

		try {
			InitialContext ctx = new InitialContext();
			TestSingletonSessionBean testSingletonSessionBean = 
			(TestSingletonSessionBean)ctx.lookup(sessionBeanJNDINameStr);
			out.println("Invoked TestSingletonSessionBean "+sessionBeanJNDINameStr+" Successfully, getStr: "+ testSingletonSessionBean.getStr("Test")+"<br><br>");
	

		}catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("Failed to invoke Singleton Session Bean "+sessionBeanJNDINameStr+"<br>");
			out.println("<br>"+ e.toString()+"<br>");
		

			e.printStackTrace(out);
		}
	}
	
}
