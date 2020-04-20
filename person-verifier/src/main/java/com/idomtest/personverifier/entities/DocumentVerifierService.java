package com.idomtest.personverifier.entities;

import java.util.Map;

public interface DocumentVerifierService {
    Map<String, Object> validateDocuments(PersonDTO person);
}
