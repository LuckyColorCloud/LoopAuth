package com.sobercoding.loopauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.*;

/**
 * json工具类
 * @author: Sober
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Object jsonToObj(String jsonStr, Class<?> clazz) {
        Object obj = null;
        try {
            obj = MAPPER.readValue(jsonStr, clazz);
        }catch (IOException e){
        }
        return obj;
    }

    public static String objToJson(Object obj) {
        String json = null;
        try{
            json = MAPPER.writeValueAsString(obj);
        }catch (JsonProcessingException e){
        }
        return json;
    }

    public static <T> Set<T> jsonToList(String jsonStr,Class<?> clazz) {
        Set<T> userList = null;
        try {
            CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(Set.class, clazz);
            userList = MAPPER.readValue(jsonStr, listType);
        } catch (IOException e) {
        }
        return userList;
    }

}
