package javaroke.recommendation.core.version;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersionConfigLoader {
    private static final Logger LOGGER = Logger.getLogger(VersionConfigLoader.class.getName());
    private String CONFIG_PATH;
    private Map<String, String> configMap;

    public VersionConfigLoader(String path) {
        this.CONFIG_PATH = path;
        this.configMap = loadConfig();
    }

    public String get(String key) {
        if (configMap == null) {
            configMap = loadConfig();
        }

        return configMap.get(key);
    }

    public void updateConfig() {
        configMap = loadConfig();
    }

    public Map<String, String> loadConfig() {
        try {
            Map<String, String> configMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            if (!Files.exists(Paths.get(CONFIG_PATH))) {
                throw new IllegalArgumentException(
                        "Config file not found: " + Paths.get(CONFIG_PATH));
            }

            configMap = mapper.readValue(new File(CONFIG_PATH), Map.class);

            LOGGER.log(Level.INFO, "Version config loaded successfully: {0}", CONFIG_PATH);
            return configMap;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load config: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
