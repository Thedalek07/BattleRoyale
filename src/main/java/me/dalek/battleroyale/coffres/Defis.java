package me.dalek.battleroyale.coffres;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import static org.bukkit.Bukkit.getWorlds;

public class Defis {

    public static void openDefis(){
        // CONSOLE
        System.out.println("LES DEFIS SONT OUVERTS !");

        // OUVERTURE DE L'ARENE
        getWorlds().get(0).getBlockAt(261, 77, -5).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 77, -4).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 77, -3).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 76, -5).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 76, -4).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 76, -3).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 75, -5).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 75, -4).setType(Material.AIR);
        getWorlds().get(0).getBlockAt(261, 75, -3).setType(Material.AIR);
    }

    public static void closeDefis(){
        // OUVERTURE DE L'ARENE
        getWorlds().get(0).getBlockAt(261, 77, -5).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 77, -4).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 77, -3).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 76, -5).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 76, -4).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 76, -3).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 75, -5).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 75, -4).setType(Material.BARRIER);
        getWorlds().get(0).getBlockAt(261, 75, -3).setType(Material.BARRIER);
    }
}
