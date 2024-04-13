package me.dalek.battleroyale.initialisation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getWorlds;

public class Init {

    public static void setGamerules(){
        World world = getWorlds().get(0);
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("doWeatherCycle", "true");
    }

    public static void resetPlayer(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL); // Met le joueur en survie
            p.setExp(0); // Reset l'XP du joueur
            p.setLevel(0); // Reset les levels des joueurs
            p.getInventory().clear(); // Clear l'inventaire des joueurs
        }
    }

    public static void resetWorld(){
        World world = getWorlds().get(0);
        world.setTime(1000); // Met le jour
        world.setStorm(false); // Met le soleil (enleve la pluie)
    }
}
