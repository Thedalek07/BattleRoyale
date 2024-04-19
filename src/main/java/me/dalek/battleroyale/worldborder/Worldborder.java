package me.dalek.battleroyale.worldborder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class Worldborder {

    private static final WorldBorder wB = getServer().getWorlds().get(0).getWorldBorder();

    private static int timeWb = 600; // Durée de déplacement de la WorldBorder

    public static void phase1(){ wB.setSize(1000); }

    public static void phase2(){ worldBorder(900, 100); }

    public static void phase3(){ worldBorder(800, 100); }

    public static void phase4(){ worldBorder(500, 300); }

    public static void phase5(){ worldBorder(50, 450); }

    private static void worldBorder(int size, int reductionBlocks){
        wB.setSize(size, timeWb);
        Bukkit.broadcastMessage(ChatColor.GOLD + "La worldborder se réduit de " + reductionBlocks + " blocks !");
        sounds();
    }

    private static void sounds(){
        for(Player p: Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
        }
    }
}

