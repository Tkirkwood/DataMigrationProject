package com.sparta.thomas.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

    private static Properties properties = new Properties();


    public static String getProperty(String property) {
        try {
            properties.load(new FileReader("resources/login.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty(property);
    }

}
