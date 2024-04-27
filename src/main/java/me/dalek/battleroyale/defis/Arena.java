package me.dalek.battleroyale.defis;

import org.bukkit.Material;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.msgConsole.MSG_CONSOLE_ARENE_OUVERTE;
import static org.bukkit.Bukkit.getWorlds;

public class Arena {

    public static void openDefis(){
        // CONSOLE
        System.out.println(MSG_CONSOLE_ARENE_OUVERTE);

        // OUVERTURE DE L'ARENE
        setBlock(261, 77, -5, Material.AIR);
        setBlock(261, 77, -4, Material.AIR);
        setBlock(261, 77, -3, Material.AIR);
        setBlock(261, 76, -5, Material.AIR);
        setBlock(261, 76, -4, Material.AIR);
        setBlock(261, 76, -3, Material.AIR);
        setBlock(261, 75, -5, Material.AIR);
        setBlock(261, 75, -4, Material.AIR);
        setBlock(261, 75, -3, Material.AIR);
    }

    public static void closeDefis(){
        // OUVERTURE DE L'ARENE
        setBlock(261, 77, -5, Material.BARRIER);
        setBlock(261, 77, -4, Material.BARRIER);
        setBlock(261, 77, -3, Material.BARRIER);
        setBlock(261, 76, -5, Material.BARRIER);
        setBlock(261, 76, -4, Material.BARRIER);
        setBlock(261, 76, -3, Material.BARRIER);
        setBlock(261, 75, -5, Material.BARRIER);
        setBlock(261, 75, -4, Material.BARRIER);
        setBlock(261, 75, -3, Material.BARRIER);
    }

    private static void setBlock(int x, int y, int z, Material block){
        world.getBlockAt(x, y, z).setType(block);
    }
}
