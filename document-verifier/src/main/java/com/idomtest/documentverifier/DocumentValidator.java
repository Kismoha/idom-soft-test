package com.idomtest.documentverifier;

import com.idomtest.documentverifier.entities.PersonDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentValidator {
    public List<Error> validateDocuments(PersonDTO personDTO){
        List<Error> errors = new ArrayList<>();
        return errors.stream().filter(error -> error != null).collect(Collectors.toList());
    }
}
