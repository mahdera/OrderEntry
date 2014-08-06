/**
 * 
 */
package com.mahder.rest.orderentry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mahder.rest.orderentry.datasource.MySQLConnection;
import com.mahder.rest.orderentry.model.Product;

/**
 * @author alemayehu
 *
 */
public class ProductDao {
	public void save(Product product) {
		try {
			String sqlStr = "insert into tbl_product values(?,?,?)";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, 0);
			pStmt.setString(2, product.getName());
			pStmt.setFloat(3, product.getCost());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void update(Product product) {
		try {
			String sqlStr = "update tbl_product set name = ?, cost = ? where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setString(1, product.getName());
			pStmt.setFloat(2, product.getCost());
			pStmt.setInt(3, product.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public void delete(Product product) {
		try {
			String sqlStr = "delete from tbl_product where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, product.getId());
			MySQLConnection.writeToDatabase(pStmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
	}

	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<Product>();
		Product product = null;
		try {
			String sqlStr = "select * from tbl_product order by name";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				product = new Product();
				product.setId(rSet.getInt("id"));
				product.setName(rSet.getString("name"));
				product.setCost(rSet.getFloat("cost"));
				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return productList;
	}

	public Product getProduct(int id) {
		Product product = null;
		try {
			String sqlStr = "select * from tbl_product where id = ?";
			PreparedStatement pStmt = MySQLConnection.getPreparedStatement(sqlStr);
			pStmt.setInt(1, id);
			ResultSet rSet = MySQLConnection.readFromDatabase(pStmt);
			while(rSet.next()){
				product = new Product();
				product.setId(rSet.getInt("id"));
				product.setName(rSet.getString("name"));
				product.setCost(rSet.getFloat("cost"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MySQLConnection.disconnectDatabase();
		}
		return product;
	}
}// end class
