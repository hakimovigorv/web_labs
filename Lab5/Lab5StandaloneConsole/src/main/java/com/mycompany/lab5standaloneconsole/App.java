/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab5standaloneconsole;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class App {

    private static final String URL = "http://localhost:8080/rest/persons";

    public static void main(String[] args) {
        Client client = Client.create();
        printList(getPersons(client, null, null, null, null, null));
        System.out.println();
        printList(getPersons(client, null, null, null, "Санкт-Петербург", null));
        System.out.println();
        printList(getPersons(client, null, null, "30", null, null));
        System.out.println();
        String id = createPerson(client, "Игорь", "Хакимов", "20", "Нижнекамск", "КФУ");
        System.out.println("Created person with id = "+id);
        System.out.println();
        printList(getPersons(client, "Игорь", null, null, null, null));
        System.out.println();
        System.out.println(updatePerson(client,id, "Игорь", "Хакимов", "23", "Казань", "ИТМО"));
        System.out.println();
        printList(getPersons(client, "Игорь", null, null, null, null));
        System.out.println();
        System.out.println(deletePerson(client,id));
        printList(getPersons(client, "Игорь", null, null, null, null));
        System.out.println();
    }

    private static List<Person> getPersons(Client client, String name, String surname, String age, String birthplace, String university) {
        WebResource webResource = client.resource(URL);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        if (name != null) {
            queryParams.add("name", name);
        }
        if (surname != null) {
            queryParams.add("surname", surname);
        }
        if (age != null) {
            queryParams.add("age", age);
        }
        if (birthplace != null) {
            queryParams.add("birthplace", birthplace);
        }
        if (university != null) {
            queryParams.add("university", university);
        }
        webResource = webResource.queryParams(queryParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Person>> type = new GenericType<List<Person>>() {
        };
        return response.getEntity(type);
    }

    private static String createPerson(Client client, String name, String surname, String age, String birthplace, String university) {
        WebResource webResource = client.resource(URL);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        if (name != null) {
            queryParams.add("name", name);
        }
        if (surname != null) {
            queryParams.add("surname", surname);
        }
        if (age != null) {
            queryParams.add("age", age);
        }
        if (birthplace != null) {
            queryParams.add("birthplace", birthplace);
        }
        if (university != null) {
            queryParams.add("university", university);
        }
        webResource = webResource.queryParams(queryParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }

        return response.getEntity(String.class);
    }
    
    private static String updatePerson (Client client, String id ,String name, String surname, String age, String birthplace, String university){
        WebResource webResource = client.resource(URL);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
         if (id != null) {
            queryParams.add("id", id);
        }
        if (name != null) {
            queryParams.add("name", name);
        }
        if (surname != null) {
            queryParams.add("surname", surname);
        }
        if (age != null) {
            queryParams.add("age", age);
        }
        if (birthplace != null) {
            queryParams.add("birthplace", birthplace);
        }
        if (university != null) {
            queryParams.add("university", university);
        }
        webResource = webResource.queryParams(queryParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        return response.getEntity(String.class);
    }

    private static String deletePerson(Client client, String id) {
        WebResource webResource = client.resource(URL);
        if (id != null) {
            webResource = webResource.queryParam("id", id);
        }
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }

        return response.getEntity(String.class);
    }

    private static void printList(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}
