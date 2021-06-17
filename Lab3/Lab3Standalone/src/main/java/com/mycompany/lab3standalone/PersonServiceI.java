/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3standalone;

import com.mycompany.lab3standalone.exceptions.NotFoundException;
import com.mycompany.lab3standalone.interaction.CreateRequest;
import com.mycompany.lab3standalone.interaction.FindRequest;
import com.mycompany.lab3standalone.interaction.UpdateRequest;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author arhan
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface PersonServiceI {
    @WebMethod(operationName = "getPersons")
    public String getPersons(@WebParam(name = "fields") FindRequest findRequest);
    @WebMethod(operationName = "createPerson")
    public int createPerson(@WebParam(name = "request") CreateRequest createRequest) throws NotFoundException;
    @WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "request") UpdateRequest UpdateRequest)throws NotFoundException;
    @WebMethod(operationName = "deletePerson")
    public String delete(@WebParam(name = "request") int id) throws NotFoundException;
    
}
