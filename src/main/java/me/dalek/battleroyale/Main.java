package me.dalek.battleroyale;

import me.dalek.battleroyale.commandes.Commandes;
import me.dalek.battleroyale.commandes.Completion;
import me.dalek.battleroyale.fin.Fin;
import me.dalek.battleroyale.scoreboard.Scoreboard;
import me.dalek.battleroyale.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Objects;


public final class Main extends JavaPlugin {

    public static final HashMap<Player, Player> invites = new HashMap<>();
    public static final HashMap<String, Long> timeout = new HashMap<>();

    @Override
    public void onEnable() {
        // CONSOLE
        System.out.println("Battle Royale à démarré !");

        // COMMANDES
        Objects.requireNonNull(getCommand("revive")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("invite")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("accept")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("decline")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("leave")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("msg")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("help")).setExecutor(new Commandes());
        Objects.requireNonNull(getCommand("run")).setExecutor(new Commandes());

        // AUTOCOMPLETION
        Objects.requireNonNull(getCommand("help")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("msg")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("revive")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("invite")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("accept")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("decline")).setTabCompleter(new Completion());
        Objects.requireNonNull(getCommand("leave")).setTabCompleter(new Completion());

        // MESSAGE INITALE
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.GOLD + "Plugin Battle Royale V1.0.0 - Propriété de Bioscar et Dalek");
            p.sendMessage(ChatColor.GOLD + "Fait par The_dalek");
        }

        // BOUCLES SCHEDULE
        BukkitScheduler scheduler = getServer().getScheduler();

        // SCOREBOARD
        scheduler.scheduleSyncRepeatingTask(this, Scoreboard::updateScoreboard, 0L, 10L);

        // TIMER
        scheduler.scheduleSyncRepeatingTask(this, Timer::decompteSeconde, 0L, 20L);

        // FIN DE PARTIE
        scheduler.scheduleSyncRepeatingTask(this, Fin::finDePartie, 0L, 10L);
    }
}
