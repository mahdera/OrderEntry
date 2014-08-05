package com.restfully.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.restfully.shop.domain.Customer;


/**
 * @author alemayehu
 *
 */
@Path("/customers")
public class CustomerResource {
	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	private AtomicInteger idCounter = new AtomicInteger();
	
	@POST
	@Consumes("application/xml")
	public Response createCustomer(InputStream is){
		Customer customer = readCustomer(is);
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(), customer);
		System.out.println("Created customer "+customer.getId());
		return Response.created(URI.create("/customers/"+customer.getId())).build();
	}

	private Customer readCustomer(InputStream is) {
		// TODO Auto-generated method stub
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			
			Customer cust = new Customer();
			if(root.getAttribute("id") != null && !root.getAttribute("id").trim().equals("")){
				cust.setId(Integer.valueOf(root.getAttribute("id")));
			}
			NodeList nodes = root.getChildNodes();
			for(int i=0; i<nodes.getLength(); i++){
				Element element = (Element) nodes.item(i);
				if(element.getTagName().equals("first-name")){
					cust.setFirstName(element.getTextContent());
				}else if(element.getTagName().equals("last-name")){
					cust.setLastName(element.getTextContent());
				}else if(element.getTagName().equals("street")){
					cust.setStreet(element.getTextContent());
				}else if(element.getTagName().equals("state")){
					cust.setState(element.getTextContent());
				}else if(element.getTagName().equals("city")){
					cust.setCity(element.getTextContent());
				}else if(element.getTagName().equals("zip")){
					cust.setZip(element.getTextContent());
				}else if(element.getTagName().equals("country")){
					cust.setCountry(element.getTextContent());
				}
			}//end for...loop
			return cust;
		}catch(Exception e){
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public StreamingOutput getCustomer(@PathParam("id") int id){
		final Customer customer = customerDB.get(id);
		if(customer == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput(){
			public void write(OutputStream outputStream) throws IOException, WebApplicationException{
				outputCustomer(outputStream, customer);
			}			
		};
	}
	
	private void outputCustomer(OutputStream outputStream,
			Customer customer) throws IOException{
		// TODO Auto-generated method stub
		PrintStream writer = new PrintStream(outputStream);
		writer.println("<customer id=\""+customer.getId()+"\">");
		writer.println("<first-name>"+customer.getFirstName()+"</first-name>");
		writer.println("<last-name>"+customer.getLastName()+"</last-name>");
		writer.println("<street>"+customer.getStreet()+"</street>");
		writer.println("<city>"+customer.getCity()+"</city>");
		writer.println("<state>"+customer.getState()+"</state>");
		writer.println("<zip>"+customer.getZip()+"</zip");
		writer.println("<country>"+customer.getCountry()+"</country>");
		writer.print("</customer>");
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id, InputStream is){
		Customer update = readCustomer(is);
		Customer current = customerDB.get(id);
		if(current == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);//http 404
		
		current.setFirstName(update.getFirstName());
		current.setLastName(update.getLastName());
		current.setStreet(update.getStreet());
		current.setState(update.getState());
		current.setCity(update.getCity());
		current.setZip(update.getZip());
		current.setCountry(update.getCountry());
	}
	
}//end class
