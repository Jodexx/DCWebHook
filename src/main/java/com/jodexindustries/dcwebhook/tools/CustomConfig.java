package com.jodexindustries.dcwebhook.tools;

import com.jodexindustries.dcwebhook.DCWebHook;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CustomConfig {
    private final FileConfiguration config;
    public CustomConfig() {
        File configFile = new File(DCWebHook.plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
