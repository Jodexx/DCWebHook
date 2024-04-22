package com.jodexindustries.dcwebhook.bootstrap;

import com.jodexindustries.dcwebhook.tools.CustomConfig;
import com.jodexindustries.dcwebhook.tools.Tools;
import com.jodexindustries.donatecase.api.SubCommandManager;
import com.jodexindustries.donatecase.api.addon.JavaAddon;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public class DCWebHookAddon extends JavaAddon implements DCWebHook {
    public static CustomConfig customConfig;
    @Override
    public void onEnable() {
        Tools.load(this);
        saveConfig();
        reloadConfig();
    }
    @Override
    public void onDisable() {
        SubCommandManager.unregisterSubCommand("webhook");
    }

    public void reloadConfig() {
        customConfig = new CustomConfig(getDataFolder());
    }
    @Override
    public CustomConfig getAddonConfig() {
        return customConfig;
    }

    @Override
    public Plugin getPlugin() {
        return getDonateCase();
    }

    @Override
    public Logger getAddonLogger() {
        return getLogger();
    }
    public void saveConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }
}
