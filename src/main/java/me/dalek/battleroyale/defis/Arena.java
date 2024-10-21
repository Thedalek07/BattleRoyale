package me.dalek.battleroyale.defis;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.msgConsole.MSG_CONSOLE_ARENE_OUVERTE;
import static org.bukkit.Bukkit.getWorlds;

public class Arena {

    public static void openDefis(){
        // CONSOLE
        System.out.println(MSG_CONSOLE_ARENE_OUVERTE);

        // OUVERTURE DE L'ARENE NUMERO 1
        setBlock(-305, 60, 2, Material.REDSTONE_BLOCK);
        setBlock(-305, 58, 2, Material.REDSTONE_BLOCK);
        setBlock(-305, 60, 2, Material.AIR);
        setBlock(-305, 58, 2, Material.AIR);

        // OUVERTURE DE L'ARENE NUMERO 2
        setBlock(301, 57, 9, Material.REDSTONE_BLOCK);
        setBlock(302, 57, 7, Material.REDSTONE_BLOCK);
        setBlock(301, 57, 9, Material.AIR);
        setBlock(302, 57, 7, Material.AIR);

        // SKELETON LAINE ROUGE
        spawnSkeleton(303, 57, 4);
        spawnSkeleton(305, 57, 3);
        spawnSkeleton(313, 57, 6);
        spawnSkeleton(313, 57, 10);
        spawnSkeleton(305, 57, 13);
        spawnSkeleton(303, 57, 12);
        spawnSkeleton(318, 57, 10);
        spawnSkeleton(325, 57, 13);
        spawnSkeleton(317, 57, 3);
        spawnSkeleton(325, 57, 5);
        spawnSkeleton(-287, 63, -2);
        spawnSkeleton(-295, 63, -3);
        spawnSkeleton(-287, 63, 7);
        spawnSkeleton(-294, 63, 4);
        spawnSkeleton(-299, 63, 0);
        spawnSkeleton(-299, 63, 4);
        spawnSkeleton(-307, 63, 7);
        spawnSkeleton(-309, 63, 6);
        spawnSkeleton(-307, 63, -2);
        spawnSkeleton(-309, 63, -3);

        spawnWhitherSkeleton(-287, 63, 0);
        spawnWhitherSkeleton(-287, 63, -4);
        spawnWhitherSkeleton(-294, 63, -2);
        spawnWhitherSkeleton(-289, 63, 6);
        spawnWhitherSkeleton(-289, 63, 8);
        spawnWhitherSkeleton(-294, 63, 6);
        spawnWhitherSkeleton(-294, 63, 2);
        spawnWhitherSkeleton(-309, 63, -2);
        spawnWhitherSkeleton(-307, 63, -3);
        spawnWhitherSkeleton(-299, 63, 7);
        spawnWhitherSkeleton(-299, 63, 2);
        spawnWhitherSkeleton(-299, 63, -3);
        spawnWhitherSkeleton(-309, 63, 7);
        spawnWhitherSkeleton(-307, 63, 6);
        spawnWhitherSkeleton(325, 57, 7);
        spawnWhitherSkeleton(325, 57, 3);
        spawnWhitherSkeleton(318, 57, 4);
        spawnWhitherSkeleton(325, 57, 12);
        spawnWhitherSkeleton(323, 57, 14);
        spawnWhitherSkeleton(318, 57, 12);
        spawnWhitherSkeleton(318, 57, 8);
        spawnWhitherSkeleton(303, 57, 3);
        spawnWhitherSkeleton(305, 57, 4);
        spawnWhitherSkeleton(313, 57, 3);
        spawnWhitherSkeleton(313, 57, 8);
        spawnWhitherSkeleton(313, 57, 13);
        spawnWhitherSkeleton(303, 57, 13);
        spawnWhitherSkeleton(305, 57, 12);

        spawnRavager(311, 64, 11);
        spawnRavager(311, 64, 5);
        spawnRavager(-301, 69, 5);
        spawnRavager(-301, 69, -1);

    }

    public static void closeDefis(){
        // FERMETURE DE L'ARENE NUMERO 1
        setBlock(-305, 60, 1, Material.REDSTONE_BLOCK);
        setBlock(-305, 58, 1, Material.REDSTONE_BLOCK);
        setBlock(-305, 60, 1, Material.AIR);
        setBlock(-305, 58, 1, Material.AIR);

        // FERMETURE DE L'ARENE NUMERO 2
        setBlock(302, 57, 9, Material.REDSTONE_BLOCK);
        setBlock(301, 57, 7, Material.REDSTONE_BLOCK);
        setBlock(302, 57, 9, Material.AIR);
        setBlock(301, 57, 7, Material.AIR);
    }

    private static void setBlock(int x, int y, int z, Material block){
        world.getBlockAt(x, y, z).setType(block);
    }



    private static void spawnSkeleton(int x, int y, int z){
        Skeleton skeleton = (Skeleton) world.spawnEntity(new Location(world, x, y , z), EntityType.SKELETON);
        skeleton.setRemoveWhenFarAway(false);
        LivingEntity skeletonEntity = skeleton;
        skeletonEntity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        skeletonEntity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        skeletonEntity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        skeletonEntity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack skeletonbow = new ItemStack (Material.BOW, 1);
        ItemMeta skeletonMeta = skeletonbow.getItemMeta();
        skeletonMeta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
        skeletonbow.setItemMeta(skeletonMeta);
        skeletonEntity.getEquipment().setItemInMainHand(skeletonbow);
    }

    private static void spawnWhitherSkeleton(int x, int y, int z){
        WitherSkeleton skeleton = (WitherSkeleton) world.spawnEntity(new Location(world, x, y , z), EntityType.WITHER_SKELETON);
        skeleton.setRemoveWhenFarAway(false);
        LivingEntity skeletonEntity = skeleton;
        skeletonEntity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        skeletonEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        skeletonEntity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        skeletonEntity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        skeletonEntity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
    }

    private static void spawnRavager(int x, int y, int z){
        Ravager ravager = (Ravager) world.spawnEntity(new Location(world, x, y , z), EntityType.RAVAGER);
        ravager.setRemoveWhenFarAway(false);
        Skeleton skeleton = (Skeleton) world.spawnEntity(new Location(world, x, y , z), EntityType.SKELETON);
        skeleton.setRemoveWhenFarAway(false);
        LivingEntity skeletonEntity = skeleton;
        skeletonEntity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        skeletonEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        skeletonEntity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        skeletonEntity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        ItemStack skeletonbow = new ItemStack (Material.BOW, 1);
        ItemMeta skeletonMeta = skeletonbow.getItemMeta();
        skeletonMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
        skeletonMeta.addEnchant(Enchantment.ARROW_FIRE, 2, true);
        skeletonbow.setItemMeta(skeletonMeta);
        skeletonEntity.getEquipment().setItemInMainHand(skeletonbow);
        ravager.addPassenger(skeleton);
    }


}
