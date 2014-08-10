package com.icloud.viper_monster.Smelter.enums;

import org.bukkit.command.CommandSender;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public enum Permissions {

    ADMIN("smelter.admin"), //Allows you to reload config files
    SMELT("smelter.smelt"), //Allows you to use /smelt
    SMELT_FREE("smelter.smelt.free"), //Allows you to use /smelt without any costs
    SMELT_NO_COOLDOWN("smelter.smelt.no-cooldown"), //Allows you to use /smelt without a cooldown
    SMELTALL("smelter.smeltall"), //Allows you to use /smeltall
    SMELTALL_FREE("smelter.smeltall.free"), //Allows you to use /smeltall without any costs
    SMELTALL_NO_COOLDOWN("smelter.smeltall.no-cooldown"); //Allows you to use /smeltall without a cooldown

    private String node;

    Permissions(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public boolean isAllowed(CommandSender sender) {
        return sender.isOp() || sender.hasPermission(this.getNode());
    }
}