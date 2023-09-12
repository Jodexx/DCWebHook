package com.jodexindustries.dcwebhook.tools;

import org.bukkit.ChatColor;

public class Tools {
    public static String rc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
