package com.idomtest.personverifier;

import com.idomtest.resources.DocumentDTO;
import com.idomtest.resources.PersonDTO;
import com.idomtest.resources.Error;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is responsible for validationg a PersonDTO
 */
public class PersonValidator
{
    private static final String HUNGARIAN_LETTERS = "öÖüÜóÓőŐúÚéÉáÁűŰíÍ";
    private static final String DR = "(Dr.){0,1}";

    private CitizenshipDictionary dictionary = CitizenshipDictionary.getInstance();

    /**
     * validates a PersonDTO
     * @param person
     * @return The list of Errors
     */
    public List<Error> validatePerson(PersonDTO person){

        List<Error> errors = new ArrayList<>();
        errors.add(validateName(person.getCurrentName()));
        errors.add(validateName(person.getBirthName()));
        errors.add(validateName(person.getMothersName()));
        errors.add(validateBirthDate(person.getBirthDate()));
        errors.add(validateGender(person.getGender()));
        errors.add(validateCitizenship(person.getCitizenship()));

        errors.addAll(checkValidDocumentNumbers(person.getDocuments()));

        fillCitizenshipCode(person);

        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
    }

    private void fillCitizenshipCode(PersonDTO person) {

        for(JSONObject item : dictionary.getDictionary()){

            if(person.getCitizenship() == item.get("kod")){

                person.setCitizenshipDecode( (String) item.get("allampolgarsag"));
                break;
            }
        }
    }

    private Error validateCitizenship(String citizenship) {

        if(citizenship != null && citizenship.length() == 3){

            for(JSONObject item : dictionary.getDictionary()){

                if(item.get("kod").equals(citizenship)){

                    return null;
                }
            }
        }

        return new Error("Person DTO Error","Hibás állampolgárság!");
    }

    private Error validateGender(char gender) {

        if(gender == 'F' || gender == 'M'){

            return null;
        }

        return new Error(PersonDTO.class.toString(),"Nem megfelelő a megadott nem! (F vagy M)");
    }

    private Error validateBirthDate(String birthDate) {

        try {

            Date date = new SimpleDateFormat("yyyy.MM.dd").parse(birthDate);
            Date currentDate = new Date();
            int birthYear = date.getYear();
            int currentYear = currentDate.getYear();

            if(currentYear-birthYear > 80 || currentYear-birthYear < 18){

                return new Error("PersonDTO","Nem megengedett korosztály. 18-80");
            }

        } catch (ParseException | NullPointerException e) {

            return new Error(PersonDTO.class.toString(),"Rossz születési dátum formátum! Megengedett: yyyy.MM.dd");
        }

        return null;
    }

    private Error validateName(String name) {

        if(name != null && Pattern.matches(DR+"[a-zA-z.Ä/\"\\-\\s"+HUNGARIAN_LETTERS+"]{2,}"+DR,name) && name.length() <= 80){

            return null;
        }

        return new Error(PersonDTO.class.toString(),"Hibás név formátum! Legalább két névelemnek kell lennie, " +
                "a kezdő vagy befejező Dr.-on kívül magyar ABC plussz Ä, pont, perjel, aposztróf, kötőjel és szóköz Max 80");
    }

    private List<Error> checkValidDocumentNumbers(List<DocumentDTO> documents) {

        List<Error> errors = new ArrayList<>();
        Map<Integer, Integer> documentTypeNumbers = new HashMap<>();

        for (DocumentDTO document : documents) {

            int type = document.getDocumentType();
            Integer value = documentTypeNumbers.get(type);

            if (value == null) {

                documentTypeNumbers.put(type, 0);

            } else {

                documentTypeNumbers.put(type, value + 1);
            }
        }

        for (Integer key : documentTypeNumbers.keySet()){

            int value = documentTypeNumbers.get(key);

            if(value > 1){

                errors.add(new Error("PersonDTO","A(z) " + key + " típusú dokumentumból több mint 1 érvényes!"));
            }
        }

        return errors;
    }
}
