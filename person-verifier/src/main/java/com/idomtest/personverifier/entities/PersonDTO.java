package com.idomtest.personverifier.entities;

import java.util.List;

public class PersonDTO {
    private String currentName;
    private String birthName;
    private String mothersName;
    private char gender;
    private String birthDate;
    private String citizenship;
    private String citizenshipDecode;
    private List<DocumentDTO> documents;


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getCitizenshipDecode() {
        return citizenshipDecode;
    }

    public void setCitizenshipDecode(String citizenshipDecode) {
        this.citizenshipDecode = citizenshipDecode;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }
}
