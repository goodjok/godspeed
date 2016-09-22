package com.godspeed.source.util.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class JsonConvertUtils {
    private static ObjectMapper objectMapper;
    static{
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static String getStringValue(Object obj) {
        if (obj == null)
            return null;

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static Object getModelValue(String data, Class clazz) {
        if (data == null)
            return null;

        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Object getModelValue(String data, JavaType clazzType) {
        if (data == null)
            return null;

        try {
            return objectMapper.readValue(data, clazzType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JavaType getJavaType(Class typeClass, Class rawTypeClass) {

        try {
            return objectMapper.getTypeFactory().constructParametricType(typeClass, rawTypeClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JavaType getJavaType(Class typeClass) {

        try {
            return objectMapper.getTypeFactory().uncheckedSimpleType(typeClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
