package me.dalek.battleroyale.coffres;

import me.dalek.battleroyale.messages.Messages;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.dalek.battleroyale.context.Context.coCoffres;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.*;

public class Coffres {

    public static void coffre1() {
        createChest(MSG_CONSOLE_COFFRE_UN, MSG_PLAYER_COFFRE_UN, createCoffre1Items());
    }

    public static void coffre2() {
        createChest(MSG_CONSOLE_COFFRE_DEUX, MSG_PLAYER_COFFRE_DEUX, createCoffre2Items());
    }

    private static void createChest(Messages.enum_Msg consoleMessage, Messages.enum_Msg playerMessage, ItemStack[] items) {
        System.out.println(consoleMessage);
        playSounds();
        Bukkit.broadcastMessage(String.valueOf(playerMessage));

        Inventory inv = addChest();
        for (int i = 0; i < items.length; i++) {
            inv.setItem(i, items[i]);
        }
    }

    private static void playSounds() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
        }
    }

    private static ItemStack[] createCoffre1Items() {
        return new ItemStack[] {
                new ItemStack(Material.DIAMOND_HELMET),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                new ItemStack(Material.DIAMOND_PICKAXE),
                new ItemStack(Material.DIAMOND, 1),
                new ItemStack(Material.BOW),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                new ItemStack(Material.DIAMOND_BOOTS),
                new ItemStack(Material.GOLDEN_APPLE, 10),
                new ItemStack(Material.DIAMOND_SWORD),
                new ItemStack(Material.ENDER_PEARL, 8),
                new ItemStack(Material.REDSTONE_BLOCK, 2),
                new ItemStack(Material.ARROW, 32),
                new ItemStack(Material.IRON_INGOT, 20),
                new ItemStack(Material.GOLD_INGOT, 20),
                new ItemStack(Material.DIAMOND_AXE),
                new ItemStack(Material.COPPER_BLOCK),
                new ItemStack(Material.COOKED_BEEF, 10)
        };
    }

    private static ItemStack[] createCoffre2Items() {
        return new ItemStack[] {
                addEnchantement(Material.DIAMOND_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 1),
                addEnchantement(Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 1),
                addEnchantement(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 2),
                new ItemStack(Material.DIAMOND, 2),
                addEnchantement(Material.BOW, Enchantment.ARROW_DAMAGE, 1),
                addEnchantement(Material.DIAMOND_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1),
                addEnchantement(Material.DIAMOND_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 1),
                new ItemStack(Material.GOLDEN_APPLE, 20),
                addEnchantement(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 1),
                new ItemStack(Material.ENDER_PEARL, 16),
                new ItemStack(Material.REDSTONE_BLOCK, 4),
                new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.IRON_INGOT, 40),
                new ItemStack(Material.GOLD_INGOT, 40),
                addEnchantement(Material.DIAMOND_AXE, Enchantment.DAMAGE_ALL, 1),
                new ItemStack(Material.COPPER_BLOCK, 2),
                new ItemStack(Material.COOKED_BEEF, 20)
        };
    }

    private static ItemStack addEnchantement(Material item, Enchantment enchantment, int level) {
        ItemStack enchantedItem = new ItemStack(item);
        enchantedItem.addUnsafeEnchantment(enchantment, level);
        return enchantedItem;
    }

    private static Inventory addChest() {
        Location chestLocation = coCoffres;
        Block blockChest = chestLocation.getBlock();
        blockChest.setType(Material.AIR); // Clear the block before setting the chest
        blockChest.setType(Material.CHEST); // Set the block to a chest
        Chest chest = (Chest) blockChest.getState();
        return chest.getInventory();
    }
}
