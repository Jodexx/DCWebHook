package com.jodexindustries.dcwebhook.tools;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CustomConfig {
    private final FileConfiguration config;
    private final File configFile;
    public CustomConfig(File dataFolder) {
        configFile = new File(dataFolder, "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
    public File getConfigFile() {
        return configFile;
    }
}
