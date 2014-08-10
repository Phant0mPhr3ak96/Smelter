package com.icloud.viper_monster.Smelter.commands;

import com.icloud.viper_monster.Smelter.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public abstract class AbstractCommand implements CommandExecutor {

    public Main plugin;

    public AbstractCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public boolean hasEnough(Player player, double amount) {
        return plugin.getEconomy().has(player, amount);
    }

    public void withdrawPlayer(Player player, double amount) {
        plugin.getEconomy().withdrawPlayer(player, amount);
    }

    public String getPrimaryGroup(Player player) {
        return plugin.getPermission().getPrimaryGroup(player);
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args);
}