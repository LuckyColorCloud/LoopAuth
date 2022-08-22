package com.sobercoding.loopauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sobercoding.loopauth.model.TokenModel;

import java.io.IOException;
import java.util.*;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/08 17:43
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Object jsonToObj(String jsonStr, Class<?> clazz) {
        Object obj = null;
        try {
            obj = MAPPER.readValue(jsonStr, clazz);
        }catch (IOException e){
            e.printStackTrace();
        }
        return obj;
    }

    public static String objToJson(Object obj) {
        String json = null;
        try{
            json = MAPPER.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }

    public static <T> Set<T> jsonToList(String jsonStr,Class<?> clazz) {
        Set<T> userList = null;
        try {
            CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(Set.class, clazz);
            userList = MAPPER.readValue(jsonStr, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static void main(String[] args) {
        TokenModel tokenModel = new TokenModel().setValue("sad");
        System.out.println(objToJson(tokenModel));
    }

}
