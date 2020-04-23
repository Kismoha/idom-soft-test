package com.idomtest.personverifier;

import com.idomtest.resources.DocumentVerifierService;
import com.idomtest.resources.Error;
import com.idomtest.resources.OkmanyDTO;
import com.idomtest.resources.SzemelyDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@EnableAutoConfiguration
public class PersonReceiver {

    private ServiceHolder serviceHolder = ServiceHolder.getInstance();
    private DocumentVerifierService documentVerifierService = serviceHolder.getDocumentVerifierService();
    private PersonValidator validator = new PersonValidator();

    @Bean
    public HttpInvokerProxyFactoryBean invoker(){

        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/validate");
        invoker.setServiceInterface(DocumentVerifierService.class);
        return invoker;
    }

    /**
     * conducts a person's computations
     *  calls the validator
     *  puts together the return value which holds:
     *      Errors: a list of the validation errors
     *      SzemelyDTO the filled out person object
     * @param person
     * @return A map containing the Errors and the validated SzemelyDTO
     */
    @PostMapping( value="/postPerson",
    produces = APPLICATION_JSON_VALUE)
    Map<String, Object> validatePerson(@RequestBody SzemelyDTO person) {

        List<Error> errors = new ArrayList<>();

        errors.addAll(validator.validatePerson(person));

        Map<String, Object> serviceResult = serviceHolder.getDocumentVerifierService().validateDocuments(person);
        errors.addAll( (List<Error>) serviceResult.get("Errors"));
        person.setOkmLista( (List<OkmanyDTO>) serviceResult.get("OkmanyDTOs"));

        errors.addAll(validator.checkValidDocumentNumbers(person.getOkmLista()));

        Map<String, Object> result = new HashMap<>();
        result.put("Errors :", errors);
        result.put("PersonDTO", person);
        return result;
    }

}
