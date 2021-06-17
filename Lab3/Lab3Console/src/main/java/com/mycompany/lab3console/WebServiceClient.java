/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3console;

import com.mycompany.lab3standalone.CreateRequest;
import com.mycompany.lab3standalone.FieldsInter;
import com.mycompany.lab3standalone.FindRequest;
import com.mycompany.lab3standalone.FindResponse;
import com.mycompany.lab3standalone.NotFoundException;
import com.mycompany.lab3standalone.PersonServiceI;
import com.mycompany.lab3standalone.UpdateRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author arhan
 */
public class WebServiceClient {

    public static void main(String[] args) throws MalformedURLException, NotFoundException {
        URL url = new URL("http://localhost:8080/PersonService?wsdl");
        QName qname = new QName("http://lab3standalone.mycompany.com/", "PersonService");

        Service service = Service.create(url, qname);
        PersonServiceI personService = service.getPort(PersonServiceI.class);
        Map<String, Object> req_ctx = ((BindingProvider) personService).getRequestContext();
        //req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList("user"));
        headers.put("Password", Collections.singletonList("password"));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        FindRequest findRequest = new FindRequest();
        List<FieldsInter> fieldsInterList = findRequest.getFields();

        FieldsInter fieldsInter = new FieldsInter();
        fieldsInter.setField("birthplace");
        fieldsInter.setValue("Москва");

        fieldsInterList.add(fieldsInter);

        //FindResponse findResponse = personService.getPersons(findRequest);
        System.out.println(personService.getPersons(findRequest));

        CreateRequest createRequest = new CreateRequest();
        createRequest.setName("Игорь");
        createRequest.setSurname("Хакимов");
        createRequest.setAge(23);
        createRequest.setBirthplace("Нижнекамск");
        createRequest.setUniversity("КФУ");
        Integer id = 0;
        try {
            id = personService.createPerson(createRequest);
            System.out.println("Person " + "(" + id.toString() + ")" + " has been created");
        } catch (NotFoundException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setName("Игорь");
        updateRequest.setSurname("Хакимов");
        updateRequest.setAge(23);
        updateRequest.setBirthplace("Нижнекамск");
        updateRequest.setUniversity("ИТМО");
        updateRequest.setId(id);
        try {
            String updateResponse = personService.updatePerson(updateRequest);
            System.out.println(updateResponse);
        } catch (NotFoundException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            String deleteResponse = personService.deletePerson(id);
            System.out.println(deleteResponse);
        } catch (NotFoundException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            personService.deletePerson(id);
        } catch (NotFoundException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
