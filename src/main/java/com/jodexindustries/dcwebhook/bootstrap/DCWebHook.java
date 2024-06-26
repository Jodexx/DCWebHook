package com.jodexindustries.dcwebhook.bootstrap;

import com.jodexindustries.dcwebhook.tools.CustomConfig;
import com.jodexindustries.donatecase.api.CaseManager;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public interface DCWebHook {
    CustomConfig getAddonConfig();
    void reloadConfig();
    Plugin getPlugin();
    Logger getAddonLogger();
    CaseManager getAPI();
}
