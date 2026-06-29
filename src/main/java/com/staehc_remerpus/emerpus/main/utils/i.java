package com.staehc_remerpus.emerpus.main.utils;

import java.lang.reflect.Field;

public class i {

    public static void setGameMode(Class<?> targetClass, Object object, Object value) {
        if (object == null) return;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (targetClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public static Object getGameMode(Class<?> targetClass, Object object) {
        if (object == null) return null;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (targetClass.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}