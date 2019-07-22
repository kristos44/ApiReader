package com.mycompany.app.properties;

import com.sun.tools.javac.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties getProperties() {
        ClassLoader loader = Main.class.getClassLoader();
        InputStream in = loader.getResourceAsStream("app.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
