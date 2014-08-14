/**
 * 
 */
package com.mahder.rest.orderentry.resources;

import java.sql.ResultSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
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
		
		if(jsonArray.length() != 0){
			returnString = jsonArray.toString();
		}else{
			returnString = "Empty result set";
		}
		//return Response.ok(returnString).build();
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/{id}")
	public Response getCustomer(@PathParam("id") int id){
		String returnString = null;
		CustomerDao customerDao = new CustomerDao();
		ResultSet rSet = customerDao.getCustomerResultSet(id);
		
		ToJSON toJSON = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		if(jsonArray.length() != 0){
			jsonArray = toJSON.toJSONArray(rSet);
			returnString = jsonArray.toString();
		}else{
			returnString = "Empty result set";
		}
		return Response.ok(returnString).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Response saveCustomer(String incomingData){
		//System.out.println(incomingData);
		Customer customer = new Customer();
		String returnString = null;
		
		try{
			CustomerDao customerDao = new CustomerDao();
			ObjectMapper objectMapper = new ObjectMapper();
			customer = objectMapper.readValue(incomingData, Customer.class);
			if(customer != null){
				customerDao.save(customer);
				returnString = "Customer saved successfully!";
			}else{
				returnString = "Please enter all required data values...";
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("The server was unable to process your request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response updateCustomer(@PathParam("id") int id, String incomingData){		
		Customer customer = null, existingCustomer = null;
		String returnString = null;
		
		try{
			ObjectMapper objectMapper = new ObjectMapper();			
			customer = objectMapper.readValue(incomingData, Customer.class);
			CustomerDao customerDao = new CustomerDao();
			existingCustomer = customerDao.getCustomer(id);
			if(existingCustomer != null){
				existingCustomer.setFirstName(customer.getFirstName());
				existingCustomer.setLastName(customer.getLastName());
				existingCustomer.setCountry(customer.getCountry());
				existingCustomer.setState(customer.getState());
				existingCustomer.setCity(customer.getCity());
				existingCustomer.setZip(customer.getZip());
				existingCustomer.setStreet(customer.getStreet());
				
				customerDao.update(existingCustomer);			
				returnString = "Customer updated Successfully!";
			}else{
				return Response.status(404).entity("Customer object not found").build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("The server was unable to process your request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response deleteCustomer(@PathParam("id") int id){
		String returnString = null;
		CustomerDao customerDao = new CustomerDao();
		Customer customer = customerDao.getCustomer(id);
		if(customer != null){
			customerDao.delete(customer);
			returnString = "Customer deleted successfully!";
		}else{
			return Response.status(404).entity("Customer object could not be found!").build();
		}		
		return Response.ok(returnString).build();
	}
	
}//end class
