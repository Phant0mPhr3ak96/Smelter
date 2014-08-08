package com.duckzcraft.viper_monster.Smelter;

import com.duckzcraft.viper_monster.Smelter.commands.CMD_smelt;
import com.duckzcraft.viper_monster.Smelter.commands.CMD_smeltall;
import com.duckzcraft.viper_monster.Smelter.commands.CMD_smelter;
import com.duckzcraft.viper_monster.Smelter.utilities.ConfigUtils;
import com.duckzcraft.viper_monster.Smelter.utilities.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.WeakHashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    private Economy economy = null;
    private Permission permission = null;
    private WeakHashMap<UUID, Long> cooldown = new WeakHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().warning("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
        }

        setupPermissions();
        saveDefaultConfig();

        int i = 0;
        for (String name : Utils.getValidMaterials()) {
            getConfig().set("material-names-for-permissions." + i, name);
            i++;
        }
        saveConfig();

        getCommand("smelt").setExecutor(new CMD_smelt(this));
        getCommand("smeltall").setExecutor(new CMD_smeltall(this));
        getCommand("smelter").setExecutor(new CMD_smelter(this));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Main get() {
        return instance;
    }

    private boolean setupEconomy() {
        if (!getServer().getPluginManager().isPluginEnabled("Vault")) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermission() {
        return permission;
    }

    public boolean isOnCooldown(Player player) {
        if (!cooldown.containsKey(player.getUniqueId())) return false;
        if (cooldown.get(player.getUniqueId()) < System.currentTimeMillis()) {
            cooldown.remove(player.getUniqueId());
            return false;
        }
        return true;

        /*if (!cooldown.containsKey(player.getUniqueId())) cooldown.put(player.getUniqueId(), System.currentTimeMillis());
        return cooldown.get(player.getUniqueId()) > System.currentTimeMillis();*/
    }

    public void updateCooldown(Player player) {
        long time = ConfigUtils.getSmeltPerRankCooldown(getPermission().getPrimaryGroup(player));
        if (time == System.currentTimeMillis()) return;
        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + time);
    }

    public void removeCooldown(Player player) {
        cooldown.remove(player.getUniqueId());
    }
}