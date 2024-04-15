package com.example.markethibernate.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public static Integer parseInt(String idString) {
        try {
            return Integer.parseInt(idString);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public static String dateToStringShort(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return dateTime.format(formatter);
    }

    public static String moneyToString(Integer money) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(money);
    }

}
