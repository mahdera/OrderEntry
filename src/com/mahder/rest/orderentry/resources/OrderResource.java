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

import com.mahder.rest.orderentry.dao.OrderDao;
import com.mahder.rest.orderentry.model.Order;
import com.mahder.util.ToJSON;

/**
 * @author alemayehu
 *
 */
@Path("/order")
public class OrderResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getAllOrders(){		
		OrderDao orderDao = new OrderDao();
		ResultSet rSet = null;
		String returnString = null;
		rSet = orderDao.getAllOrdersResultSet();
		
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
	public Response getOrder(@PathParam("id") int id){
		OrderDao orderDao = new OrderDao();
		ResultSet rSet = null;
		String returnString = null;
		rSet = orderDao.getOrderResultSet(id);
		
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
	public Response saveOrder(String incomingData){
		String returnString = null;
		Order order = null;
		try{
			OrderDao orderDao = new OrderDao();
			ObjectMapper objectMapper = new ObjectMapper();
			order = objectMapper.readValue(incomingData, Order.class);
			orderDao.save(order);
			returnString = "Order object saved to the database...";
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server encountered error while processing your request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response updateOrder(@PathParam("id") int id, String incomingData){
		String returnString = null;
		Order order = null, existingOrder = null;
		try{
			OrderDao orderDao = new OrderDao();
			ObjectMapper objectMapper = new ObjectMapper();
			order = objectMapper.readValue(incomingData, Order.class);
			if(order != null){
				existingOrder = orderDao.getOrder(id);
				existingOrder.setDate(order.getDate());
				existingOrder.setCustomerId(order.getCustomerId());
				existingOrder.setLineItemId(order.getLineItemId());
				existingOrder.setTotal(order.getTotal());
				orderDao.update(existingOrder);
				returnString = "Order object updated successfully!";
			}else{
				return Response.status(404).entity("Order object not found!").build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server encountered problem while processing your request").build();
		}
		return Response.ok(returnString).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response deleteOrder(@PathParam("id") int id){
		String returnString = null;
		Order order = null;
		OrderDao orderDao = new OrderDao();
		order = orderDao.getOrder(id);
		if(order != null){
			orderDao.delete(order);
			returnString = "Order object deleted successfully!";			
		}else{
			return Response.status(404).entity("Could not find the delete end point(resource)").build();
		}
		return Response.ok(returnString).build();
	}
}//end class
