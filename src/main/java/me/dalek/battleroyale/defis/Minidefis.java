package me.dalek.battleroyale.defis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Minidefis {

    private static boolean epreuveActive = false;
    private static Location coEntreeEpreuve = new Location(Bukkit.getWorlds().get(0), -3, 51, -243);

    public static void getPlayer(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if((p.getLocation().distance(coEntreeEpreuve) < 1) && (epreuveActive == false)){
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

    private static void spawnCreature(int x, int y, int z, EntityType creature){
        World world = Bukkit.getWorlds().get(0);
        world.spawnEntity(new Location(world, x, y , z), creature);
    }

}
