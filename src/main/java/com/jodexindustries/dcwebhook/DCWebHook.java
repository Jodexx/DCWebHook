package com.jodexindustries.dcwebhook;

import com.jodexindustries.dcwebhook.commands.MainCommand;
import com.jodexindustries.dcwebhook.events.EventListener;
import com.jodexindustries.dcwebhook.tools.CustomConfig;
import com.jodexindustries.donatecase.api.SubCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DCWebHook extends JavaPlugin {
    public static DCWebHook plugin;
    public static CustomConfig customConfig;

    @Override
    public void onEnable() {
        String ver = getServer().getPluginManager().getPlugin("DonateCase").getDescription().getVersion();
        ver = ver.replaceAll("\\.", "");
        int intVer = Integer.parseInt(ver);
        if(intVer < 2134) {
            getLogger().log(Level.SEVERE, "Unsupported version of the DonateCase! Use >2.1.3.4");
            getPluginLoader().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        SubCommandManager.registerSubCommand("webhook", new MainCommand());
    }
    public void reloadConfig() {
        customConfig = new CustomConfig();
    }
}
