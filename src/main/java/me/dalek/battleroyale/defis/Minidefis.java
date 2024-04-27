package me.dalek.battleroyale.defis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.dalek.battleroyale.context.Context.world;

public class Minidefis {

    private static boolean epreuveActive = false;
    private static final Location coEntreeEpreuve = new Location(Bukkit.getWorlds().get(0), -3, 51, -243);

    public static void getPlayer(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if((p.getLocation().distance(coEntreeEpreuve) < 1) && (!epreuveActive)){
                epreuveActive = true;
                setMobs();
            }
        }
    }

    private static void setMobs(){
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

    public static void setMobsInit(){
        spawnCreature(-1, 55, 243, EntityType.MAGMA_CUBE);
        spawnCreature(1, 55, 260, EntityType.MAGMA_CUBE);
        spawnCreature(-5, 56, 244, EntityType.MAGMA_CUBE);
        spawnCreature(12, 55, 257, EntityType.SLIME);
        spawnCreature(6, 55, 243, EntityType.SLIME);
        spawnCreature(-3, 55, 252, EntityType.SLIME);
    }

    private static void spawnCreature(int x, int y, int z, EntityType creature){
        world.spawnEntity(new Location(world, x, y , z), creature);
    }


}
