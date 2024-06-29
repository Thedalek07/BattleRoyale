package me.dalek.battleroyale.defis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.Lever;

import static me.dalek.battleroyale.context.Context.world;

public class Minidefis {

    public static void initMiniDefis(){
        spawnCreature(-7, 59, -202, EntityType.RAVAGER);
        spawnCreature(-1, 62, 186, EntityType.RAVAGER);
    }

    private static LivingEntity spawnCreature(int x, int y, int z, EntityType creature){
        LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, x+0.5, y , z+0.5), creature);
        mob.setRemoveWhenFarAway(false);
        return mob;
    }

    private static void summonSkeleton(int x, int y, int z, Material itemHand){
        LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, x+0.5, y, z+0.5), EntityType.WITHER_SKELETON);
        mob.getEquipment().getItemInMainHand().setType(itemHand);
        mob.setRemoveWhenFarAway(false);
    }

    public static void openMiniDefis(){
        Bukkit.broadcastMessage(ChatColor.GOLD + "Les mini défis sont ouverts !");
        setBlock(4, 50, 241, Material.AIR);
        setBlock(4, 51, 241, Material.AIR);
        setBlock(-3, 51, -243, Material.AIR);
        setBlock(-3, 52, -243, Material.AIR);
    }

    public static void closeMiniDefis(){
        setBlock(4, 50, 241, Material.BARRIER);
        setBlock(4, 51, 241, Material.BARRIER);
        setBlock(-4, 50, 241, Material.BARRIER);
        setBlock(-4, 51, 241, Material.BARRIER);
        setBlock(-3, 51, -243, Material.BARRIER);
        setBlock(-3, 52, -243, Material.BARRIER);
        setBlock(5, 51, -243, Material.BARRIER);
        setBlock(5, 52, -243, Material.BARRIER);
    }

    private static void setBlock(int x, int y, int z, Material block){
        world.getBlockAt(x, y, z).setType(block);
    }
}
