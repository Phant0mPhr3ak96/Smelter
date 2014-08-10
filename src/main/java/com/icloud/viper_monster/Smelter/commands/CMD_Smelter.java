package com.icloud.viper_monster.Smelter.commands;

import com.icloud.viper_monster.Smelter.Main;
import com.icloud.viper_monster.Smelter.enums.Lang;
import com.icloud.viper_monster.Smelter.enums.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.RED;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:12
 */
public class CMD_Smelter extends AbstractCommand {

    public CMD_Smelter(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("smelter")) return true;

        if (!Permissions.ADMIN.isAllowed(sender)) {
            Lang.NO_PERMISSION.send(sender);
            return true;
        }

        if (args.length < 1 || args.length > 1) {
            sender.sendMessage(DARK_RED + "Usage: " + RED + command.getUsage().replaceAll("<command>", label));
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "reload":
                case "r": {
                    Main.get().getConfig().reload();
                    Main.get().getLangConfig().reload();
                    Lang.CONFIGS_RELOADED.send(sender);
                    return true;
                }
            }

            sender.sendMessage(DARK_RED + "Usage: " + RED + command.getUsage().replaceAll("<command>", label));
            return true;
        }
        return true;
    }
}