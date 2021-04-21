package com.mycompany.lab1standalone.interaction;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "fields")
public class FieldsInter implements Serializable{
    private String field;
    private String value;

    public FieldsInter() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}