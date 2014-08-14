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

import com.mahder.rest.orderentry.dao.ProductDao;
import com.mahder.rest.orderentry.model.Product;
import com.mahder.util.ToJSON;

/**
 * @author alemayehu
 *
 */

@Path("/product")
public class ProductResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getAllProducts(){
		String returnString = null;
		ResultSet rSet = null;
		ProductDao productDao = new ProductDao();
		rSet = productDao.getAllProductsResultSet();
		
		ToJSON toJSON = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		jsonArray = toJSON.toJSONArray(rSet);
		if(jsonArray.length() != 0){
			returnString = jsonArray.toString();
		}else{
			returnString = "Empty result set";
		}
		return Response.ok(returnString).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	@Path("/get/{id}")
	public Response getProduct(@PathParam("id") int id){
		String returnString = null;
		ResultSet rSet = null;
		ProductDao productDao = new ProductDao();
		rSet = productDao.getProductResultSet(id);
		
		ToJSON toJSON = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		jsonArray = toJSON.toJSONArray(rSet);
		if(jsonArray.length() != 0){
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
	public Response saveProduct(String incomingData){
		String returnString = null;
		Product product = null;
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			product = objectMapper.readValue(incomingData, Product.class);
			if(product != null){
				ProductDao productDao = new ProductDao();
				productDao.save(product);
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("The server has encountered an error while processing your request").build();
		}
		return Response.ok(returnString).build();
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response updateProduct(@PathParam("id") int id, String incomingData){
		String returnString = null;
		Product existingProduct = null, product = null;
		ProductDao productDao = new ProductDao();
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			product = objectMapper.readValue(incomingData, Product.class);
			existingProduct = productDao.getProduct(id);
			if(existingProduct != null){
				existingProduct.setName(product.getName());
				existingProduct.setCost(product.getCost());
				productDao.update(existingProduct);
				returnString = "Product updated successfully!";
			}else{
				return Response.status(404).entity("Product object not found!").build();
			}
		}catch(Exception e){
			e.printStackTrace();
			Response.status(500).entity("The server has encountered an error while trying to execute your request!").build();
		}
		return Response.ok(returnString).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response deleteProduct(@PathParam("id") int id){
		String returnString = null;
		Product product = null;
		ProductDao productDao = new ProductDao();
		product = productDao.getProduct(id);
		if(product != null){
			productDao.delete(product);
			returnString = "Product deleted successfully!";
		}else{
			return Response.status(404).entity("Product not found!").build();
		}
		return Response.ok(returnString).build();
	}
}//end class
