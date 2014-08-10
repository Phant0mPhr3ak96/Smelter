package com.icloud.viper_monster.Smelter.utilities;

import com.google.common.collect.HashBasedTable;
import com.icloud.viper_monster.Smelter.Main;

import java.util.UUID;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:42
 */
public final class TempStorage {

    private TempStorage() {
    }

    private static final HashBasedTable<UUID, String, Long> cooldowns = HashBasedTable.create();
    //private static final WeakHashMap<UUID, Long> cooldowns = new WeakHashMap<>();

    public static boolean isOnCooldown(UUID uuid, boolean all) {
        String key = all ? "SmeltAll" : "Smelt";
        if (!cooldowns.contains(uuid, key)) return false;
        if (cooldowns.get(uuid, key) < System.currentTimeMillis()) {
            removeCooldown(uuid, all);
            return false;
        }
        return true;
    }

    public static void updateCooldown(UUID uuid, boolean all) {
        String key = all ? "SmeltAll" : "Smelt";
        long time = ConfigUtils.getSmeltPerRankCooldown(Main.get().getPermission().getPrimaryGroup(Main.get().getServer().getPlayer(uuid)), all) * 1000;
        if (time <= 0) return;
        cooldowns.put(uuid, key, System.currentTimeMillis() + time);
    }

    public static void removeCooldown(UUID uuid, boolean all) {
        String key = all ? "SmeltAll" : "Smelt";
        cooldowns.remove(uuid, key);
    }

    public static long getTimeLeft(UUID uuid, boolean all) {
        String key = all ? "SmeltAll" : "Smelt";
        long timeLeft = -1;
        if (isOnCooldown(uuid, all)) {
            long cooldownDone = cooldowns.get(uuid, key);
            timeLeft = cooldownDone - System.currentTimeMillis();
        }
        return timeLeft;
    }

    public static double getFormattedTimeLeft(UUID uuid, boolean all) {
        return getTimeLeft(uuid, all) / 1000.0;
    }
}