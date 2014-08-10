package com.icloud.viper_monster.Smelter.utilities;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public class Config extends YamlConfiguration {

    public static Config createConfig(Plugin plugin, String file) {
        return new Config(plugin, file + (!file.endsWith(".yml") ? ".yml" : ""));
    }

    private final File configFile;

    public Config(Plugin plugin, String file) {
        this(plugin, new File(plugin.getDataFolder(), file));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Config(Plugin plugin, File file) {
        configFile = file;
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        if (!configFile.exists()) {
            if (plugin.getResource(file.getName()) == null) {
                try {
                    configFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else plugin.saveResource(file.getName(), true);
        }

        reload();
    }


    public boolean reload() {
        try {
            load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save() {
        try {
            save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}