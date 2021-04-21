/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3standalone.exceptions;

/**
 *
 * @author arhan
 */
public class PersonServiceFault {
 private static final String DEFAULT_MESSAGE = "Web Service exeption";

 protected String message;
 public String getMessage() {
    return message;
 }
 
 public void setMessage(String message) {
    this.message = message;
 }

 public static PersonServiceFault defaultInstance() {
    PersonServiceFault fault = new PersonServiceFault();
    fault.message = DEFAULT_MESSAGE;
    return fault;
 }
}