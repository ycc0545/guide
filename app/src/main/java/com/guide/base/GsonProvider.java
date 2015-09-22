package com.guide.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private static GsonProvider instance;

    private final Gson gson;

    private GsonProvider() {
        gson = new GsonBuilder().
                create();

    }

    public synchronized static GsonProvider getInstance() {
        if (instance == null) {
            instance = new GsonProvider();
        }
        return instance;
    }

    public Gson get() {
        return gson;
    }


}
