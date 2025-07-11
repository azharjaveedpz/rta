package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties initProperties() {
        Properties prop = new Properties();
        try {
            // Load global config
            FileInputStream globalFis = new FileInputStream("src/test/resources/config/global.properties");
            prop.load(globalFis);

            // Determine environment
            String env = System.getProperty("env", prop.getProperty("env", "mqa"));
 // prefer system property
            String envFilePath = String.format("src/test/resources/config/%s.properties", env);

            // Load environment config
            File envFile = new File(envFilePath);
            if (!envFile.exists()) {
                throw new RuntimeException("Missing environment file: " + envFilePath);
            }

            // Load env-specific file (overrides global)
            FileInputStream envFis = new FileInputStream(envFile);
            prop.load(envFis);

        } catch (IOException e) {
            throw new RuntimeException("Error loading config files", e);
        }

        return prop;
    }
}