/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.toysrus.rest.sales;

import com.sysintg.toysrus.dao.SalesDAO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Yuta
 */
@Path("/v1/sales")
public class V1_sales {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showSalesReport() throws JSONException{
        Response response = null;
        String returnString = SalesDAO.getSales().toString();
        
        return response.ok(returnString).build();
    }
    
}
