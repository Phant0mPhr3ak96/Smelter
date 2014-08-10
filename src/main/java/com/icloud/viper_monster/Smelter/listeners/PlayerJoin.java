package com.icloud.viper_monster.Smelter.listeners;

import com.icloud.viper_monster.Smelter.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.ChatColor.*;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 23:08
 */
public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!Main.get().isUpdateAvailable()) return;

        Player player = event.getPlayer();

        player.sendMessage(GOLD + Main.get().getVersionName() + RED + " is available!");
        player.sendMessage(RED + "You can download it from:");
        player.sendMessage(GRAY + "http://dev.bukkit.org/bukkit-plugins/smelter/files");
    }
}