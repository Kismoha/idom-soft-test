package com.idomtest.documentverifier;

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
 * The class represents a dictonary of documents
 * Singleton class
 */
public class DocumentDictionary {
    private static DocumentDictionary instance;
    private List<JSONObject> documents;

    /**
     * Creates the dictionary from a json file
     */
    private DocumentDictionary(){
        try {
            Object obj = new JSONParser().parse(new FileReader("src/main/resources/kodszotar46_okmanytipus.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray jsonDocuments = (JSONArray) jsonObject.get("rows");

            documents = new ArrayList<>();
            Iterator itr = jsonDocuments.iterator();

            while(itr.hasNext()){
                documents.add((JSONObject) itr.next());
            }

        } catch (IOException e) {
            //TODO
        } catch (ParseException e) {
            //TODO
        }
    }

    /**
     * returns the instance's dictionary which holds the documents
     * @return dictionary
     */
    public List<JSONObject> getDictionary(){
        return documents;
    }

    /**
     * returns the instance of the singleton
     * @return the instance
     */
    public static DocumentDictionary getInstance() {
        if(instance == null){
            instance = new DocumentDictionary();
        }
        return instance;
    }
}