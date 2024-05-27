package com.jodexindustries.dcwebhook.bootstrap;

import com.jodexindustries.dcwebhook.tools.CustomConfig;
import com.jodexindustries.dcwebhook.tools.Tools;
import com.jodexindustries.donatecase.api.CaseManager;
import com.jodexindustries.donatecase.api.addon.internal.InternalJavaAddon;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public final class DCWebHookAddon extends InternalJavaAddon implements DCWebHook {
    public static CustomConfig customConfig;
    private CaseManager api;
    @Override
    public void onEnable() {
        api = getCaseAPI();
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
        return getDonateCase();
    }

    @Override
    public Logger getAddonLogger() {
        return getLogger();
    }

    @Override
    public CaseManager getAPI() {
        return null;
    }

    public void saveConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }
}
