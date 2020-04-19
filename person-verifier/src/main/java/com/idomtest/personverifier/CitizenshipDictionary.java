package com.idomtest.personverifier;

import org.json.simple.JSONObject;

import java.util.List;

public class CitizenshipDictionary {

    private List<JSONObject> citizenships;

    private CitizenshipDictionary(){

    }

    private static class HoldInstance {
        private static final CitizenshipDictionary INSTANCE = new CitizenshipDictionary();
    }

    public static CitizenshipDictionary getInstance() {
        return HoldInstance.INSTANCE;
    }
}
