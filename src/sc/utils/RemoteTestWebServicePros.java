package sc.utils;

public class RemoteTestWebServicePros {

	public static String mTargetNamespace;
	
	public static String mService_url;
	
	static{
		mTargetNamespace = "http://tempuri.org/";
		mService_url = "http://10.30.146.97/TestDotNetWebApplication/TestWebService.asmx?wsdl";
	}

	public static String getmTargetNamespace() {
		return mTargetNamespace;
	}

	public static void setmTargetNamespace(String mTargetNamespace) {
		RemoteTestWebServicePros.mTargetNamespace = mTargetNamespace;
	}

	public static String getmService_url() {
		return mService_url;
	}

	public static void setmService_url(String mService_url) {
		RemoteTestWebServicePros.mService_url = mService_url;
	}
	
	
}
