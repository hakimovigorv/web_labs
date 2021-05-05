/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab4ee_console;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

public class App {
    private static final String URL = "http://localhost:8080/Lab4EE/rest/persons";

    public static void main(String[] args) {
        Client client = Client.create();
        printList(getPersons(client, null, null, null, null, null));
        System.out.println();
        printList(getPersons(client, null, null, null, "Санкт-Петербург", null));
        System.out.println();
        printList(getPersons(client, null, null, "30", null, null));
    }
     private static List<Person> getPersons(Client client, String name, String surname, String age, String birthplace, String univercity) {
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
        if (univercity != null) {
            queryParams.add("univercity", univercity);
        }
        webResource = webResource.queryParams(queryParams);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<Person>> type = new GenericType<List<Person>>() {};
        return response.getEntity(type);
    }

    private static void printList(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person);
        }
    }
}
