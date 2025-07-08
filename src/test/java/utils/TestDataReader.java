package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataReader {

    private static final String BASE_PATH = "src/test/resources/testdata/";

    public static Properties load(String fileName, Properties config) {
        Properties prop = new Properties();

        // ✅ Strictly require 'env' key from config
        String env = config.getProperty("env");
        if (env == null || env.trim().isEmpty()) {
            throw new RuntimeException(" 'env' is missing in configuration. Please define it in global.properties or pass as -Denv=qa");
        }

        String filePath = BASE_PATH + env.toLowerCase() + "/" + fileName + ".properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(" Failed to load test data file: " + filePath, e);
        }

        return prop;
    }
}
