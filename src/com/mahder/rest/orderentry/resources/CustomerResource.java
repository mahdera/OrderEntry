/**
 * 
 */
package com.mahder.rest.orderentry.resources;

import java.sql.ResultSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.mahder.rest.orderentry.dao.CustomerDao;
import com.mahder.rest.orderentry.model.Customer;
import com.mahder.util.ToJSON;

/**
 * @author alemayehu
 *
 */
@Path("/customer")
public class CustomerResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public String getAllCustomers(){
		String returnString = null;
		CustomerDao customerDao = new CustomerDao();
		ResultSet rSet = customerDao.getAllCustomersResultSet();
		
		ToJSON toJSON = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		jsonArray = toJSON.toJSONArray(rSet);		
		returnString = jsonArray.toString();
		return returnString;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String getCustomer(@PathParam("id") int id){
		String returnString = null;
		CustomerDao customerDao = new CustomerDao();
		ResultSet rSet = customerDao.getCustomerResultSet(id);
		
		ToJSON toJSON = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		jsonArray = toJSON.toJSONArray(rSet);
		returnString = jsonArray.toString();
		return returnString;
	}
}//end class
