package com.idomtest.documentverifier.entities;

import java.util.Date;

public class DocumentDTO {
    private int documentType;
    private String documentNumber;
    private byte[] documetnPicture;
    private Date expirationDate;
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

    public byte[] getDocumetnPicture() {
        return documetnPicture;
    }

    public void setDocumetnPicture(byte[] documetnPicture) {
        this.documetnPicture = documetnPicture;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
