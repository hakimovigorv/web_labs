/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab6standalone;

import com.mycompany.lab6standalone.exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    @GET
    public List<Person> getPersons(@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("age") Integer age, @QueryParam("birthplace") String birthplace, @QueryParam("university") String university) {
        List<Fields> fields = new ArrayList<>();
        fields.add(new Fields("name", name));
        fields.add(new Fields("surname", surname));
        fields.add(new Fields("age", age));
        fields.add(new Fields("birthplace", birthplace));
        fields.add(new Fields("university", university));
        List<Person> persons = new PostgreSQLDAO().getPersons(fields);
        return persons;
    }
    
    @POST
    public String createPerson(@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("age") Integer age, @QueryParam("birthplace") String birthplace, @QueryParam("university") String university){
        Person newPerson = new Person(name, surname, age, birthplace, university);
        PostgreSQLDAO dao = new PostgreSQLDAO();
        Integer id = dao.createPerson(newPerson);
        return id.toString();
    }
    
    @PUT
    public String updatePerson(@QueryParam("id") Integer id,@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("age") Integer age, @QueryParam("birthplace") String birthplace, @QueryParam("university") String university) throws PersonNotFoundException{
        Person newPerson = new Person(name, surname, age, birthplace, university);
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updatePerson(id, newPerson);
    }
    
    @DELETE 
    public String deletePerson(@QueryParam("id") Integer id) throws PersonNotFoundException{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deletePerson(id);
    }
}
