package com.duckzcraft.viper_monster.Smelter.commands;

import com.duckzcraft.viper_monster.Smelter.Main;
import com.duckzcraft.viper_monster.Smelter.utilities.ConfigUtils;
import com.duckzcraft.viper_monster.Smelter.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    public Main plugin;

    public AbstractCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Utils.translateColors(ConfigUtils.getPrefix() + message));
    }

    public boolean hasEnough(Player player, double amount) {
        return plugin.getEconomy().has(player, amount);
    }

    public void withdrawPlayer(Player player, double amount) {
        plugin.getEconomy().withdrawPlayer(player, amount);
    }

    public String format(double amount) {
        return plugin.getEconomy().format(amount);
    }

    public String getPrimaryGroup(Player player) {
        return plugin.getPermission().getPrimaryGroup(player);
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args);
}