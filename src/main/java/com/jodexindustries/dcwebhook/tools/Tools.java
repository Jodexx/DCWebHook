package com.jodexindustries.dcwebhook.tools;

import com.jodexindustries.dcwebhook.bootstrap.DCWebHook;
import com.jodexindustries.dcwebhook.commands.MainCommand;
import com.jodexindustries.dcwebhook.events.EventListener;
import com.jodexindustries.donatecase.api.SubCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Tools {
    public static void load(DCWebHook dcWebHook) {
        String ver = Bukkit.getServer().getPluginManager().getPlugin("DonateCase").getDescription().getVersion();
        ver = ver.replaceAll("\\.", "");
        int intVer = Integer.parseInt(ver);
        if(intVer < 2134) {
            dcWebHook.getAddonLogger().log(Level.SEVERE, "Unsupported version of the DonateCase! Use >2.1.3.4");
            return;
        }
        Bukkit.getServer().getPluginManager().registerEvents(new EventListener(dcWebHook), dcWebHook.getPlugin());
        SubCommandManager.registerSubCommand("webhook", new MainCommand(dcWebHook));
    }
    public static String rc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
