/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.toysrus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Yuta
 */
public class SalesDAO {
    
    public static JSONArray getSales() throws JSONException{
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonYear = new JSONObject();
        JSONArray jsonMonths = new JSONArray();
        JSONObject jsonMonth = new JSONObject();
        JSONArray jsonSales = new JSONArray();
        JSONObject jsonSale = new JSONObject();
        
        PreparedStatement pStmt = null;
        Connection conn = DatabaseUtils.retrieveConnection();
        ResultSet rs = null;
        
        String query
                = "SELECT       YEAR(O.ORDERDATE), MONTH(O.ORDERDATE), OD.PRODUCTCODE, (OD.PRICEEACH * OD.QUANTITYORDERED) AS SALES "
                + "FROM         ORDERS O JOIN ORDERDETAILS OD ON O.ORDERNUMBER = OD.ORDERNUMBER "
                + "GROUP BY     YEAR(O.ORDERDATE), MONTH(O.ORDERDATE), OD.PRODUCTCODE;";
        
        try{
            pStmt = conn.prepareStatement(query);
            rs = pStmt.executeQuery();
            
            int tempYear = 0;
            int tempMonth = 0;
            
            while(rs.next()){
                
                if(rs.getInt(1) != tempYear){
                    jsonMonths = new JSONArray();
                    //System.out.println("Year: " + rs.getInt(1));
                    jsonArray.put(jsonYear);
                    jsonYear.put("year", rs.getInt(1));
                    jsonYear.put("months", jsonMonths);
                    jsonYear = new JSONObject();
                }
                
                if(rs.getInt(2) != tempMonth){
                    jsonSales = new JSONArray();
                    //System.out.println("\tMonth: " + rs.getInt(2));
                    jsonMonths.put(jsonMonth);
                    jsonMonth.put("month", rs.getInt(2));
                    jsonMonth.put("sales", jsonSales);
                    jsonMonth = new JSONObject();
                }
                
                //System.out.println("\t\tProduct Code: " + rs.getString(3));
                //System.out.println("\t\tSales: " + rs.getDouble(4));
                jsonSales.put(jsonSale);
                jsonSale.put("productCode", rs.getString(3));
                jsonSale.put("sales", rs.getDouble(4));
                jsonSale = new JSONObject();
                
                tempYear = rs.getInt(1);
                tempMonth = rs.getInt(2);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    conn.close();
                    pStmt.close();
                    rs.close();
                }catch(Exception e){}
            }
        }
        
        return jsonArray;
    }
    
}
