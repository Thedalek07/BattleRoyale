package me.dalek.battleroyale.initialisation;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.dalek.battleroyale.context.Context.coCoffres;
import static me.dalek.battleroyale.context.Context.world;
import static org.bukkit.Bukkit.getPort;
import static org.bukkit.Bukkit.getWorlds;

public class Init {

    public static void setGamerules(){
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("doWeatherCycle", "true");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("reducedDebugInfo", "true");
        world.setPVP(false);
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
        coCoffres.getBlock().setType(Material.AIR);
    }

    public static void slowFalling(){
        for(Player p : Bukkit.getOnlinePlayers()){
            PotionEffect effect = p.getPlayer().getPotionEffect(PotionEffectType.SLOW_FALLING);
            if ((effect != null) && (p.isOnGround())) {
                p.removePotionEffect(PotionEffectType.SLOW_FALLING);
            }
        }
    }

    public static Location coChest(){
        return coCoffres;
    }
}
