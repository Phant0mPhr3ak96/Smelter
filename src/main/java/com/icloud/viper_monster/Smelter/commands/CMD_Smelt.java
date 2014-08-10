package com.icloud.viper_monster.Smelter.commands;

import com.icloud.viper_monster.Smelter.Main;
import com.icloud.viper_monster.Smelter.enums.Lang;
import com.icloud.viper_monster.Smelter.enums.Permissions;
import com.icloud.viper_monster.Smelter.utilities.ConfigUtils;
import com.icloud.viper_monster.Smelter.utilities.TempStorage;
import com.icloud.viper_monster.Smelter.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.RED;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:12
 */
public class CMD_Smelt extends AbstractCommand {

    public CMD_Smelt(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("smelt")) return true;

        if (!Permissions.SMELT.isAllowed(sender)) {
            Lang.NO_PERMISSION.send(sender);
            return true;
        }

        if (!isPlayer(sender)) {
            Lang.ONLY_PLAYER_COMMAND.send(sender);
            return true;
        }

        if (args.length > 0) {
            sender.sendMessage(DARK_RED + "Usage: " + RED + command.getUsage().replaceAll("<command>", label));
            return true;
        }

        Player player = (Player) sender;

        if (!Utils.isValidMaterial(player.getItemInHand().getType())) {
            Lang.INVALID_ITEM.send(sender);
            return true;
        }

        if (ConfigUtils.isPerItemPermission() && !Utils.hasItemPermission(player, false, player.getItemInHand().getType())) {
            Lang.NO_ITEM_PERMISSION.send(sender);
            return true;
        }

        int amount = player.getItemInHand().getAmount();
        double price = ConfigUtils.getSmeltPerRankCost(getPrimaryGroup(player));
        boolean isEconomyEnabled = ConfigUtils.isEconomy();

        if (isEconomyEnabled && !hasEnough(player, price * amount) && !Permissions.SMELT_FREE.isAllowed(sender)) {
            String message = Lang.NOT_ENOUGH_MONEY.toString();
            message = message.replaceAll("%money_needed", price + "");

            player.sendMessage(message);
            return true;
        }

        if (TempStorage.isOnCooldown(player.getUniqueId(), false)) {
            String message = Lang.ON_COOLDOWN.toString();
            message = message.replaceAll("%time_left", TempStorage.getFormattedTimeLeft(player.getUniqueId(), false) + "");

            player.sendMessage(message);
            return true;
        }

        ItemStack smelted = Utils.getSmeltedItemStack(player.getItemInHand());

        player.setItemInHand(smelted);
        if (isEconomyEnabled && !Permissions.SMELT_FREE.isAllowed(sender)) withdrawPlayer(player, price * amount);
        Lang.SMELTED_one.send(player);
        if (!Permissions.SMELT_NO_COOLDOWN.isAllowed(player)) TempStorage.updateCooldown(player.getUniqueId(), false);
        return true;
    }
}