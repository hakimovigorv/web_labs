package com.mycompany.lab2standalone.interaction;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "findRequest")
public class FindRequest implements Serializable {
    private List<FieldsInter> RequestFields;


    public List<FieldsInter> getFields() {
        return RequestFields;
    }

    public void setFields(List<FieldsInter> fieldFinds) {
        this.RequestFields = fieldFinds;
    }
}