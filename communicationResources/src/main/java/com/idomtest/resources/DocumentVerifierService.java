package com.idomtest.resources;

import java.util.Map;

public interface DocumentVerifierService {
    Map<String, Object> validateDocuments(SzemelyDTO person);
}
