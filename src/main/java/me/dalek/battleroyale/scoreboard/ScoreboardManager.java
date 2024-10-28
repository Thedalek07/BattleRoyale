package me.dalek.battleroyale.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.dalek.battleroyale.worldborder.Worldborder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.timer.Timer.getMinutesChallenges;
import static me.dalek.battleroyale.timer.Timer.getSecondes;

public class ScoreboardManager {

    // Map pour stocker les tableaux de scores pour chaque joueur
    private static final Map<Player, FastBoard> boards = new HashMap<>();
    private static List<Location> locs_gerard = new ArrayList<>();
    private static challenge status_challenge = challenge.none;
    public enum challenge{
        none,
        miniDefis,
        coffre1,
        arene,
        coffre2
    }

    public static void createScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + "Battle Royale");

        // Set initial scores
        board.updateLine(0, ChatColor.BLUE  + "Équipe");
        board.updateLine(1, "Aucune");
        board.updateLine(2, ChatColor.RED + "Coordonnées");
        board.updateLine(3, "0 / 0 / 0");
        board.updateLine(4, ChatColor.YELLOW + "WorldBorder");
        board.updateLine(5, "1000");
        board.updateLine(6, ChatColor.AQUA + "Gérard");
        board.updateLine(7, "0");

        // Stockez l'instance du tableau dans la map
        boards.put(player, board);
    }

    public static void updateScoreboard() {
        for(Player player : Bukkit.getOnlinePlayers()){
            FastBoard board = boards.get(player);
            if (board != null) {
                board.updateLine(1, getPlayerTeam(player));
                board.updateLine(3, getPlayerCoordinates(player));
                board.updateLine(5, String.valueOf((int) Worldborder.getBossbarValue()));
                if(!status_challenge.equals(challenge.none)){
                    board.updateLine(8, ChatColor.GREEN + getChallenge());
                    board.updateLine(9, getMinutesChallenges() + ":" + getSecondes());
                }else {
                    board.removeLine(8);
                    board.removeLine(9);
                }
                board.updateLine(7, getCoGerard(player));
            }
        }
    }

    public static void initCoGerard(){
        locs_gerard.add(new Location(world, 100, 64, 100));
        locs_gerard.add(new Location(world, -100, 69, 100));
        locs_gerard.add(new Location(world, 100, 87, -100));
        locs_gerard.add(new Location(world, -100, 72, -100));
        locs_gerard.add(new Location(world, 250, 73, 250));
        locs_gerard.add(new Location(world, -250, 69, 250));
        locs_gerard.add(new Location(world, 250, 64, -250));
        locs_gerard.add(new Location(world, -250, 104, -250));
    }

    public static String getCoGerard(Player player){
        double minDistance = Double.MAX_VALUE;
        for(Location loc : locs_gerard){
            double distance = loc.distance(player.getLocation());
            if(distance < minDistance){
                minDistance = distance;
            }
        }
        return String.valueOf((int)minDistance);
    }

    public static void SetStatusChallenge(challenge status){
        status_challenge = status;
    }

    public static String getChallenge(){
        switch(status_challenge){
            case miniDefis:
                return "Mini Défis";
            case arene:
                return "Arenes";
            case coffre1:
                return "Coffre 1";
            case coffre2:
                return "Coffre 2";
            case none:
            default:
                return "empty";
        }
    }

    public static String getPlayerTeam(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                return team.getDisplayName(); // Retourne le nom affiché de l'équipe
            }
        }
        return "Aucune"; // Le joueur n'est pas dans une équipe
    }

    private static String getPlayerCoordinates(Player player) {
        return String.format("%d / %d / %d",
                (int) player.getLocation().getX(),
                (int) player.getLocation().getY(),
                (int) player.getLocation().getZ());
    }
}
