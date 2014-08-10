package com.icloud.viper_monster.Smelter.enums;

import com.icloud.viper_monster.Smelter.Main;
import com.icloud.viper_monster.Smelter.utilities.Utils;
import org.bukkit.command.CommandSender;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public enum Lang {

    TITLE("title", "&4[&6Smelter&4]&r "),
    ONLY_PLAYER_COMMAND("only-player-command", "&cThis command can only be run by a player."),
    NO_PERMISSION("no-permission", "&cYou don't have permission to perform this action."),
    NO_ITEM_PERMISSION("no-item-permission", "&cYou don't have permission to smelt this item."),
    NO_SMELTABLE_ITEMS("no-smeltable-items", "&cYou have no smeltable items in your inventory."),
    CONFIGS_RELOADED("config-reloaded", "&aYou have reloaded all configuration files."),
    SMELTED_one("smelted-one", "&aItem from your hand was successfully smelted."),
    SMELTED_all("smelted-all", "&aEvery smeltable item from your inventory was smelted."),
    NOT_ENOUGH_MONEY("not-enough-money", "&cYou don't have enough money. Money needed: &7%money_needed&c."),
    INVALID_ITEM("invalid-item", "&cPlease use a valid item to smelt."),
    ON_COOLDOWN("on-cooldown", "&cYou are on a cooldown for &7%time_left &cmore seconds.");

    private String message;

    Lang(String path, String defaultMessage) {
        this.message = Main.get().getLangConfig().getString(path, defaultMessage);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return Utils.colorize(TITLE.getMessage()) + Utils.colorize(getMessage());
    }

    public void send(CommandSender sender) {
        sender.sendMessage(this.toString());
    }
}