package com.idomtest.documentverifier.entities;

import java.util.List;
import java.util.Map;

public interface DocumentVerifierService {
    Map<String, Object> validateDocuments(PersonDTO person);
}
