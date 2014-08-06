/**
 * 
 */
package com.mahder.rest.orderentry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mahder.rest.orderentry.datasource.MySQLConnection;
import com.mahder.rest.orderentry.model.Order;

/**
 * @author alemayehu
 *
 */
public class OrderDao {
	public void save(Order order) {
		try {
			String sqlStr = "insert into tbl_order values(?,?,?,?,?)";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, 0);
			pStmt.setFloat(2, order.getTotal());
			pStmt.setDate(3, order.getDate());
			pStmt.setInt(4,order.getCustomerId());
			pStmt.setInt(5, order.getLineItemId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void delete(Order order) {
		try {
			String sqlStr = "delete from tbl_order where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, order.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void update(Order order) {
		try {
			String sqlStr = "update tbl_order set total = ?, order_date = ?, customer_id = ?, line_item_id = ? where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setFloat(1, order.getTotal());
			pStmt.setDate(2, order.getDate());
			pStmt.setInt(3, order.getCustomerId());
			pStmt.setInt(4, order.getLineItemId());
			pStmt.setInt(5, order.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<Order>();
		Order order = null;
		try {
			String sqlStr = "select * from tbl_order by order_date desc";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				order = new Order();
				order.setId(rSet.getInt("id"));
				order.setTotal(rSet.getFloat("total"));
				order.setDate(rSet.getDate("order_date"));
				order.setCustomerId(rSet.getInt("customer_id"));
				order.setLineItemId(rSet.getInt("line_item_id"));
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return orderList;
	}
	
	public ResultSet getAllOrdersResultSet(){
		ResultSet rSet = null;
		try{
			String sqlStr = "select * from tbl_order order by order_date desc";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			rSet = MySQLConnection.readFromDatabase(pStmt);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return rSet;
	}

	public Order getOrder(int id) {
		Order order = null;
		try {
			String sqlStr = "select * from tbl_order where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, id);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				order = new Order();
				order.setId(rSet.getInt("id"));
				order.setTotal(rSet.getFloat("total"));
				order.setDate(rSet.getDate("order_date"));
				order.setCustomerId(rSet.getInt("customer_id"));
				order.setLineItemId(rSet.getInt("line_item_id"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return order;

	}
}// end class
