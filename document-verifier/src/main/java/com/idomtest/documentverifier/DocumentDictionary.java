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

public class DocumentDictionary {
    private static DocumentDictionary instance;
    private List<JSONObject> documents;

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

    public List<JSONObject> getDictionary(){
        return documents;
    }

    public static DocumentDictionary getInstance() {
        if(instance == null){
            instance = new DocumentDictionary();
        }
        return instance;
    }
}
