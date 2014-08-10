package com.icloud.viper_monster.Smelter;

import com.icloud.viper_monster.Smelter.commands.CMD_Smelt;
import com.icloud.viper_monster.Smelter.commands.CMD_SmeltAll;
import com.icloud.viper_monster.Smelter.commands.CMD_Smelter;
import com.icloud.viper_monster.Smelter.listeners.PlayerJoin;
import com.icloud.viper_monster.Smelter.utilities.Config;
import com.icloud.viper_monster.Smelter.utilities.ConfigUtils;
import com.icloud.viper_monster.Smelter.utilities.Updater;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private Economy economy = null;
    private Permission permission = null;
    private Config config, langConfig;
    private Updater updater;
    private boolean updateAvailable;

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().warning("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            new MetricsLite(this).start();
        } catch (Exception ex) {
            getLogger().warning("[Metrics] Failed to submit the stats :-(");
        }

        instance = this;

        registerCommands();
        registerListeners();

        config = Config.createConfig(this, "config");
        langConfig = Config.createConfig(this, "lang");

        if (ConfigUtils.isUpdater()) {
            updater = new Updater(61864, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
            updateAvailable = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
        }

        config = Config.createConfig(this, "config");
        langConfig = Config.createConfig(this, "lang");

        setupPermissions();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Main get() {
        return instance;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public Config getLangConfig() {
        return langConfig;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getVersionName() {
        return updater.getVersionName();
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

    private void registerCommands() {
        getCommand("smelt").setExecutor(new CMD_Smelt(this));
        getCommand("smeltall").setExecutor(new CMD_SmeltAll(this));
        getCommand("smelter").setExecutor(new CMD_Smelter(this));
    }

    private void registerListeners() {
        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new PlayerJoin(), this);
    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermission() {
        return permission;
    }
}