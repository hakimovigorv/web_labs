package com.mycompany.lab3standalone;

import com.mycompany.lab3standalone.interaction.CreateRequest;
import com.mycompany.lab3standalone.interaction.FieldsInter;
import com.mycompany.lab3standalone.interaction.FindRequest;
import com.mycompany.lab3standalone.interaction.UpdateRequest;
import com.mycompany.lab3standalone.exceptions.NotFoundException;
import com.mycompany.lab3standalone.exceptions.PersonServiceFault;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;


@WebService(serviceName = "PersonService")
public class PersonWebService {
    @WebMethod(operationName = "getPersons")
    public List<Person> getPersons(@WebParam(name = "fields") FindRequest findRequest) {

        List<Fields> fields = new ArrayList<>();
        for(FieldsInter f : findRequest.getFields())
        {
            fields.add(new Fields(f.getField(),f.getValue()));
        }

        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Person> persons = dao.getPersons(fields);

        return persons;
    }
    
    @WebMethod(operationName = "createPerson")
    public int createPerson(@WebParam(name = "request") CreateRequest createRequest) {

        Person newPerson = new Person(createRequest.getName(),createRequest.getSurname(),createRequest.getAge(),createRequest.getBirthplace(),createRequest.getUniversity());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.createPerson(newPerson);
    }
    
    @WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "request") UpdateRequest UpdateRequest)throws NotFoundException {

        Person newPerson = new Person(UpdateRequest.getName(),UpdateRequest.getSurname(),UpdateRequest.getAge(),UpdateRequest.getBirthplace(),UpdateRequest.getUniversity());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updatePerson(UpdateRequest.getId(),newPerson);
    }
    
    @WebMethod(operationName = "deletePerson")
    public String delete(@WebParam(name = "request") int id) throws NotFoundException{
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deletePerson(id);
    }

}