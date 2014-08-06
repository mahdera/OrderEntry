/**
 * 
 */
package com.mahder.util;

import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.sql.ResultSetMetaData;

/**
 * @author Mahder on macbook Pro
 *
 */
public class ToJSON {
	public JSONArray toJSONArray(ResultSet rSet){
		JSONArray jsonArray = new JSONArray();
		String temp = null;
		
		try{
			//we will need the column names...
			ResultSetMetaData rSetMetaData = rSet.getMetaData();
			while(rSet.next()){
				//how many columns are there?
				int numColumns = rSetMetaData.getColumnCount();
				//each row in the result set will be converted into JSON object...
				JSONObject jsonObject  = new JSONObject();
				//loop thru all columns and place them into the JSON object...
				for(int i=1;i<=numColumns;i++){					
					String columnName = rSetMetaData.getColumnName(i);					
					if(rSetMetaData.getColumnType(i) == java.sql.Types.SMALLINT){
						jsonObject.put(columnName, rSet.getInt(columnName));						
					}else if(rSetMetaData.getColumnType(i) == java.sql.Types.VARCHAR){
						temp = rSet.getString(columnName);
						temp = ESAPI.encoder().canonicalize(temp);
						temp = ESAPI.encoder().encodeForHTML(temp);						
						jsonObject.put(columnName, temp);						
					}else if(rSetMetaData.getColumnType(i) == java.sql.Types.INTEGER){
						jsonObject.put(columnName, rSet.getInt(columnName));
					}else if(rSetMetaData.getColumnType(i) == java.sql.Types.DATE){
						jsonObject.put(columnName, rSet.getDate(columnName));
					}else if(rSetMetaData.getColumnType(i) == java.sql.Types.FLOAT){
						jsonObject.put(columnName, rSet.getFloat(columnName));
					}
				}
				//now add the json object to the json array...
				jsonArray.put(jsonObject);				
			}//end row while...loop
			rSet.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonArray;
	}
	
}//end class
