package me.dalek.battleroyale.scoreboard;

import me.dalek.battleroyale.timer.Timer;
import me.dalek.battleroyale.worldborder.Worldborder;
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
            if (manager != null){
                org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
                org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);

                // RECUPERE LA VALEUR DE LA WORLDBORDER
                double wbSize = Worldborder.getBossbarValue();

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
                int min = Timer.getMinutes();

                // AFFICHAGE DU SCOREBOARD
                if(min >= intervalle){
                    String val = valeursAffichage + ":" + Timer.getSecondes();
                    affichageDefis(objective, "Coffre 1", val);
                }
                if(min >= intervalle-intervalleCoffres && min < intervalle){
                    String val = (valeursAffichage + intervalleCoffres) + ":" + Timer.getSecondes();
                    affichageDefis(objective, "Mini-Défis", val);
                }
                if(min >= intervalle-(intervalleCoffres*2) && min < intervalle-intervalleCoffres){
                    String val = (valeursAffichage + intervalleCoffres*2) + ":" + Timer.getSecondes();
                    affichageDefis(objective, "Coffre 2", val);
                }
                if(min >= intervalle-(intervalleCoffres*3) && min < intervalle-(intervalleCoffres*2)){
                    String val = (valeursAffichage + intervalleCoffres*3) + ":" + Timer.getSecondes();
                    affichageDefis(objective, "Défis", val);
                }
                p.setScoreboard(scoreboard);
            }
        }
    }

    private static void affichageDefis(Objective objective, String title, String val){
        Score coffre_label = objective.getScore(ChatColor.GREEN + title);
        coffre_label.setScore(1);
        Score coffre_val = objective.getScore(val);
        coffre_val.setScore(0);
    }

    public static int getIntervalleCoffres(){
        return intervalleCoffres;
    }
}
