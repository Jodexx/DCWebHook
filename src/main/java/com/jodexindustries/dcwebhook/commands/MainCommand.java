package com.jodexindustries.dcwebhook.commands;

import com.jodexindustries.dcwebhook.DCWebHook;
import com.jodexindustries.dcwebhook.tools.Tools;
import com.jodexindustries.donatecase.api.SubCommand;
import com.jodexindustries.donatecase.api.SubCommandType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Tools.rc("&a/dc webhook reload"));
        } else {
            if(args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(Tools.rc("&aConfig reloaded!"));
                DCWebHook.plugin.reloadConfig();
            }
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        if(args.length < 1) {
            List<String> completions = new ArrayList<>();
            completions.add("webhook");
            return completions;
        } else if(args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("reload");
            return completions;
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public SubCommandType getType() {
        return SubCommandType.ADMIN;
    }
    @Override
    public String[] getArgs() {
        String[] args = new String[2];
        args[0] = "webhook";
        args[1] = "reload";
        return args;
    }
    @Override
    public String getDescription() {
        return "Reload config";
    }
}
