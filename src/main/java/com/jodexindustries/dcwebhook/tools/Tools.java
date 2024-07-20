package com.jodexindustries.dcwebhook.tools;

import com.jodexindustries.dcwebhook.bootstrap.Main;
import com.jodexindustries.dcwebhook.commands.MainCommand;
import com.jodexindustries.dcwebhook.config.Config;
import com.jodexindustries.dcwebhook.events.EventListener;
import com.jodexindustries.donatecase.api.Case;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Tools {
    private final Main main;
    private final Config config;

    public Tools(Main main) {
        this.main = main;
        this.config = new Config(main);
    }

    public void load() {
        String ver = Case.getInstance().getDescription().getVersion();
        ver = ver.replaceAll("\\.", "");
        int intVer = Integer.parseInt(ver);
        if(intVer < 2134) {
            main.getLogger().log(Level.SEVERE, "Unsupported version of the DonateCase! Use >2.1.3.4");
            return;
        }

        Bukkit.getServer().getPluginManager().registerEvents(new EventListener(this), main.getPlugin());

        main.getCaseAPI().getSubCommandManager().registerSubCommand("webhook",
                new MainCommand(this));
    }

    public void unload() {
        main.getCaseAPI().getSubCommandManager().unregisterSubCommand("webhook");
    }

    public static String rc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public Main getMain() {
        return main;
    }

    public Config getConfig() {
        return config;
    }
}
