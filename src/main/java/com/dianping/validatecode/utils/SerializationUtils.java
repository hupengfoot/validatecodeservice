package com.dianping.validatecode.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializationUtils {
    private SerializationUtils() {

    }

    public static <T> String serialize(T o) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.toJson(o);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T deSerialize(String src, Type typeOfT) {
        try {
            Gson gson = new GsonBuilder().create();
            return (T) gson.fromJson(src, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T deSerialize(String src, Type typeOfT, String timePattern) {
        try {
            Gson gson = new GsonBuilder().setDateFormat(timePattern).create();
            return (T) gson.fromJson(src, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

}
