package com.idomtest.personverifier.entities;

import java.util.Date;

public class DocumentDTO {
    private int documentType;
    private String documentNumber;
    private String documentPicture;
    private String expirationDate;
    private boolean valid;

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentPicture() {
        return documentPicture;
    }

    public void setDocumentPicture(String documentPicture) {
        this.documentPicture = documentPicture;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
