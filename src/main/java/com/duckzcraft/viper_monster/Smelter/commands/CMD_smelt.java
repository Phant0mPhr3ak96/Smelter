package com.duckzcraft.viper_monster.Smelter.commands;

import com.duckzcraft.viper_monster.Smelter.Main;
import com.duckzcraft.viper_monster.Smelter.utilities.ConfigUtils;
import com.duckzcraft.viper_monster.Smelter.utilities.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMD_smelt extends AbstractCommand {

    public CMD_smelt(Main plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!isPlayer(sender)) {
            sendMessage(sender, ConfigUtils.getMessage("only-player"));
            return true;
        }

        if (!sender.hasPermission("smelter.smelt")) {
            sendMessage(sender, ConfigUtils.getMessage("no-permission"));
            return true;
        }

        Player player = (Player) sender;

        if (!Utils.isValidMaterial(player.getItemInHand().getType())) {
            sendMessage(player, ConfigUtils.getMessage("not-valid-type"));
            return true;
        }

        if (ConfigUtils.isPerItemPermission() && !Utils.hasItemPermission(player, false, player.getItemInHand().getType())) {
            sendMessage(player, ConfigUtils.getMessage("no-permission"));
            return true;
        }

        int amount = player.getItemInHand().getAmount();
        double price = ConfigUtils.getSmeltPerRankCost(getPrimaryGroup(player));
        //boolean isEconomyEnabled = ConfigUtils.isEconomy();

        if (/*isEconomyEnabled && */!hasEnough(player, price * amount)) {
            sendMessage(player, ConfigUtils.getMessage("no-money"));
            return true;
        }

        if (plugin.isOnCooldown(player)) {
            sendMessage(player, ConfigUtils.getMessage("on-cooldown"));
            return true;
        }

        ItemStack smelted = Utils.getSmeltedItemStack(player.getItemInHand());

        player.setItemInHand(smelted);
        /*if (isEconomyEnabled) */
        withdrawPlayer(player, price * amount);
        sendMessage(player, ConfigUtils.getMessage("successful-one"));
        if (!player.hasPermission("smelter.nocooldown")) plugin.updateCooldown(player);
        return true;
    }
}