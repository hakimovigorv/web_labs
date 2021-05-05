/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab6standalone.exceptions;

/**
 *
 * @author arhan
 */
public class PersonNotFoundException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    public static PersonNotFoundException DEFAULT_INSTANCE = new PersonNotFoundException("Person not found");

    public PersonNotFoundException(String message) {
        super(message);
    }
}
