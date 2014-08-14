/**
 * 
 */
package com.mahder.rest.orderentry.resources;

import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.mahder.rest.orderentry.dao.LineItemDao;
import com.mahder.rest.orderentry.model.LineItem;
import com.mahder.util.ToJSON;

/**
 * @author alemayehu
 *
 */
@Path("/lineitem")
public class LineItemResource {
	
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Response saveLineItem(String incomingData){
		String returnString = null;
		LineItem lineItem = null;
		try{
			LineItemDao lineItemDao = new LineItemDao();
			ObjectMapper objectMapper = new ObjectMapper();
			lineItem = objectMapper.readValue(incomingData, LineItem.class);
			lineItemDao.save(lineItem);
			returnString = "Lineitem data saved successfully!";			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("The server encountered error while proccessing the request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getAllLineItems(){
		String returnString = null;
		LineItemDao lineItemDao = new LineItemDao();
		ResultSet rSet = lineItemDao.getAllLineItemsResultSet();
		
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/{id}")
	public Response getLineItem(@PathParam("id") int id){
		String returnString = null;
		LineItemDao lineItemDao = new LineItemDao();
		ResultSet rSet = lineItemDao.getLineItemResultSet(id);
		
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
	
	@PUT
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response updateLineItem(@PathParam("id") int id, String incomingData){
		LineItem lineItem = null, existingLineItem = null;
		String returnString = null;
		try{
			LineItemDao lineItemDao = new LineItemDao();
			ObjectMapper objectMapper = new ObjectMapper();			
			lineItem = objectMapper.readValue(incomingData, LineItem.class);
			existingLineItem = lineItemDao.getLineItem(id);
			if(existingLineItem != null){
				existingLineItem.setProductId(lineItem.getProductId());
				existingLineItem.setQuantity(lineItem.getQuantity());
				lineItemDao.update(lineItem);
				returnString = "Lineitem object updated successfully!";
			}else{
				return Response.status(404).entity("Lineitem object not found!").build();
			}						
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Error encountered while processing your request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response deleteLineItem(@PathParam("id") int id){
		String returnString = null;
		LineItem lineItem = null;
		LineItemDao lineItemDao = new LineItemDao();
		lineItem = lineItemDao.getLineItem(id);
		if(lineItem != null){
			lineItemDao.delete(lineItem);
			returnString = "Lineitem deleted successfully!";
		}else{
			return Response.status(404).entity("Lineitem object not found!").build();
		}		
		return Response.ok(returnString).build();
	}
	
}//end class
