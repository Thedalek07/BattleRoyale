package me.dalek.battleroyale.config;

import me.dalek.battleroyale.Main;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import static me.dalek.battleroyale.context.Context.world;

public class Config {

    public static void initConfigPlayer(){
        for(int x = 0 ; x < 50 ; x++){
            for(int z = 0 ; z < 50 ; z++){
                String co = x + " / " + z;
                String bloc = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z).getType().name();
                Main.getPlugin().getConfig().set(co, bloc);
            }
        }
        Main.getPlugin().saveConfig();
    }
}
