package com.example.markethibernate.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;

public class AppUtil {
    public static String toJson(Object o) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        return gson.toJson(o);
    }

    public static Long parseId(String idString) {
        try {
            return Long.parseLong(idString);
        } catch (Exception ex) {
            return null;
        }
    }


}
