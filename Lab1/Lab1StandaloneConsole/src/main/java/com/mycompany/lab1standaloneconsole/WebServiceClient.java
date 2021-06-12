/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab1standaloneconsole;


import com.mycompany.lab1standalone.Person;
import com.mycompany.lab1standalone.PersonService;
import com.mycompany.lab1standalone.FieldsInter;
import com.mycompany.lab1standalone.FindRequest;
import com.mycompany.lab1standalone.GetPersonsResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Future;



public class WebServiceClient {
public static void main(String[] args) throws MalformedURLException, InterruptedException, ExecutionException {
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
        
        
        //add tast getPersons()
        Callable task;
        task = () -> {
            FindRequest findRequest2 = new FindRequest();
            List<FieldsInter> fieldsInterList2 = findRequest2.getFields();
            FieldsInter fieldsInter2 = new FieldsInter();
            fieldsInter2.setField("age");
            fieldsInter2.setValue("26");
            fieldsInterList2.add(fieldsInter2);
            return personService.getPersonWebServicePort().getPersons(findRequest2);
        };
        
        FutureTask<List<Person>> future = new FutureTask<>(task);
        //start task
        new Thread(future).start();
        //while waiting for responce do smth
        while (!future.isDone()){
            Thread.sleep(10);
            System.out.println("Getting persons...");
        }
        
        persons = future.get();
        
        for (Person person : persons) {
            System.out.println("name: " + person.getName() + ", surname: " + person.getSurname() + ", age: " + person.getAge() + ", birthplace: " + person.getBirthplace()+ ", university: " + person.getUniversity());
        }
        System.out.println("Total persons: " + persons.size());
}
}
