/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3console;

import com.mycompany.lab3standalone.CreateRequest;
import com.mycompany.lab3standalone.FieldsInter;
import com.mycompany.lab3standalone.FindRequest;
import com.mycompany.lab3standalone.NotFoundException;
import com.mycompany.lab3standalone.Person;
import com.mycompany.lab3standalone.PersonService;
import com.mycompany.lab3standalone.UpdateRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author arhan
 */
public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException, NotFoundException {
        URL url = new URL("http://localhost:8080/PersonService?wsdl");
        PersonService personService = new PersonService(url);
        FindRequest findRequest = new FindRequest();
        List<FieldsInter> fieldsInterList = findRequest.getFields();

        FieldsInter fieldsInter = new FieldsInter();
        fieldsInter.setField("birthplace");
        fieldsInter.setValue("Москва");

        fieldsInterList.add(fieldsInter);

        List<Person> persons = personService.getPersonWebServicePort().getPersons(findRequest);
        for (Person person : persons) {
            System.out.println("name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " + person.getAge() + ", birthplace: " + person.getBirthplace()+ ", university: " + person.getUniversity());
        }
        System.out.println("Total persons: " + persons.size());
        
        CreateRequest createRequest = new CreateRequest();
        createRequest.setName("Игорь");
        createRequest.setSurname("Хакимов");
        createRequest.setAge(23);
        createRequest.setBirthplace("Нижнекамск");
        createRequest.setUniversity("КФУ");
        
        Integer id = personService.getPersonWebServicePort().createPerson(createRequest);
        System.out.println("Person " + "(" + id.toString() + ")" + " has been created");
        
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setName("Игорь");
        updateRequest.setSurname("Хакимов");
        updateRequest.setAge(23);
        updateRequest.setBirthplace("Нижнекамск");
        updateRequest.setUniversity("ИТМО");
        updateRequest.setId(id);
        
        String updateResponse = personService.getPersonWebServicePort().updatePerson(updateRequest);
        System.out.println(updateResponse);
        
        String deleteResponse = personService.getPersonWebServicePort().deletePerson(id);
        System.out.println(deleteResponse);
        
        try 
        {
            personService.getPersonWebServicePort().deletePerson(id);
        }
        catch (NotFoundException e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
}
}
