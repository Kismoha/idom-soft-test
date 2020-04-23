package com.idomtest.documentverifier;

import com.idomtest.resources.Error;
import com.idomtest.resources.OkmanyDTO;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<Error> validateDocuments(List<OkmanyDTO> documents){

        List<Error> errors = new ArrayList<>();

        if(documents == null){

            return new ArrayList<>();
        }

        for(OkmanyDTO document : documents){

            errors.addAll(validateDocument(document));

        }

        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
    }

    private List<Error> validateDocument(OkmanyDTO document) {

        List<Error> errors = new ArrayList<>();
        String documentType = document.getOkmTipus();

        errors.add(validateType(document.getOkmanySzam(),documentType));
        errors.add(validatePicture(document.getOkmanyKep()));
        errors.add(validateExpirationDate(document.getLejarDat()));
        errors.add(fillValid(document));

        return errors;
    }

    private Error fillValid(OkmanyDTO document) {
        try {

            Date documentDate = new SimpleDateFormat("yyyy.MM.dd").parse(document.getLejarDat());
            Date currentDate = new Date();
            int compareResult = documentDate.compareTo(currentDate);

            if(compareResult > 0){

                document.setErvenyes(true);

            }else{

                document.setErvenyes(false);

            }

        } catch (ParseException | NullPointerException e) {
            //Ignore this exception, expiration date has been already validated
        }
        return null;
    }

    private Error validateExpirationDate(String expirationDate) {

        try {

            new SimpleDateFormat("yyyy.MM.dd").parse(expirationDate);

        } catch (ParseException | NullPointerException e) {

            return new Error("OkmanyDTO","Rossz lejárati dátum formátum! Megengedett: yyyy.MM.dd");

        }

        return null;
    }

    private Error validatePicture(byte[] documentPicture) {

        //TODO current implementations do not work

        /*ImageIcon image = new ImageIcon(documentPicture);
        int width = image.getIconWidth();
        int height = image.getIconHeight();

        if(width != 827 || height != 1063){
            return new Error("OkmanyDTO","A megadott okmany kep nem a megfelelő méretű! (1063*827)");
        }*/

        /*ByteArrayInputStream bais = new ByteArrayInputStream(documentPicture);
        try {
            BufferedImage image = ImageIO.read(bais);
            int width = image.getWidth();
            int height = image.getHeight();

            if(width != 827 || height != 1063){
                return new Error("OkmanyDTO","A megadott okmany kep nem a megfelelő méretű! (1063*827)");
            }
        } catch (IOException e) {
            return new Error("OkmanyDtO","Image parsing error!");
        }*/

        return null;
    }

    private Error validateType(String documentNumber, String documentType) {

        for (JSONObject item : dictionary.getDictionary()){

            if(item.get("kod").equals(documentType)){

                return validateNumber(documentNumber,documentType);

            }
        }

        return new Error("OkmanyDTO","Rossz dokumentum típus!");
    }

    private Error validateNumber(String documentNumber, String documentType) {

        switch(documentType){
            case "1" :

                if(documentNumber != null && Pattern.matches("[0-9]{6}+[a-zA-Z]{2}+",documentNumber)) {

                    return null;
                }

                return new Error("OkmanyDTO","Hibás dokumentum szám! Megengedett: 6 szám + 2 betű");

            case "2" :

                if(documentNumber != null && Pattern.matches("[a-zA-Z]{2}+[0-9]{7}+",documentNumber)){

                    return null;
                }

                return new Error("OkmanyDTO","Hibás dokumentum szám! Megengedett: 2 betű + 7 szám");

            default :

                if(documentNumber != null && documentNumber.length()<=10){

                    return null;
                }

                return new Error("OkmanyDTO","Hibás dokumentum szám! Megengedett: MAX 10 karakter");
        }
    }
}
