package javaroke.recommendation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerConfigLoader {
    private static final Logger LOGGER = Logger.getLogger(ControllerConfigLoader.class.getName());
    private static final String CONFIG_PATH =
            "src/main/java/javaroke/recommendation/controllers/config.json";

    public static String get(String key) {
        return loadConfig().get(key);
    }

    public static Map<String, String> loadConfig() {
        try {
            Map<String, String> configMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            if (!Files.exists(Paths.get(CONFIG_PATH))) {
                throw new IllegalArgumentException(
                        "Config file not found: " + Paths.get(CONFIG_PATH));
            }

            configMap = mapper.readValue(new File(CONFIG_PATH), Map.class);

            LOGGER.info("Config loaded successfully.");
            return configMap;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load config: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
