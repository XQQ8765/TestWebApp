package sc.test;




public class Test {

	public static void testMethod(){
		sc.ejb.session.TestStatelessSessionBean.class.getMethods();
		Class clazz = null;
		Object o = new Object();
		//(clazz)o;
	}

	/*public static void main(String[] args){
		Properties props = new Properties();


		//JBoss 7.1.1 properties
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "remote://10.154.10.193:4447");
        props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put(Context.SECURITY_PRINCIPAL, "testuser");
        props.put(Context.SECURITY_CREDENTIALS, "rdisfun");
        props.put("jboss.naming.client.ejb.context", false);
        //props.put("jboss.naming.client.ejb.context", true);
		try {
			InitialContext ctx = new InitialContext(props);
			//System.out.println(ctx.lookup("ejb")+"aa");
			TestStatelessSessionBeanRemote bean = (TestStatelessSessionBeanRemote)ctx.lookup("TestWebApp/TestStatelessSessionBean!sc.ejb.session.TestStatelessSessionBeanRemote");

			//ctx.lookup("ejb:/TestWebApp/TestStatefulSessionBean!sc.ejb.session.TestStatefulSessionBeanRemote");
			System.out.println(bean.getStr("test"));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
