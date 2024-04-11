package me.dalek.battleroyale;

import me.dalek.battleroyale.commandes.Commandes;
import me.dalek.battleroyale.commandes.Completion;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

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

        // SCOREBOARD
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getNewScoreboard();
                    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                    Team myTeam = sb.getPlayerTeam(p);

                    // RECUPERE LA VALEUR DE LA BOSSBAR
                    BossBar timerCoffre = getServer().getBossBar(NamespacedKey.minecraft("2"));

                    // RECUPERE LA VALEUR DE LA BOSSBAR
                    WorldBorder wB = getServer().getWorld(p.getWorld().getName()).getWorldBorder();
                    Double wbSize = wB.getSize();

                    // RECUPERE LES COORDONNEES
                    Double val_X = p.getLocation().getX();
                    Double val_Y = p.getLocation().getY();
                    Double val_Z = p.getLocation().getZ();

                    // CREER L'OBJECTIFS
                    Objective objective = scoreboard.registerNewObjective("TimerScore", "dummy", ChatColor.BLUE + "Battle Royale" );
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    // NOM DE L'EQUIPE
                    Score teamName = objective.getScore(ChatColor.GOLD + "Equipe");
                    teamName.setScore(7);
                    if(myTeam != null){
                        Score teamNameVal = objective.getScore(ChatColor.RED + myTeam.getName());
                        teamNameVal.setScore(6);
                    }else{
                        Score teamNameVal = objective.getScore(ChatColor.RED + "Aucune");
                        teamNameVal.setScore(6);
                    }


                    // COORDONNEES
                    Score co = objective.getScore(ChatColor.GOLD + "Coordonnées");
                    co.setScore(5);
                    Score co_val = objective.getScore(ChatColor.RED + val_X.toString().substring(0, val_X.toString().indexOf(".")) + " / " + val_Y.toString().substring(0, val_Y.toString().indexOf(".")) + " / " + val_Z.toString().substring(0, val_Z.toString().indexOf(".")));
                    co_val.setScore(4);

                    // WORLDBORDER
                    Score border = objective.getScore(ChatColor.GOLD + "WorldBorder");
                    border.setScore(3);
                    Score border_val = objective.getScore(ChatColor.RED + wbSize.toString().substring(0, wbSize.toString().indexOf(".")));
                    border_val.setScore(2);

                    // COFFRES
                    Score coffre_label = objective.getScore(ChatColor.GOLD + "Coffre 1");
                    coffre_label.setScore(1);
                    Score coffre_val = objective.getScore(ChatColor.RED + "00:00");
                    coffre_val.setScore(0);

                    p.setScoreboard(scoreboard);

                }
            }
        }, 0L, 10L);


    }
}
