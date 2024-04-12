package me.dalek.battleroyale;

import me.dalek.battleroyale.commandes.Commandes;
import me.dalek.battleroyale.commandes.Completion;
import me.dalek.battleroyale.scoreboard.Scoreboard;
import me.dalek.battleroyale.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;


public final class Main extends JavaPlugin {

    public static final HashMap<Player, Player> invites = new HashMap<>();
    public static final HashMap<String, Long> timeout = new HashMap<>();

    @Override
    public void onEnable() {
        // CONSOLE
        System.out.println("Battle Royale à démarré !");

        // COMMANDES
        getCommand("revive").setExecutor(new Commandes());
        getCommand("invite").setExecutor(new Commandes());
        getCommand("accept").setExecutor(new Commandes());
        getCommand("decline").setExecutor(new Commandes());
        getCommand("leave").setExecutor(new Commandes());
        getCommand("msg").setExecutor(new Commandes());
        getCommand("help").setExecutor(new Commandes());
        getCommand("run").setExecutor(new Commandes());

        // AUTOCOMPLETION
        getCommand("help").setTabCompleter(new Completion());
        getCommand("msg").setTabCompleter(new Completion());
        getCommand("revive").setTabCompleter(new Completion());
        getCommand("invite").setTabCompleter(new Completion());
        getCommand("accept").setTabCompleter(new Completion());
        getCommand("decline").setTabCompleter(new Completion());
        getCommand("leave").setTabCompleter(new Completion());

        // MESSAGE INITALE
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.GOLD + "Plugin Battle Royale V1.0.0 - Propriété de Bioscar et Dalek");
            p.sendMessage(ChatColor.GOLD + "Fait par The_dalek");
        }

        // BOUCLES SCHEDULE
        BukkitScheduler scheduler = getServer().getScheduler();

        // SCOREBOARD
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Scoreboard.updateScoreboard();
            }
        }, 0L, 10L);

        // TIMER
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Timer.decompteSeconde();
            }
        }, 0L, 20L);


    }
}
