package com.mycompany.lab3standalone;

import com.mycompany.lab3standalone.interaction.CreateRequest;
import com.mycompany.lab3standalone.interaction.FieldsInter;
import com.mycompany.lab3standalone.interaction.FindRequest;
import com.mycompany.lab3standalone.interaction.UpdateRequest;
import com.mycompany.lab3standalone.exceptions.NotFoundException;
import com.mycompany.lab3standalone.exceptions.PersonServiceFault;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;


@WebService(serviceName = "PersonService",endpointInterface = "com.mycompany.lab3standalone.PersonServiceI")
public class PersonWebService implements PersonServiceI{
    
    @Resource
    WebServiceContext wsctx;
    
    @Override
    //@WebMethod(operationName = "getPersons")
    public String getPersons(@WebParam(name = "fields") FindRequest findRequest) {

        List<Fields> fields = new ArrayList<>();
        for(FieldsInter f : findRequest.getFields())
        {
            fields.add(new Fields(f.getField(),f.getValue()));
        }

        PostgreSQLDAO dao = new PostgreSQLDAO();
        List<Person> persons = dao.getPersons(fields);
        String result="";
        for (Person person : persons) {
            result=result+"name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " + person.getAge() + ", birthplace: " + person.getBirthplace()+ ", university: " + person.getUniversity()+"\n";
        }
        result=result+"Total persons: " + persons.size()+"\n";
        
        return result;
    }
    
    @Override
    //@WebMethod(operationName = "createPerson")
    public int createPerson(@WebParam(name = "request") CreateRequest createRequest) throws NotFoundException {
        MessageContext mctx = wsctx.getMessageContext();
        HardCodeAuth(mctx);
        Person newPerson = new Person(createRequest.getName(),createRequest.getSurname(),createRequest.getAge(),createRequest.getBirthplace(),createRequest.getUniversity());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.createPerson(newPerson);
    }
    
    @Override
    //@WebMethod(operationName = "updatePerson")
    public String updatePerson(@WebParam(name = "request") UpdateRequest UpdateRequest)throws NotFoundException {
        MessageContext mctx = wsctx.getMessageContext();
        HardCodeAuth(mctx);
        Person newPerson = new Person(UpdateRequest.getName(),UpdateRequest.getSurname(),UpdateRequest.getAge(),UpdateRequest.getBirthplace(),UpdateRequest.getUniversity());
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updatePerson(UpdateRequest.getId(),newPerson);
    }
    
    @Override
    //@WebMethod(operationName = "deletePerson")
    public String delete(@WebParam(name = "request") int id) throws NotFoundException{
        MessageContext mctx = wsctx.getMessageContext();
        HardCodeAuth(mctx);
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deletePerson(id);
    }
    
    public void HardCodeAuth(MessageContext mctx) throws NotFoundException{
        //MessageContext mctx = wsctx.getMessageContext();
        
    //get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");

        String username = "";
        String password = "";
        
        if(userList!=null){
        	//get username
        	username = userList.get(0).toString();
        }
        	
        if(passList!=null){
        	//get password
        	password = passList.get(0).toString();
        }
        if (username.equals("user") && password.equals("password")){
        	System.out.println("Authorised");
        }else{
        	throw new NotFoundException("Auth error",new PersonServiceFault());
        }
    }
}