package com.idomtest.documentverifier;

import com.idomtest.documentverifier.entities.DocumentVerifierService;
import com.idomtest.documentverifier.entities.PersonDTO;

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
