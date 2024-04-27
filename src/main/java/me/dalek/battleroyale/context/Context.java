package me.dalek.battleroyale.context;

import org.bukkit.Location;
import org.bukkit.World;

import static org.bukkit.Bukkit.getWorlds;

public class Context {
    public static World world = getWorlds().get(0);
    public static final Location coCoffres = new Location(world, 0, 88, 0);
    public static final Location spawnLobby = new Location(world, 0, -50, 4);
}
