/**
 * 
 */
package com.mahder.rest.orderentry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mahder.rest.orderentry.datasource.MySQLConnection;
import com.mahder.rest.orderentry.model.Customer;

/**
 * @author alemayehu
 *
 */
public class CustomerDao {
	public void save(Customer customer) {
		try {			
			String sqlStr = "insert into tbl_customer values(?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, 0);
			pStmt.setString(2, customer.getFirstName());
			pStmt.setString(3, customer.getLastName());
			pStmt.setString(4, customer.getStreet());
			pStmt.setString(5, customer.getCity());
			pStmt.setString(6, customer.getState());
			pStmt.setString(7, customer.getZip());
			pStmt.setString(8, customer.getCountry());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void update(Customer customer) {
		try {
			String sqlStr = "update tbl_customer set first_name = ?, last_name = ?, street = ?, city = ?, state = ?, zip = ?, country = ? where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setString(1, customer.getFirstName());
			pStmt.setString(2, customer.getLastName());
			pStmt.setString(3, customer.getStreet());
			pStmt.setString(4, customer.getCity());
			pStmt.setString(5, customer.getState());
			pStmt.setString(6, customer.getZip());
			pStmt.setString(7, customer.getCountry());
			pStmt.setInt(8, customer.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void delete(Customer customer) {
		try {
			String sqlStr = "delete from tbl_customer where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, customer.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}
	
	public ResultSet getAllCustomersResultSet(){
		ResultSet rSet = null;
		try{
			String sqlStr = "select * from tbl_customer order by first_name, last_name";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			rSet = MySQLConnection.readFromDatabase(pStmt);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//MySQLConnection.disconnectDatabase();
		}
		return rSet;
	}
	
	public ResultSet getCustomerResultSet(int id){
		ResultSet rSet = null;
		try{
			String sqlStr = "select * from tbl_customer where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, id);
			rSet = MySQLConnection.readFromDatabase(pStmt);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rSet;
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customerList = new ArrayList<Customer>();
		Customer customer = null;
		try {
			String sqlStr = "select * from tbl_customer order by first_name, last_name";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				customer = new Customer();
				customer.setId(rSet.getInt("id"));
				customer.setFirstName(rSet.getString("first_name"));
				customer.setLastName(rSet.getString("last_name"));
				customer.setCity(rSet.getString("city"));
				customer.setState(rSet.getString("state"));
				customer.setStreet(rSet.getString("street"));
				customer.setZip(rSet.getString("zip"));
				customer.setCountry(rSet.getString("country"));
				customerList.add(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return customerList;
	}

	public Customer getCustomer(int id) {
		Customer customer = null;
		try {
			String sqlStr = "select * from tbl_customer where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, id);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				customer = new Customer();
				customer.setId(rSet.getInt("id"));
				customer.setFirstName(rSet.getString("first_name"));
				customer.setLastName(rSet.getString("last_name"));
				customer.setCity(rSet.getString("city"));
				customer.setState(rSet.getString("state"));
				customer.setStreet(rSet.getString("street"));
				customer.setZip(rSet.getString("zip"));
				customer.setCountry(rSet.getString("country"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return customer;
	}
}// end class
