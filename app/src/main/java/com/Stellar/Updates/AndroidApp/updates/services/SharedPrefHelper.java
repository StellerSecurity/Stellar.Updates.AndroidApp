package com.Stellar.Updates.AndroidApp.updates.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.Stellar.Updates.AndroidApp.updates.MainApplication;


public class SharedPrefHelper {
    private static final String SHARED_PREFS_NAME = MainApplication.getAppContext().getPackageName();
    private static SharedPrefHelper instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefHelper() {
        instance = this;
        sharedPreferences = MainApplication.getAppContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefHelper getInstance() {
        if (instance == null) {
            instance = new SharedPrefHelper();
        }
        return instance;
    }


    public void save(String key, Object value) {
        final SharedPreferences.Editor editor = getEditor();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-supported preference");
        }
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defValue) {
        final T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }


    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

}
