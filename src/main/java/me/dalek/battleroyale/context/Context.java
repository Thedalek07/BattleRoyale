package me.dalek.battleroyale.context;

import org.bukkit.Location;
import org.bukkit.World;

import static org.bukkit.Bukkit.getWorlds;

public class Context {
    public static World world = getWorlds().get(0);
    public static final Location coCoffres = new Location(world, 0, 70, 0);
    public static final Location spawnLobby = new Location(world, -11, -26, -9 );
    public static final String adresseIpBotDiscord = "188.165.76.91";
    public static final String nameVocalChannel = "Battle Royale 3";
    public static final String idChannelDeadMessage = "1004307823011836016";
}
