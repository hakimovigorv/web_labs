/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1j2ee_console;

import com.mycompany.lab1j2ee.Person;
import com.mycompany.lab1j2ee.PersonService;
import com.mycompany.lab1j2ee.FieldsInter;
import com.mycompany.lab1j2ee.FindRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class WebServiceClient {
public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/Lab1J2EE/PersonService?wsdl");
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
}
}
