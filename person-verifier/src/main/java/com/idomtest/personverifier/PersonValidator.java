package com.idomtest.personverifier;

import com.idomtest.personverifier.entities.Error;
import com.idomtest.personverifier.entities.PersonDTO;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonValidator
{
    private static final String HUNGARIAN_LETTERS = "öÖüÜóÓőŐúÚéÉáÁűŰíÍ";
    private static final String DR = "(Dr.){0,1}";

    private CitizenshipDictionary dictionary = CitizenshipDictionary.getInstance();

    public List<Error> validatePerson(PersonDTO person){
        List<Error> errors = new ArrayList<>();
        errors.add(validateName(person.getCurrentName()));
        errors.add(validateName(person.getBirthName()));
        errors.add(validateName(person.getMothersName()));
        errors.add(validateBirthDate(person.getBirthDate()));
        errors.add(validateGender(person.getGender()));
        errors.add(validateCitizenship(person.getCitizenship()));
        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
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
            //TODO : between 18 and 80
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
}
