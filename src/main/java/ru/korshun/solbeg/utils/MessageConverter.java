package ru.korshun.solbeg.utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {

    private final Gson gson = new Gson();

    public <T> T extractMessage(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public String toJson(Object message) {
        return gson.toJson(message);
    }
}
