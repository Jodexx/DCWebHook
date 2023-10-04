package com.jodexindustries.dcwebhook.events;

import com.jodexindustries.dcwebhook.DCWebHook;
import com.jodexindustries.dcwebhook.tools.DiscordWebhook;
import com.jodexindustries.donatecase.api.events.AnimationEndEvent;
import com.jodexindustries.donatecase.tools.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.io.IOException;

public class EventListener implements Listener {
    @EventHandler
    public void onAnimationEnd(AnimationEndEvent e) {
        String player = e.getPlayer().getName();
        String animation = e.getAnimation();
        String caseType = e.getCaseType();
        String winGroup = e.getWinGroup();
        String caseTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', new CustomConfig().getConfig().getString("DonatCase.Cases." + caseType + ".Title")));
        Bukkit.getScheduler().runTaskAsynchronously(DCWebHook.plugin,  () -> {
            String webhook = DCWebHook.customConfig.getConfig().getString("Webhook");
            if(!webhook.isEmpty()) {
                DiscordWebhook.EmbedObject object = new DiscordWebhook.EmbedObject();
                DiscordWebhook discordWebhook = new DiscordWebhook(webhook);
                String title = DCWebHook.customConfig.getConfig().getString("Embed.Title");
                String authorName = DCWebHook.customConfig.getConfig().getString("Embed.Author.Name")
                        .replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String description = DCWebHook.customConfig.getConfig().getString("Embed.Description").replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String footerText = DCWebHook.customConfig.getConfig().getString("Embed.Footer.Text").replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String footerIcon = DCWebHook.customConfig.getConfig().getString("Embed.Footer.Icon");
                ConfigurationSection fieldsSection = DCWebHook.customConfig.getConfig().getConfigurationSection("Embed.Fields");
                object.setTitle(title);
                for (String field : fieldsSection.getKeys(false)) {
                    String fieldTitle = DCWebHook.customConfig.getConfig().getString("Embed.Fields." + field + ".Title").replaceAll("%player%", player)
                            .replaceAll("%animation%", animation)
                            .replaceAll("%wingroup%", winGroup)
                            .replaceAll("%casetitle%", caseTitle)
                            .replaceAll("%casetype%", caseType);
                    String fieldValue = DCWebHook.customConfig.getConfig().getString("Embed.Fields." + field + ".Value").replaceAll("%player%", player)
                            .replaceAll("%animation%", animation)
                            .replaceAll("%wingroup%", winGroup)
                            .replaceAll("%casetitle%", caseTitle)
                            .replaceAll("%casetype%", caseType);
                    boolean inline = DCWebHook.customConfig.getConfig().getBoolean("Embed.Fields." + field + ".Inline");
                    object.addField(fieldTitle, fieldValue, inline);
                }
                if (!authorName.isEmpty()) {
                    object.setAuthor(authorName,
                            DCWebHook.customConfig.getConfig().getString("Embed.Author.Url"),
                            DCWebHook.customConfig.getConfig().getString("Embed.Author.Icon"));
                }
                if (!description.isEmpty()) {
                    object.setDescription(description);
                }
                if (!footerText.isEmpty() || footerIcon.isEmpty()) {
                    object.setFooter(footerText, footerIcon);
                }
                int r = DCWebHook.customConfig.getConfig().getInt("Embed.Color.r");
                int g = DCWebHook.customConfig.getConfig().getInt("Embed.Color.g");
                int b = DCWebHook.customConfig.getConfig().getInt("Embed.Color.b");
                object.setColor(new Color(r, g, b));
                discordWebhook.addEmbed(object);
                try {
                    discordWebhook.execute();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
