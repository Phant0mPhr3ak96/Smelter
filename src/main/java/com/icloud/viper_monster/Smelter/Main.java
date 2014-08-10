package com.icloud.viper_monster.Smelter;

import com.icloud.viper_monster.Smelter.commands.CMD_Smelt;
import com.icloud.viper_monster.Smelter.commands.CMD_SmeltAll;
import com.icloud.viper_monster.Smelter.commands.CMD_Smelter;
import com.icloud.viper_monster.Smelter.utilities.Config;
import com.icloud.viper_monster.Smelter.utilities.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

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

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().warning("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
        }

        config = Config.createConfig(this, "config");
        langConfig = Config.createConfig(this, "lang");

        setupPermissions();
        saveDefaultConfig();

        int i = 0;
        for (String name : Utils.getValidMaterials()) {
            getConfig().set("material-names-for-permissions." + i, name);
            i++;
        }
        saveConfig();

        getCommand("smelt").setExecutor(new CMD_Smelt(this));
        getCommand("smeltall").setExecutor(new CMD_SmeltAll(this));
        getCommand("smelter").setExecutor(new CMD_Smelter(this));
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
}