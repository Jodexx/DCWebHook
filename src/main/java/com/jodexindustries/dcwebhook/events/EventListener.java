package com.jodexindustries.dcwebhook.events;

import com.jodexindustries.dcwebhook.bootstrap.DCWebHook;
import com.jodexindustries.dcwebhook.tools.DiscordWebhook;
import com.jodexindustries.donatecase.api.events.AnimationEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.io.IOException;

public class EventListener implements Listener {
    private final DCWebHook dcWebHook;
    public EventListener(DCWebHook dcWebHook) {
        this.dcWebHook = dcWebHook;
    }
    @EventHandler
    public void onAnimationEnd(AnimationEndEvent e) {
        String player = e.getPlayer().getName();
        String animation = e.getAnimation();
        String caseType = e.getCaseData().getCaseName();
        String winGroup = e.getWinItem().getGroup();
        String caseTitle = ChatColor.stripColor(e.getCaseData().getCaseTitle());
        Bukkit.getScheduler().runTaskAsynchronously(dcWebHook.getPlugin(),  () -> {
            String webhook = dcWebHook.getAddonConfig().getConfig().getString("Webhook");
            if(!webhook.isEmpty()) {
                DiscordWebhook.EmbedObject object = new DiscordWebhook.EmbedObject();
                DiscordWebhook discordWebhook = new DiscordWebhook(webhook);
                String title = dcWebHook.getAddonConfig().getConfig().getString("Embed.Title");
                String authorName = dcWebHook.getAddonConfig().getConfig().getString("Embed.Author.Name")
                        .replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String description = dcWebHook.getAddonConfig().getConfig().getString("Embed.Description").replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String footerText = dcWebHook.getAddonConfig().getConfig().getString("Embed.Footer.Text").replaceAll("%player%", player)
                        .replaceAll("%animation%", animation)
                        .replaceAll("%wingroup%", winGroup)
                        .replaceAll("%casetitle%", caseTitle)
                        .replaceAll("%casetype%", caseType);
                String footerIcon = dcWebHook.getAddonConfig().getConfig().getString("Embed.Footer.Icon");
                ConfigurationSection fieldsSection = dcWebHook.getAddonConfig().getConfig().getConfigurationSection("Embed.Fields");
                object.setTitle(title);
                for (String field : fieldsSection.getKeys(false)) {
                    String fieldTitle = dcWebHook.getAddonConfig().getConfig().getString("Embed.Fields." + field + ".Title").replaceAll("%player%", player)
                            .replaceAll("%animation%", animation)
                            .replaceAll("%wingroup%", winGroup)
                            .replaceAll("%casetitle%", caseTitle)
                            .replaceAll("%casetype%", caseType);
                    String fieldValue = dcWebHook.getAddonConfig().getConfig().getString("Embed.Fields." + field + ".Value").replaceAll("%player%", player)
                            .replaceAll("%animation%", animation)
                            .replaceAll("%wingroup%", winGroup)
                            .replaceAll("%casetitle%", caseTitle)
                            .replaceAll("%casetype%", caseType);
                    boolean inline = dcWebHook.getAddonConfig().getConfig().getBoolean("Embed.Fields." + field + ".Inline");
                    object.addField(fieldTitle, fieldValue, inline);
                }
                if (!authorName.isEmpty()) {
                    object.setAuthor(authorName,
                            dcWebHook.getAddonConfig().getConfig().getString("Embed.Author.Url"),
                            dcWebHook.getAddonConfig().getConfig().getString("Embed.Author.Icon"));
                }
                if (!description.isEmpty()) {
                    object.setDescription(description);
                }
                if (!footerText.isEmpty() || footerIcon.isEmpty()) {
                    object.setFooter(footerText, footerIcon);
                }
                int r = dcWebHook.getAddonConfig().getConfig().getInt("Embed.Color.r");
                int g = dcWebHook.getAddonConfig().getConfig().getInt("Embed.Color.g");
                int b = dcWebHook.getAddonConfig().getConfig().getInt("Embed.Color.b");
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
