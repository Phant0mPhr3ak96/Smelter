package com.duckzcraft.viper_monster.Smelter.commands;

import com.duckzcraft.viper_monster.Smelter.Main;
import com.duckzcraft.viper_monster.Smelter.utilities.ConfigUtils;
import com.duckzcraft.viper_monster.Smelter.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMD_smeltall extends AbstractCommand {

    public CMD_smeltall(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!isPlayer(sender)) {
            sendMessage(sender, ConfigUtils.getMessage("only-player"));
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("smelter.smeltall")) {
            sendMessage(sender, ConfigUtils.getMessage("no-permission"));
            return true;
        }

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
            sendMessage(player, ConfigUtils.getMessage("not-enough-items"));
            return true;
        }

        if (!hasEnough(player, price * amount)) {
            sendMessage(player, ConfigUtils.getMessage("no-money"));
            return true;
        }

        if (plugin.isOnCooldown(player)) {
            sendMessage(player, ConfigUtils.getMessage("on-cooldown"));
            return true;
        }

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (!Utils.isValidMaterial(itemStack.getType())) continue;
            ItemStack smelted = Utils.getSmeltedItemStack(itemStack);

            itemStack.setAmount(smelted.getAmount());
            itemStack.setItemMeta(smelted.getItemMeta());
            itemStack.setDurability(smelted.getDurability());
            itemStack.setType(smelted.getType());
            itemStack.setData(itemStack.getData());
        }

        withdrawPlayer(player, price * amount);
        sendMessage(player, ConfigUtils.getMessage("successful-all"));
        if (!player.hasPermission("smelter.nocooldown")) plugin.updateCooldown(player);
        return true;
    }
}