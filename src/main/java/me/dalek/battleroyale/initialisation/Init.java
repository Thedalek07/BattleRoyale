package me.dalek.battleroyale.initialisation;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getWorlds;

public class Init {

    private static World world = getWorlds().get(0);

    public static void setGamerules(){
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("doWeatherCycle", "true");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("reducedDebugInfo", "true");
        Bukkit.getWorlds().get(0).setPVP(false);
    }

    public static void resetPlayer(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL); // Met le joueur en survie
            p.setExp(0); // Reset l'XP du joueur
            p.setLevel(0); // Reset les levels des joueurs
            p.getInventory().clear(); // Clear l'inventaire des joueurs
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        }
    }

    public static void resetWorld(){
        world.setTime(1000); // Met le jour
        world.setStorm(false); // Met le soleil (enleve la pluie)
        Location locCoffre1 = new Location(getWorlds().get(0), 0, 88, 0); // Position du coffre 1
        locCoffre1.getBlock().setType(Material.AIR);
    }
}
