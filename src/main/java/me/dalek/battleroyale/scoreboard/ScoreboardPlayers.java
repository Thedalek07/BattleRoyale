package me.dalek.battleroyale.scoreboard;

import me.dalek.battleroyale.timer.Timer;
import me.dalek.battleroyale.worldborder.Worldborder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardPlayers {
    private static final int intervalleCoffres = Timer.getIntervalleCoffres();
    private static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static Map<Player, Scoreboard> playerScoreboards = new HashMap<>();

    public static void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = playerScoreboards.computeIfAbsent(player, p -> manager.getNewScoreboard());
            Objective objective = getOrCreateObjective(scoreboard);

            updateScoreboardContent(player, objective);
            player.setScoreboard(scoreboard);
        }
    }

    private static Objective getOrCreateObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.getObjective("TimerScore");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("TimerScore", "dummy", ChatColor.GOLD + "Battle Royale");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        return objective;
    }

    private static void updateScoreboardContent(Player player, Objective objective) {
        Team myTeam = manager.getMainScoreboard().getPlayerTeam(player);
        double wbSize = Worldborder.getBossbarValue();
        String teamNameValue = (myTeam != null) ? myTeam.getName() : "Aucune";

        setScore(objective, ChatColor.BLUE + "Équipe", 7);
        setScore(objective, teamNameValue, 6);

        setScore(objective, ChatColor.RED + "Coordonnées", 5);
        updateCoordinates(player, objective.getScoreboard());

        setScore(objective, ChatColor.YELLOW + "WorldBorder", 3);
        setScore(objective, String.valueOf((int) wbSize), 2);

        int min = Timer.getMinutes();
        int valeursAffichage = min - (Timer.getMinutesInit() - intervalleCoffres);
        if (valeursAffichage > 0){
            displayChallenges(objective, min, valeursAffichage);
        }
    }

    private static void updateCoordinates(Player player, Scoreboard scoreboard) {
        Team coordTeam = scoreboard.getTeam("coordinates");
        if (coordTeam == null) {
            coordTeam = scoreboard.registerNewTeam("coordinates");
            coordTeam.addEntry(ChatColor.GREEN + ""); // Utilise une entrée vide pour n’afficher que les coordonnées
        }
        String playerCoords = getPlayerCoordinates(player);
        coordTeam.setPrefix(playerCoords);  // Met à jour dynamiquement les coordonnées
        coordTeam.setSuffix(""); // Si nécessaire, ajoutez des informations supplémentaires ici
        Score coordScore = scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GREEN + "");
        coordScore.setScore(4);
    }

    private static void setScore(Objective objective, String title, int score) {
        Score scoreEntry = objective.getScore(title);
        scoreEntry.setScore(score);
    }

    private static String getPlayerCoordinates(Player player) {
        return String.format("%d / %d / %d",
                (int) player.getLocation().getX(),
                (int) player.getLocation().getY(),
                (int) player.getLocation().getZ());
    }

    private static void displayChallenges(Objective objective, int min, int valeursAffichage) {
        int intervalle = Timer.getMinutesInit() - intervalleCoffres;

        if (min >= intervalle) {
            displayChallenge(objective, "Coffre 1", valeursAffichage + ":" + Timer.getSecondes());
        }
        if (min >= intervalle - intervalleCoffres && min < intervalle) {
            displayChallenge(objective, "Mini-Défis", (valeursAffichage + intervalleCoffres) + ":" + Timer.getSecondes());
        }
        if (min >= intervalle - (intervalleCoffres * 2) && min < intervalle - intervalleCoffres) {
            displayChallenge(objective, "Coffre 2", (valeursAffichage + (intervalleCoffres * 2)) + ":" + Timer.getSecondes());
        }
        if (min >= intervalle - (intervalleCoffres * 3) && min < intervalle - (intervalleCoffres * 2)) {
            displayChallenge(objective, "Défis", (valeursAffichage + (intervalleCoffres * 3)) + ":" + Timer.getSecondes());
        }
    }

    private static void displayChallenge(Objective objective, String title, String val) {
        setScore(objective, ChatColor.GREEN + title, 1);
        setScore(objective, val, 0);
    }
}
