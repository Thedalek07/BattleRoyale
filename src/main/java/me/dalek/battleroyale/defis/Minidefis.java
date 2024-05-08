package me.dalek.battleroyale.defis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import static me.dalek.battleroyale.context.Context.world;

public class Minidefis {

    private static boolean epreuveTempleUnActive = false;
    private static final Location coEntreeEpreuveTempleUn = new Location(Bukkit.getWorlds().get(0), -3, 51, -243);
    private static boolean epreuveTempleDeuxActive = false;
    private static final Location coEntreeEpreuveTempleDeux = new Location(Bukkit.getWorlds().get(0), 4, 50, 241);
    public static void getPlayer(){
        for(Player p :  Bukkit.getOnlinePlayers()){
            if((p.getLocation().distance(coEntreeEpreuveTempleUn) < 1) && (!epreuveTempleUnActive)){
                epreuveTempleUnActive = true;
                setMobsTempleUn();
            } else if ((p.getLocation().distance(coEntreeEpreuveTempleDeux) < 1) && (!epreuveTempleDeuxActive)) {
                epreuveTempleDeuxActive = true;
                setMobsTempleDeux();
            }
        }
    }

    private static void setMobsTempleUn(){
        spawnCreature(-10, 51, -254, EntityType.CREEPER);
        spawnCreature(-4, 51, -250, EntityType.SILVERFISH);
        spawnCreature(-11, 51, -256, EntityType.SILVERFISH);
        spawnCreature(-4, 51, -256, EntityType.SILVERFISH);
        spawnCreature(-4, 51, -253, EntityType.SILVERFISH);
        spawnCreature(-4, 51, -258, EntityType.SILVERFISH);
        spawnCreature(-8, 51, -260, EntityType.CREEPER);
        spawnCreature(-8, 51, -260, EntityType.SILVERFISH);
        spawnCreature(2, 51, -261, EntityType.SILVERFISH);
        spawnCreature(6, 51, -265, EntityType.SILVERFISH);
        spawnCreature(11, 51, -264, EntityType.SILVERFISH);
        spawnCreature(8, 51, -261, EntityType.SILVERFISH);
        spawnCreature(13, 51, -265, EntityType.SILVERFISH);
        spawnCreature(13, 51, -256, EntityType.CREEPER);
        spawnCreature(7, 51, -253, EntityType.CREEPER);
        spawnCreature(8, 51, -251, EntityType.SILVERFISH);
        spawnCreature(10, 51, -249, EntityType.SILVERFISH);
        spawnCreature(12, 51, -254, EntityType.SILVERFISH);
    }

    private static void setMobsTempleDeux(){
        summonSkeleton(9, 50, 249, Material.IRON_SWORD);
        summonSkeleton(7, 50, 255, Material.IRON_AXE);
        summonSkeleton(12, 50, 251, Material.IRON_SWORD);
        summonSkeleton(11, 50, 261, Material.IRON_SWORD);
        summonSkeleton(1, 50, 259, Material.IRON_AXE);
        summonSkeleton(-3, 50, 257, Material.IRON_SWORD);
        summonSkeleton(-8, 50, 260, Material.IRON_AXE);
        summonSkeleton(-5, 50, 262, Material.IRON_SWORD);
        summonSkeleton(-8, 50, 253, Material.IRON_SWORD);
        summonSkeleton(-6, 50, 256, Material.IRON_SWORD);
        summonSkeleton(-6, 50, 249, Material.IRON_AXE);
    }

    private static void spawnCreature(int x, int y, int z, EntityType creature){
        world.spawnEntity(new Location(world, x, y , z), creature);
    }

    private static void summonSkeleton(int x, int y, int z, Material itemHand){
        Skeleton skeleton = (Skeleton) world.spawnEntity(new Location(world, x, y, z), EntityType.WITHER_SKELETON);
        LivingEntity mob = skeleton;
        mob.getEquipment().getItemInMainHand().setType(itemHand);
    }

    public static void openMiniDefis(){
        Bukkit.broadcastMessage(ChatColor.GOLD + "Les mini défis sont ouvets !");
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
