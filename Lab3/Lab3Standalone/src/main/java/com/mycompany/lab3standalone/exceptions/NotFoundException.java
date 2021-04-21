/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3standalone.exceptions;
import javax.xml.ws.WebFault;
/**
 *
 * @author arhan
 */

@WebFault(faultBean = "com.mycompany.lab3standalone.exceptions.PersonServiceFault")
public class NotFoundException extends Exception{
 private static final long serialVersionUID = -6647544772732631047L;
 private final PersonServiceFault fault;
 
 public NotFoundException(String message, PersonServiceFault fault) {
    super(message);
    this.fault = fault;
 }
 public NotFoundException(String message, PersonServiceFault fault, Throwable cause) {
    super(message, cause);
    this.fault = fault;
 }
 public PersonServiceFault getFaultInfo() {
 return fault;
 }
}