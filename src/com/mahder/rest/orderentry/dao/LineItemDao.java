/**
 * 
 */
package com.mahder.rest.orderentry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mahder.rest.orderentry.datasource.MySQLConnection;
import com.mahder.rest.orderentry.model.LineItem;

/**
 * @author alemayehu
 *
 */
public class LineItemDao {
	public void save(LineItem lineItem) {
		try {
			String sqlStr = "insert into tbl_line_item values(?,?,?)";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, 0);
			pStmt.setInt(2, lineItem.getQuantity());
			pStmt.setInt(3, lineItem.getProductId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void update(LineItem lineItem) {
		try {
			String sqlStr = "update tbl_line_item set quantity = ?, product_id = ? where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, lineItem.getQuantity());
			pStmt.setInt(2, lineItem.getProductId());
			pStmt.setInt(3, lineItem.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void delete(LineItem lineItem) {
		try {
			String sqlStr = "delete from tbl_line_item where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, lineItem.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public List<LineItem> getAllLineItems() {
		List<LineItem> lineItemList = new ArrayList<LineItem>();
		LineItem lineItem = null;
		try {
			String sqlStr = "select * from tbl_line_item";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				lineItem = new LineItem();
				lineItem.setId(rSet.getInt("id"));
				lineItem.setQuantity(rSet.getInt("quantity"));
				lineItem.setProductId(rSet.getInt("product_id"));
				lineItemList.add(lineItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return lineItemList;
	}
	
	public ResultSet getAllLineItemsResultSet(){
		ResultSet rSet = null;
		try{
			String sqlStr = "select * from tbl_line_item";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			rSet = MySQLConnection.readFromDatabase(pStmt);
			return rSet;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rSet;
	}

	public LineItem getLineItem(int id) {
		LineItem lineItem = null;
		try {
			String sqlStr = "select * from tbl_line_item where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, id);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				lineItem = new LineItem();
				lineItem.setId(rSet.getInt("id"));
				lineItem.setQuantity(rSet.getInt("quantity"));
				lineItem.setProductId(rSet.getInt("product_id"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return lineItem;
	}
}// end class
