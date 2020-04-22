package com.idomtest.documentverifier;

import com.idomtest.resources.DocumentDTO;
import com.idomtest.resources.Error;
import com.idomtest.resources.PersonDTO;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The class is responsible for validation a person's documents
 */
public class DocumentValidator {

    private DocumentDictionary dictionary = DocumentDictionary.getInstance();

    /**
     * Validates all of the given documents and returns with the errors
     * @param documents
     * @return List of Errors
     */
    public List<Error> validateDocuments(List<DocumentDTO> documents){

        List<Error> errors = new ArrayList<>();

        for(DocumentDTO document : documents){

            errors.addAll(validateDocument(document));

        }

        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
    }

    private List<Error> validateDocument(DocumentDTO document) {

        List<Error> errors = new ArrayList<>();
        int documentType = document.getDocumentType();

        errors.add(validateType(document.getDocumentNumber(),documentType));
        errors.add(validatePicture(document.getDocumentPicture()));
        errors.add(validateExpirationDate(document.getExpirationDate()));
        errors.add(fillValid(document));

        return errors;
    }

    private Error fillValid(DocumentDTO document) {
        try {

            Date documentDate = new SimpleDateFormat("yyyy.MM.dd").parse(document.getExpirationDate());
            Date currentDate = new Date();
            int compareResult = documentDate.compareTo(currentDate);

            if(compareResult > 0){

                document.setValid(true);

            }else{

                document.setValid(false);

            }

        } catch (ParseException | NullPointerException e) {

            return new Error("DocumentDTO","Hibás lejárati dátum! formátum: yyyy.MM.dd");

        }
        return null;
    }

    private Error validateExpirationDate(String expirationDate) {

        try {

            new SimpleDateFormat("yyyy.MM.dd").parse(expirationDate);

        } catch (ParseException | NullPointerException e) {

            return new Error(PersonDTO.class.toString(),"Rossz születési dátum formátum! Megengedett: yyyy.MM.dd");

        }

        return null;
    }

    private Error validatePicture(String documentPicture) {
        //TODO implement picture validation
        return null;
    }

    private Error validateType(String documentNumber, int documentType) {

        for (JSONObject item : dictionary.getDictionary()){

            if(item.get("kod").equals("" + documentType)){

                return validateNumber(documentNumber,documentType);

            }
        }

        return new Error(DocumentDTO.class.toString(),"Rossz dokumentum típus!");
    }

    private Error validateNumber(String documentNumber, int documentType) {

        switch(documentType){
            case 1 :

                if(documentNumber != null && Pattern.matches("[0-9]{6}+[a-zA-Z]{2}+",documentNumber)) {

                    return null;
                }

                return new Error(DocumentDTO.class.toString(),"Hibás dokumentum szám! Megengedett: 6 szám + 2 betű");

            case 2 :

                if(documentNumber != null && Pattern.matches("[a-zA-Z]{2}+[0-9]{7}+",documentNumber)){

                    return null;
                }

                return new Error(DocumentDTO.class.toString(),"Hibás dokumentum szám! Megengedett: 2 betű + 7 szám");

            default :

                if(documentNumber != null && documentNumber.length()<=10){

                    return null;
                }

                return new Error(DocumentDTO.class.toString(),"Hibás dokumentum szám! Megengedett: MAX 10 karakter");
        }
    }
}
