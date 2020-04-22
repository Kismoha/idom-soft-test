package com.idomtest.personverifier;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A dictionary which holds the citizenships
 * Singleton
 */
public class CitizenshipDictionary {

    private static CitizenshipDictionary instance;
    private List<JSONObject> citizenships;

    private CitizenshipDictionary(){
        try {
            Object obj = new JSONParser().parse(new FileReader("src/main/resources/kodszotar21_allampolg.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray jsonCitizenships = (JSONArray) jsonObject.get("rows");

            citizenships = new ArrayList<>();
            Iterator itr = jsonCitizenships.iterator();

            while(itr.hasNext()){

                citizenships.add((JSONObject) itr.next());
            }

        } catch (IOException e) {
            //TODO
        } catch (ParseException e) {
            //TODO
        }
    }

    /**
     * returns the dictonary which holds the citizenships
     * @return a List of the citizenships
     */
    public List<JSONObject> getDictionary(){
        return citizenships;
    }

    /**
     * returns the instance or creates it if it doesn't exists
     * @return instance
     */
    public static CitizenshipDictionary getInstance() {
        if(instance == null){

            instance = new CitizenshipDictionary();
        }

        return instance;
    }
}
