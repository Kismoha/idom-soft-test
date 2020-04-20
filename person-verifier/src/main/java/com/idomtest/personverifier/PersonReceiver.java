package com.idomtest.personverifier;

import com.idomtest.personverifier.entities.PersonDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@EnableAutoConfiguration
public class PersonReceiver {

    private PersonValidator validator = new PersonValidator();

    @RequestMapping( value="/postPerson",
    produces = APPLICATION_JSON_VALUE)
    Map<String, Object> validatePerson(@RequestBody PersonDTO person) {
        Map<String, Object> result = new HashMap<>();
        result.put("Errors :", validator.validatePerson(person));
        result.put("Person :", person);
        return result;
    }

}
