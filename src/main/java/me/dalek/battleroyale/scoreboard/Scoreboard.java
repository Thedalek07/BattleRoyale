package me.dalek.battleroyale.scoreboard;

import me.dalek.battleroyale.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class Scoreboard {

    private static final int intervalleCoffres = Timer.getIntervalleCoffres();

    public static void updateScoreboard (){
        for(Player p: Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            assert manager != null;
            org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
            org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            Team myTeam = sb.getPlayerTeam(p);

            // RECUPERE LA VALEUR DE LA BOSSBAR
            WorldBorder wB = Objects.requireNonNull(getServer().getWorld(p.getWorld().getName())).getWorldBorder();
            double wbSize = wB.getSize();

            // RECUPERE LES COORDONNEES
            double val_X = p.getLocation().getX();
            double val_Y = p.getLocation().getY();
            double val_Z = p.getLocation().getZ();

            // CREER L'OBJECTIFS
            Objective objective = scoreboard.registerNewObjective("TimerScore", "dummy", ChatColor.GOLD + "Battle Royale" );
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            // NOM DE L'EQUIPE
            Score teamName = objective.getScore(ChatColor.BLUE + "Equipe");
            teamName.setScore(7);
            Score teamNameVal;
            if(myTeam != null){
                teamNameVal = objective.getScore(myTeam.getName());
            }else{
                teamNameVal = objective.getScore("Aucune");
            }
            teamNameVal.setScore(6);


            // COORDONNEES
            Score co = objective.getScore(ChatColor.RED + "Coordonnées");
            co.setScore(5);
            Score co_val = objective.getScore(Double.toString(val_X).substring(0, Double.toString(val_X).indexOf(".")) + " / " + Double.toString(val_Y).substring(0, Double.toString(val_Y).indexOf(".")) + " / " + Double.toString(val_Z).substring(0, Double.toString(val_Z).indexOf(".")));
            co_val.setScore(4);

            // WORLDBORDER
            Score border = objective.getScore(ChatColor.YELLOW + "WorldBorder");
            border.setScore(3);
            Score border_val = objective.getScore(Double.toString(wbSize).substring(0, Double.toString(wbSize).indexOf(".")));
            border_val.setScore(2);

            // COFFRES + DEFIS
            int intervalle = Timer.getMinutesInit() - intervalleCoffres;

            // VALEURS D'AFFICHAGE
            int valeursAffichage = Timer.getMinutes() - intervalle;

            // AFFICHAGE DU SCOREBOARD
            if(Timer.getMinutes() >= intervalle){
                Score coffre_label = objective.getScore(ChatColor.GREEN + "Coffre 1");
                coffre_label.setScore(1);
                Score coffre_val = objective.getScore(valeursAffichage + ":" + Timer.getSecondes());
                coffre_val.setScore(0);
            }
            if(Timer.getMinutes() >= intervalle-intervalleCoffres && Timer.getMinutes() < intervalle){
                Score coffre_label = objective.getScore(ChatColor.GREEN + "Défis");
                coffre_label.setScore(1);
                Score coffre_val = objective.getScore((valeursAffichage + intervalleCoffres) + ":" + Timer.getSecondes());
                coffre_val.setScore(0);
            }
            if(Timer.getMinutes() >= intervalle-(intervalleCoffres*2) && Timer.getMinutes() < intervalle-intervalleCoffres){
                Score coffre_label = objective.getScore(ChatColor.GREEN + "Coffre 2");
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
