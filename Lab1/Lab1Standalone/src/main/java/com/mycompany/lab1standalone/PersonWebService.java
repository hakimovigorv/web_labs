package com.mycompany.lab1standalone;

import com.mycompany.lab1standalone.interaction.FieldsInter;
import com.mycompany.lab1standalone.interaction.FindRequest;
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
}