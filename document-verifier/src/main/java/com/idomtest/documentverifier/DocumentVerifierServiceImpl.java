package com.idomtest.documentverifier;

import com.idomtest.resources.DocumentVerifierService;
import com.idomtest.resources.PersonDTO;

import java.util.HashMap;
import java.util.Map;

public class DocumentVerifierServiceImpl implements DocumentVerifierService {
    private DocumentValidator validator = new DocumentValidator();

    /**
     * Calls the validator and builds the return value of the service
     * @param person
     * @return A Map with two kys:
     *  Errors: contains a list of validation errors.
     *  DocumentDTOs contains a list consisting of the filled out documents
     */
    @Override
    public Map<String, Object> validateDocuments(PersonDTO person) {
        Map<String, Object> result = new HashMap<>();
        result.put("Errors", validator.validateDocuments(person.getDocuments()));
        result.put("DocumentDTOs", person.getDocuments());
        return result;
    }
}
