package com.idomtest.personverifier.entities;

import java.io.Serializable;

public class Error implements Serializable {
    String type;
    String description;

    private static final long serialVersionUID = 1L;

    public Error(String type, String description){
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
