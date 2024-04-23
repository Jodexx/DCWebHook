package com.jodexindustries.dcwebhook.bootstrap;

import com.jodexindustries.dcwebhook.tools.CustomConfig;
import com.jodexindustries.dcwebhook.tools.Tools;
import com.jodexindustries.donatecase.api.CaseManager;
import com.jodexindustries.donatecase.api.SubCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class DCWebHookPlugin extends JavaPlugin implements DCWebHook {
    public static CustomConfig customConfig;
    private CaseManager api;

    @Override
    public void onEnable() {
        api = new CaseManager(this);
        Tools.load(this);
        saveConfig();
        reloadConfig();
    }
    @Override
    public void onDisable() {
        api.getSubCommandManager().unregisterSubCommand("webhook");
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
        return this;
    }

    @Override
    public Logger getAddonLogger() {
        return getLogger();
    }

    @Override
    public CaseManager getAPI() {
        return api;
    }

    public void saveConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }
}
