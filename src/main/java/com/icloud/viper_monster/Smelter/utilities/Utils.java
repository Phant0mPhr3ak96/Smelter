package com.icloud.viper_monster.Smelter.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Author: viper_monster
 * Project: Smelter
 * Date: 10.8.2014. 12:06
 */
public final class Utils {

    private Utils() {
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private static Set<Material> validMaterials = new HashSet<>(Arrays.asList(Material.PORK, Material.RAW_BEEF,
            Material.RAW_CHICKEN, Material.RAW_FISH, Material.POTATO_ITEM, Material.IRON_ORE, Material.GOLD_ORE,
            Material.SAND, Material.COBBLESTONE, Material.CLAY_BALL, Material.NETHERRACK, Material.CLAY,
            Material.DIAMOND_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.COAL_ORE, Material.EMERALD_ORE,
            Material.QUARTZ_ORE, Material.LOG, Material.LOG_2, Material.CACTUS));

    public static String translateColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> getValidMaterials() {
        List<String> names = new ArrayList<>();
        for (Material material : validMaterials) {
            names.add(material.name());
        }
        Collections.sort(names);
        return names;
    }

    public static boolean hasItemPermission(Player player, boolean allCMD, Material material) {
        return player.hasPermission("smelter." + (allCMD ? "smeltall" : "smelt") + "." + material.name());
    }

    public static boolean isValidMaterial(Material material) {
        return validMaterials.contains(material);
    }

    public static ItemStack getSmeltedItemStack(ItemStack itemStack) {
        ItemStack is = itemStack.clone();

        switch (is.getType()) {
            case PORK: {
                is.setType(Material.GRILLED_PORK);
                break;
            }
            case RAW_BEEF: {
                is.setType(Material.COOKED_BEEF);
                break;
            }
            case RAW_CHICKEN: {
                is.setType(Material.COOKED_CHICKEN);
                break;
            }
            case RAW_FISH: {
                is.setType(Material.RAW_FISH);
                break;
            }
            case POTATO_ITEM: {
                is.setType(Material.BAKED_POTATO);
                break;
            }
            case IRON_ORE: {
                is.setType(Material.IRON_INGOT);
                break;
            }
            case GOLD_ORE: {
                is.setType(Material.GOLD_INGOT);
                break;
            }
            case SAND: {
                is.setType(Material.GLASS);
                break;
            }
            case COBBLESTONE: {
                is.setType(Material.STONE);
                break;
            }
            case CLAY_BALL: {
                is.setType(Material.CLAY_BRICK);
                break;
            }
            case NETHERRACK: {
                is.setType(Material.NETHER_BRICK);
                break;
            }
            case CLAY: {
                is.setType(Material.HARD_CLAY);
                break;
            }
            case DIAMOND_ORE: {
                is.setType(Material.DIAMOND);
                break;
            }
            case LAPIS_ORE: {
                is.setType(Material.INK_SACK);
                is.setDurability((short) 4);
                break;
            }
            case REDSTONE_ORE: {
                is.setType(Material.REDSTONE);
                break;
            }
            case COAL_ORE: {
                is.setType(Material.COAL);
                break;
            }
            case EMERALD_ORE: {
                is.setType(Material.EMERALD);
                break;
            }
            case QUARTZ_ORE: {
                is.setType(Material.QUARTZ);
                break;
            }
            case LOG: {
                is.setType(Material.COAL);
                is.setDurability((short) 1);
                break;
            }
            case LOG_2: {
                is.setType(Material.COAL);
                is.setDurability((short) 1);
                break;
            }
            case CACTUS: {
                is.setType(Material.QUARTZ);
                is.setDurability((short) 2);
                break;
            }
        }

        return is;
    }
}