package com.icloud.viper_monster.Smelter.commands;

import com.icloud.viper_monster.Smelter.Main;
import com.icloud.viper_monster.Smelter.enums.Lang;
import com.icloud.viper_monster.Smelter.enums.Permissions;
import com.icloud.viper_monster.Smelter.utilities.ConfigUtils;
import com.icloud.viper_monster.Smelter.utilities.TempStorage;
import com.icloud.viper_monster.Smelter.utilities.Utils;
import org.bukkit.Material;
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
public class CMD_SmeltAll extends AbstractCommand {

    public CMD_SmeltAll(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("smeltall")) return true;

        if (!Permissions.SMELTALL.isAllowed(sender)) {
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
        double price = ConfigUtils.getSmeltPerRankCost(getPrimaryGroup(player));
        int amount = 0;

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (!Utils.isValidMaterial(itemStack.getType())) continue;
            if (ConfigUtils.isPerItemPermission() && !Utils.hasItemPermission(player, true, itemStack.getType())) {
                continue;
            }
            amount += itemStack.getAmount();
        }

        if (amount <= 0) {
            Lang.NO_SMELTABLE_ITEMS.send(sender);
            return true;
        }

        boolean isEconomyEnabled = ConfigUtils.isEconomy();

        if (isEconomyEnabled && !hasEnough(player, price * amount)) {
            String message = Lang.NOT_ENOUGH_MONEY.toString();
            message = message.replaceAll("%money_needed", price + "");

            player.sendMessage(message);
            return true;
        }

        if (TempStorage.isOnCooldown(player.getUniqueId(), true)) {
            String message = Lang.ON_COOLDOWN.toString();
            message = message.replaceAll("%time_left", TempStorage.getFormattedTimeLeft(player.getUniqueId(), true) + "");

            player.sendMessage(message);
            return true;
        }

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (!Utils.isValidMaterial(itemStack.getType())) continue;
            if (ConfigUtils.isPerItemPermission() && !Utils.hasItemPermission(player, true, itemStack.getType())) {
                continue;
            }
            ItemStack smelted = Utils.getSmeltedItemStack(itemStack);

            itemStack.setAmount(smelted.getAmount());
            itemStack.setItemMeta(smelted.getItemMeta());
            itemStack.setDurability(smelted.getDurability());
            itemStack.setType(smelted.getType());
            itemStack.setData(itemStack.getData());
        }

        if (isEconomyEnabled && !Permissions.SMELTALL_FREE.isAllowed(sender)) withdrawPlayer(player, price * amount);
        Lang.SMELTED_all.send(player);
        if (!Permissions.SMELTALL_NO_COOLDOWN.isAllowed(player)) {
            TempStorage.updateCooldown(player.getUniqueId(), false);
        }
        return true;
    }
}