package com.TrongHuy.botchessgame;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    public static void load(String filePath) {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Không thể đọc file cấu hình: " + e.getMessage());
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }
}
