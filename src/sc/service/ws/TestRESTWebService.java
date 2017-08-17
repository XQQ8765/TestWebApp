 
package sc.service.ws;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import com.sun.jersey.spi.container.servlet.ServletContainer;

@Path(value="/TestRESTWebService")
public class TestRESTWebService {
	/**
     * Default constructor. 
     */
    public TestRESTWebService() {
        // TODO Auto-generated constructor stub
    }


    /**
     * Retrieves representation of an instance of TestWebService
     * @return an instance of String
     */
	@GET
	@Produces("text/plain")
	public String resourceMethodGET() { 
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException();
		String ret = "Hello!";
		return ret;
	}

	/**
     * PUT method for updating or creating an instance of TestWebService
     * @content content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
	@PUT
	@Consumes("text/plain")
	public void resourceMethodPUT(String content) { 
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}