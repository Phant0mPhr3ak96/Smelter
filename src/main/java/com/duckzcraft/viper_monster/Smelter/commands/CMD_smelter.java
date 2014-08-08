package com.duckzcraft.viper_monster.Smelter.commands;

import com.duckzcraft.viper_monster.Smelter.Main;
import com.duckzcraft.viper_monster.Smelter.utilities.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CMD_smelter extends AbstractCommand {

    public CMD_smelter(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("smelter.reload")) {
                    sendMessage(sender, ConfigUtils.getMessage("no-permission"));
                    return true;
                }

                plugin.reloadConfig();
                sendMessage(sender, ConfigUtils.getMessage("config-reload"));
                return true;
            }
        }
        return true;
    }
}