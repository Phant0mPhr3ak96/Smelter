package com.icloud.viper_monster.Smelter.utilities;

import com.icloud.viper_monster.Smelter.Main;
import org.bukkit.Material;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public final class ConfigUtils {

    private ConfigUtils() {
    }

    public static boolean isPerItemPermission() {
        return Main.get().getConfig().getBoolean("per-item-permissions", false);
    }

    public static Material getMaterialForSmelting() {
        Material material = Material.getMaterial(Main.get().getConfig().getString("items-required-for-smelting.item", "COAL"));
        if (material == null) return Material.COAL;
        return material;
    }

    public static boolean isEconomy() {
        return !Main.get().getConfig().getBoolean("items-required-for-smelting.enabled", false);
    }

    public static double getSmeltPerRankCost(String rank) {
        return Main.get().getConfig().getDouble("ranks." + rank + ".cost", 0);
    }

    public static long getSmeltPerRankCooldown(String rank, boolean all) {
        String key = all ? "all" : "one";
        return Main.get().getConfig().getLong("ranks." + rank + ".cooldown." + key, 0);
    }

    public static String getMessage(String value) {
        return Main.get().getConfig().getString("messages." + value, "null");
    }

    public static String getPrefix() {
        return getMessage("prefix");
    }
}