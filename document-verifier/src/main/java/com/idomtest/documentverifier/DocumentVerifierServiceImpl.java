package com.idomtest.documentverifier;

import com.idomtest.resources.DocumentVerifierService;
import com.idomtest.resources.PersonDTO;

import java.util.HashMap;
import java.util.Map;

public class DocumentVerifierServiceImpl implements DocumentVerifierService {
    private DocumentValidator validator = new DocumentValidator();

    @Override
    public Map<String, Object> validateDocuments(PersonDTO person) {
        Map<String, Object> result = new HashMap<>();
        result.put("Errors", validator.validateDocuments(person));
        return result;
    }
}
