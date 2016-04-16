package com.submitdatas.utils;

import com.demo.Person;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 彪 on 2016/4/15.
 */
public class JsonTools {

    public static Person getPerson(String key, String jsonString) {
        Person person = new Person();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject personObject = jsonObject.getJSONObject(key);

            person.setId(personObject.getInt("id"));
            person.setName(personObject.getString("name"));
            person.setAddress(personObject.getString("address"));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return person;
    }

    public static List<Person> getPersons(String key, String jsonString) {
        List<Person> list = new ArrayList<Person>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Person person = new Person();
                person.setId(jsonObject2.getInt("id"));
                person.setName(jsonObject2.getString("name"));
                person.setAddress(jsonObject2.getString("address"));
                list.add(person);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static List<String> getlistString(String key, String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {

                String msg = jsonArray.getString(i);
                list.add(msg);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;
    }

    public static List<Map<String,Object>> getlistMap(String key, String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Map<String,Object> map=new HashMap<String, Object>();
                Iterator<String> iterator=jsonObject2.keys();

                while(iterator.hasNext()){
                    String json_key=iterator.next();
                    Object json_value=jsonObject2.get(json_key);
                    if(json_value==null){
                        json_value="";
                    }
                    map.put(json_key, json_value);
                }
                list.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
