package me.dalek.battleroyale.scoreboard;

import me.dalek.battleroyale.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static org.bukkit.Bukkit.getServer;

public class Scoreboard {

    public static int intervalleCoffres = 1;

    public static void updateScoreboard (){
        for(Player p: Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
            org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
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

            // COFFRES + DEFIS
            int intervalle = Timer.getMinutesInit() - intervalleCoffres;

            // VALEURS D'AFFICHAGE
            int valeursAffichage = Timer.getMinutes() - intervalle;

            // AFFICHAGE DU SCOREBOARD
            if(Timer.getMinutes() >= intervalle){
                Score coffre_label = objective.getScore(ChatColor.GOLD + "Coffre 1");
                coffre_label.setScore(1);
                Score coffre_val = objective.getScore(valeursAffichage + ":" + Timer.getSecondes());
                coffre_val.setScore(0);
            }
            if(Timer.getMinutes() >= intervalle-intervalleCoffres && Timer.getMinutes() < intervalle){
                Score coffre_label = objective.getScore(ChatColor.GOLD + "Défis");
                coffre_label.setScore(1);
                Score coffre_val = objective.getScore((valeursAffichage + intervalleCoffres) + ":" + Timer.getSecondes());
                coffre_val.setScore(0);
            }
            if(Timer.getMinutes() >= intervalle-(intervalleCoffres*2) && Timer.getMinutes() < intervalle-intervalleCoffres){
                Score coffre_label = objective.getScore(ChatColor.GOLD + "Coffre 2");
                coffre_label.setScore(1);
                Score coffre_val = objective.getScore((valeursAffichage + intervalleCoffres*2) + ":" + Timer.getSecondes());
                coffre_val.setScore(0);
            }

            p.setScoreboard(scoreboard);

        }
    }

    public static int getIntervalleCoffres(){
        return intervalleCoffres;
    }
}
