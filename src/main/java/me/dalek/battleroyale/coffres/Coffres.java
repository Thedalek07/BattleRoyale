package me.dalek.battleroyale.coffres;

import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.dalek.battleroyale.initialisation.Init.coChest;
import static org.bukkit.Bukkit.getWorlds;

public class Coffres {
    public static void coffre1(){
        System.out.println("COFFRE 1 SPAWN !");
        sounds();
        sendMessageAll(ChatColor.GOLD + "Le coffre 1 est apparu !");

        Inventory inv = addChest();

        // INVENTAIRE DU COFFRE
        // LIGNE 1
        inv.setItem(0, new ItemStack(Material.DIAMOND_HELMET, 1));
        inv.setItem(1, new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        inv.setItem(4, new ItemStack(Material.DIAMOND_PICKAXE, 1));
        inv.setItem(7, new ItemStack(Material.DIAMOND, 1));
        inv.setItem(8, new ItemStack(Material.BOW, 1));

        // LIGNE 2
        inv.setItem(9, new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        inv.setItem(10, new ItemStack(Material.DIAMOND_BOOTS, 1));
        inv.setItem(12, new ItemStack(Material.GOLDEN_APPLE, 10));
        inv.setItem(13, new ItemStack(Material.DIAMOND_SWORD, 1));
        inv.setItem(14, new ItemStack(Material.ENDER_PEARL, 8));
        inv.setItem(16, new ItemStack(Material.REDSTONE_BLOCK, 2));
        inv.setItem(17, new ItemStack(Material.ARROW, 32));

        // LIGNE 3
        inv.setItem(18, new ItemStack(Material.IRON_INGOT, 20));
        inv.setItem(19, new ItemStack(Material.GOLD_INGOT, 20));
        inv.setItem(22, new ItemStack(Material.DIAMOND_AXE, 1));
        inv.setItem(25, new ItemStack(Material.COPPER_BLOCK, 1));
        inv.setItem(26, new ItemStack(Material.COOKED_BEEF, 10));
    }

    public static void coffre2(){
        System.out.println("COFFRE 2 SPAWN !");
        sounds();
        sendMessageAll(ChatColor.GOLD + "Le coffre 2 est apparu !");

        // COFFRE 2
        Inventory inv = addChest();

        // INVENTAIRE DU COFFRE
        // LIGNE 1
        ItemStack helmet = addEnchantement(Material.DIAMOND_HELMET, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack legging = addEnchantement(Material.DIAMOND_LEGGINGS, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack chestplate = addEnchantement(Material.DIAMOND_CHESTPLATE, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack boots = addEnchantement(Material.DIAMOND_BOOTS, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemStack pickaxe = addEnchantement(Material.DIAMOND_PICKAXE, 1, Enchantment.DIG_SPEED, 2);
        ItemStack bow = addEnchantement(Material.BOW, 1, Enchantment.ARROW_DAMAGE, 1);
        ItemStack sword = addEnchantement(Material.DIAMOND_SWORD, 1, Enchantment.DAMAGE_ALL, 1);
        ItemStack axe = addEnchantement(Material.DIAMOND_AXE, 1, Enchantment.DAMAGE_ALL, 1);

        inv.setItem(0, helmet);
        inv.setItem(1, legging);
        inv.setItem(4, pickaxe);
        inv.setItem(7, new ItemStack(Material.DIAMOND, 2));
        inv.setItem(8, bow);

        // LIGNE 2
        inv.setItem(9, chestplate);
        inv.setItem(10, boots);
        inv.setItem(12, new ItemStack(Material.GOLDEN_APPLE, 20));
        inv.setItem(13, sword);
        inv.setItem(14, new ItemStack(Material.ENDER_PEARL, 16));
        inv.setItem(16, new ItemStack(Material.REDSTONE_BLOCK, 4));
        inv.setItem(17, new ItemStack(Material.ARROW, 64));

        // LIGNE 3
        inv.setItem(18, new ItemStack(Material.IRON_INGOT, 40));
        inv.setItem(19, new ItemStack(Material.GOLD_INGOT, 40));
        inv.setItem(22, axe);
        inv.setItem(25, new ItemStack(Material.COPPER_BLOCK, 2));
        inv.setItem(26, new ItemStack(Material.COOKED_BEEF, 20));
    }

    private static void sounds(){
        for(Player pSound : Bukkit.getOnlinePlayers()) {
            pSound.playSound(pSound.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
        }
    }

    private static void sendMessageAll(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    private static ItemStack addEnchantement(Material item, int count, Enchantment enchantement, int level){
        ItemStack itemReturn = new ItemStack(item, count);
        itemReturn.addUnsafeEnchantment(enchantement, level);
        return itemReturn;
    }

    private static Inventory addChest(){
        Location coCoffres = coChest();
        Block blockChest = coCoffres.getBlock();
        coCoffres.getBlock().setType(Material.AIR);
        coCoffres.getBlock().setType(Material.CHEST);
        Chest chest = (Chest)blockChest.getState();
        Inventory inv = chest.getInventory();
        return inv;
    }
}

