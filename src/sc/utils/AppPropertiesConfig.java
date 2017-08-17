package sc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

public class AppPropertiesConfig {

	private static Properties prop = new Properties();
	private static Properties tprop = new Properties();
	/*private static String EJBVersion;
	private static String ServletVersion;
	private static String topicConnectionFactoryJNDIName;
	private static String queueConnectionFactoryJNDIName;
	private static String embededQueueJNDIName;
	private static String embededTopicJNDIName;
	private static String dataSourceJNDIName;
	private static String remoteWebServiceEndpointAddress;
	private static String embeddedStatefulSessionBeanJNDIName;
	private static String embeddedStatelessSessionBeanJNDIName;
	private static String embeddedSingletonSessionBeanJNDIName;*/
	private static File appProsFile = new File("TestWebAppPros.properties");
	static{
		//sc.service.ws.Test t = new sc.service.ws.Test();
		//Endpoint endpoint = Endpoint.publish("http://localhost:9091/TestWebApp/test", t);

		try {
			tprop.load(AppPropertiesConfig.class.getClassLoader().getResourceAsStream("appProperties.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();

		//file.mkdir();


	}

	public AppPropertiesConfig(){

	}

	private static void outputProsToFile(){
		Date time = new Date();
		try {

			if(!appProsFile.exists())
			appProsFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(appProsFile);
			prop.store(fos, "Last Update "+time.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(appProsFile.toURI());
	}

	private static void loadProsFromFile(){
		if(appProsFile.exists()){
			try {
				FileInputStream fis = new FileInputStream(appProsFile);
				prop.load(fis);
				fis.close();
				if(!checkPros()){
					appProsFile.delete();
					prop.clear();
					init();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static boolean checkPros(){
		Set<String> ppns = prop.stringPropertyNames();
		Set<String> tppns = tprop.stringPropertyNames();
		return ppns.containsAll(tppns);
	}

	public static void init(){
		try {
			//prop.load(new FileInputStream("appProperties.properties"));
			//prop.clear();
			if(!appProsFile.exists()){
				prop.load(AppPropertiesConfig.class.getClassLoader().getResourceAsStream("appProperties.properties"));

				outputProsToFile();
			}
			else{
				loadProsFromFile();
			}
			//System.out.println(remoteWebServiceEndpointAddress);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void update(){
		Date time = new Date();
		try {
			File f;
			String fileURI = AppPropertiesConfig.class.getClassLoader().getResource("appProperties.properties").toURI().toString();
			//System.out.println("AppProperties.properties URI: "+fileURI.toString());
			if(!fileURI.startsWith("zip")){
				if(fileURI.startsWith("vfs")){
					org.jboss.vfs.VirtualFile vFile = org.jboss.vfs.VFS.getChild(AppPropertiesConfig.class.getClassLoader().getResource("appProperties.properties").toURI());
					//org.jboss.vfs.VirtualFile vFile = new org.jboss.vfs.VirtualFile();
					//fileURI = "file"+ fileURI.substring(3);
					URI fileNameDecodedTmp = org.jboss.vfs.VFSUtils.getPhysicalURI(vFile);
					fileURI = fileNameDecodedTmp.getPath();
					f = new File(fileURI);
				}
				else{
						f = new File(AppPropertiesConfig.class.getClassLoader().getResource("appProperties.properties").toURI());
				}
				FileOutputStream fos = new FileOutputStream(f);
				prop.store(fos, "Last Update "+time.toString());
				fos.close();
			}
			outputProsToFile();
			//init();
			//t = AppPropertiesConfig.class.getClassLoader().getResource("appProperties.properties").toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static void updateProperty(String propertyName, String value){
		prop.setProperty(propertyName, value);
		update();
	}

	public static String getProperty(String propertyName){
		return prop.getProperty(propertyName);

	}

	public static String getTopicConnectionFactoryJNDIName() {
		return prop.getProperty("topicConnectionFactoryJNDIName");
	}

	public static void setTopicConnectionFactoryJNDIName(
			String topicConnectionFactoryJNDIName) {
		prop.setProperty("topicConnectionFactoryJNDIName", topicConnectionFactoryJNDIName);
	}

	public static String getQueueConnectionFactoryJNDIName() {
		return prop.getProperty("queueConnectionFactoryJNDIName");
	}

	public static void setQueueConnectionFactoryJNDIName(
			String queueConnectionFactoryJNDIName) {
		prop.setProperty("queueConnectionFactoryJNDIName", queueConnectionFactoryJNDIName);
	}

	public static String getEmbededQueueJNDIName() {
		return prop.getProperty("embededQueueJNDIName");
	}

	public static void setEmbededQueueJNDIName(String embededQueueJNDIName) {
		prop.setProperty("embededQueueJNDIName", embededQueueJNDIName);
	}

	public static String getEmbededTopicJNDIName() {
		return prop.getProperty("embededTopicJNDIName");
	}

	public static void setEmbededTopicJNDIName(String embededTopicJNDIName) {
		prop.setProperty("embededTopicJNDIName", embededTopicJNDIName);
	}

	public static String getDataSourceJNDIName() {
		return prop.getProperty("dataSourceJNDIName");
	}

	public static void setDataSourceJNDIName(String dataSourceJNDIName) {
		prop.setProperty("dataSourceJNDIName", dataSourceJNDIName);
	}

	public static String getRemoteWebServiceEndpointAddress() {
		return prop.getProperty("remoteWebServiceEndpointAddress");
	}

	public static void setRemoteWebServiceEndpointAddress(
			String remoteWebServiceEndpointAddress) {
		prop.setProperty("remoteWebServiceEndpointAddress", remoteWebServiceEndpointAddress);
	}

	public static String getEmbeddedStatefulSessionBeanJNDIName() {
		return prop.getProperty("embeddedStatefulSessionBeanJNDIName");
	}

	public static void setEmbeddedStatefulSessionBeanJNDIName(
			String embeddedStatefulSessionBeanJNDIName) {
		prop.setProperty("embeddedStatefulSessionBeanJNDIName", embeddedStatefulSessionBeanJNDIName);
	}

	public static String getEmbeddedStatelessSessionBeanJNDIName() {
		return prop.getProperty("embeddedStatelessSessionBeanJNDIName");
	}

	public static void setEmbeddedStatelessSessionBeanJNDIName(
			String embeddedStatelessSessionBeanJNDIName) {
		prop.setProperty("embeddedStatelessSessionBeanJNDIName", embeddedStatelessSessionBeanJNDIName);
	}

	public static String getEmbeddedSingletonSessionBeanJNDIName() {
		return prop.getProperty("embeddedSingletonSessionBeanJNDIName");
	}

	public static void setEmbeddedSingletonSessionBeanJNDIName(
			String embeddedSingletonSessionBeanJNDIName) {
		prop.setProperty("embeddedSingletonSessionBeanJNDIName", embeddedSingletonSessionBeanJNDIName);
	}

	public static String getEJBVersion() {
		return prop.getProperty("EJBVersion");
	}

	public static String getServletVersion() {
		return prop.getProperty("ServletVersion");
	}

	public static String getremoteWebServiceNameSpace() {
		return prop.getProperty("remoteWebServiceNameSpace");
	}

	public static void setRemoteWebServiceNameSpace(
			String remoteWebServiceNameSpace) {
		prop.setProperty("remoteWebServiceNameSpace", remoteWebServiceNameSpace);
	}
}
