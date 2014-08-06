/**
 * 
 */
package com.mahder.rest.orderentry.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author alemayehu
 *
 */
@Path("/customer")
public class CustomerResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getStringCustomer(){
		return "<p>This method should return a string value!</p>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/version")
	public String getStringVersion(){
		return "<p>This is a version method...</p>";
	}
}//end class
