package com.idomtest.personverifier;

import com.idomtest.personverifier.entities.DocumentVerifierService;
import com.idomtest.personverifier.entities.PersonDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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

    @RequestMapping( value="/postPerson",
    produces = APPLICATION_JSON_VALUE)
    Map<String, Object> validatePerson(@RequestBody PersonDTO person) {
        Map<String, Object> result = new HashMap<>();
        result.put("Errors :", validator.validatePerson(person));
        result.put("Person :", person);
        result.put("Service result", serviceHolder.getDocumentVerifierService().validateDocuments(person));
        return result;
    }

}
