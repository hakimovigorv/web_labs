/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab2mtomclient;

import com.mycompany.lab2standalone.CreateRequest;
import com.mycompany.lab2standalone.FieldsInter;
import com.mycompany.lab2standalone.FindRequest;
import com.mycompany.lab2standalone.Person;
import com.mycompany.lab2standalone.PersonService;
import com.mycompany.lab2standalone.UpdateRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * @author arhan
 */
public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException, InterruptedException, ExecutionException, FileNotFoundException, IOException {
        URL url = new URL("http://localhost:8080/PersonService?wsdl");
        PersonService personService = new PersonService(url);
        String fileName = "binary.png";
        String filePath = "src/resources/" + fileName;
        File file = new File(filePath);
         
        // uploads a file
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            byte[] imageBytes = new byte[(int) file.length()];
            inputStream.read(imageBytes);
             
            byte[] fileBytes = personService.getMtomPortTypePort().echoBinary(imageBytes);
            inputStream.close();
            System.out.println("File uploaded: " + filePath);
            fileName = "echo.png";
            filePath = "src/resources/" + fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
            outputStream.write(fileBytes);
            outputStream.close();
             
            System.out.println("File downloaded: " + filePath);
        } catch (IOException ex) {
            System.err.println(ex);
        }      
}
}
