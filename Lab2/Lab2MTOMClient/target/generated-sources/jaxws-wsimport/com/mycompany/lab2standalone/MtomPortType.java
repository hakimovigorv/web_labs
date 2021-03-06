
package com.mycompany.lab2standalone;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MtomPortType", targetNamespace = "http://lab2standalone.mycompany.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MtomPortType {


    /**
     * 
     * @param request
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deletePerson", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.DeletePerson")
    @ResponseWrapper(localName = "deletePersonResponse", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.DeletePersonResponse")
    public String deletePerson(
        @WebParam(name = "request", targetNamespace = "")
        int request);

    /**
     * 
     * @param request
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createPerson", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.CreatePerson")
    @ResponseWrapper(localName = "createPersonResponse", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.CreatePersonResponse")
    public int createPerson(
        @WebParam(name = "request", targetNamespace = "")
        CreateRequest request);

    /**
     * 
     * @param fields
     * @return
     *     returns java.util.List<com.mycompany.lab2standalone.Person>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPersons", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.GetPersons")
    @ResponseWrapper(localName = "getPersonsResponse", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.GetPersonsResponse")
    public List<Person> getPersons(
        @WebParam(name = "fields", targetNamespace = "")
        FindRequest fields);

    /**
     * 
     * @param arg0
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "echoBinary", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.EchoBinary")
    @ResponseWrapper(localName = "echoBinaryResponse", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.EchoBinaryResponse")
    public byte[] echoBinary(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0);

    /**
     * 
     * @param request
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "updatePerson", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.UpdatePerson")
    @ResponseWrapper(localName = "updatePersonResponse", targetNamespace = "http://lab2standalone.mycompany.com/", className = "com.mycompany.lab2standalone.UpdatePersonResponse")
    public String updatePerson(
        @WebParam(name = "request", targetNamespace = "")
        UpdateRequest request);

}
