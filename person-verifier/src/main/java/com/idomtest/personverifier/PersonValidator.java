package com.idomtest.personverifier;

import com.idomtest.resources.Error;
import com.idomtest.resources.OkmanyDTO;
import com.idomtest.resources.SzemelyDTO;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is responsible for validationg a SzemelyDTO
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
    public List<Error> validatePerson(SzemelyDTO person){

        List<Error> errors = new ArrayList<>();
        errors.add(validateName(person.getVisNev()));
        errors.add(validateName(person.getSzulNev()));
        errors.add(validateName(person.getaNev()));
        errors.add(validateBirthDate(person.getSzulDat()));
        errors.add(validateGender(person.getNeme()));
        errors.add(validateCitizenship(person.getAllampKod()));

        errors.addAll(checkValidDocumentNumbers(person.getOkmLista()));

        fillCitizenshipCode(person);

        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
    }

    private void fillCitizenshipCode(SzemelyDTO person) {

        for(JSONObject item : dictionary.getDictionary()){

            if(person.getAllampKod() == item.get("kod")){

                person.setAllampDekod( (String) item.get("allampolgarsag"));
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

        return new Error("SzemelyDTO","Hibás állampolgárság!");
    }

    private Error validateGender(String gender) {

        if(gender.equals("F") || gender.equals("M")){

            return null;
        }

        return new Error("SzemelyDTO","Nem megfelelő a megadott nem! (F vagy M)");
    }

    private Error validateBirthDate(String birthDate) {

        try {

            Date date = new SimpleDateFormat("yyyy.MM.dd").parse(birthDate);
            Date currentDate = new Date();
            int birthYear = date.getYear();
            int currentYear = currentDate.getYear();

            if(currentYear-birthYear > 80 || currentYear-birthYear < 18){

                return new Error("SzemelyDTO","Nem megengedett korosztály. 18-80");
            }

        } catch (ParseException | NullPointerException e) {

            return new Error("SzemelyDTO","Rossz születési dátum formátum! Megengedett: yyyy.MM.dd");
        }

        return null;
    }

    private Error validateName(String name) {

        if(name != null && Pattern.matches(DR+"[a-zA-z.Ä/\"\\-\\s"+HUNGARIAN_LETTERS+"]{2,}"+DR,name) && name.length() <= 80){

            return null;
        }

        return new Error("SzemelyDTO","Hibás név formátum! Legalább két névelemnek kell lennie, " +
                "a kezdő vagy befejező Dr.-on kívül magyar ABC plussz Ä, pont, perjel, aposztróf, kötőjel és szóköz Max 80");
    }

    private List<Error> checkValidDocumentNumbers(List<OkmanyDTO> documents) {

        List<Error> errors = new ArrayList<>();
        Map<String, Integer> documentTypeNumbers = new HashMap<>();

        for (OkmanyDTO document : documents) {

            String type = document.getOkmTipus();
            Integer value = documentTypeNumbers.get(type);

            if (value == null) {

                documentTypeNumbers.put(type, 0);

            } else {

                documentTypeNumbers.put(type, value + 1);
            }
        }

        for (String key : documentTypeNumbers.keySet()){

            int value = documentTypeNumbers.get(key);

            if(value > 1){

                errors.add(new Error("Szemely","A(z) " + key + " típusú dokumentumból több mint 1 érvényes!"));
            }
        }

        return errors;
    }
}
