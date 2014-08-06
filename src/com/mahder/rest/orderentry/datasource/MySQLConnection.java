/**
 * 
 */
package com.mahder.rest.orderentry.datasource;

import java.sql.*;

public class MySQLConnection {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rSet;
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/db_order_entry";
    private static boolean connected;

    public MySQLConnection() {
        
    }

    private static void connect() throws Exception {
        Class.forName(DATABASE_DRIVER).newInstance();
        con = DriverManager.getConnection(DATABASE_URL, "root", "root");
        if(con != null){
        		stmt = con.createStatement();
        		connected = true;
        }else{
        		connected = false;
        }
    }

    public static Connection getDatabaseConnection() throws Exception{
        connect();
        Connection connection = getCon();
        return connection;
    }

    public static void disconnectDatabase() {
        try {
            stmt.close();
            con.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } 
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void writeToDatabase(PreparedStatement pStmt){
    		try{
    			pStmt.executeUpdate();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    }
    
    public static ResultSet readFromDatabase(PreparedStatement pStmt){
    		ResultSet rSet = null;
    		try{
    			rSet = pStmt.executeQuery();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return rSet;
    }
    
    public static PreparedStatement getPreparedStatement(String sqlStr){
    		PreparedStatement preparedStatement = null;
    		try{
    			connect();
    			preparedStatement = getCon().prepareStatement(sqlStr);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return preparedStatement;
    }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        MySQLConnection.con = con;
    }

    
}//end class

