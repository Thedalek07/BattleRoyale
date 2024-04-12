package me.dalek.battleroyale.fin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class Fin {

    public static void finDePartie(){
        // VARIABLES
        int nbPlayerSurvival = 0;
        int nbPlayerSpectator = 0;
        String winner = "";

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getGameMode() == GameMode.SURVIVAL){
                nbPlayerSurvival++;
                winner = p.getName();
            }else if (p.getGameMode() == GameMode.SPECTATOR){
                nbPlayerSpectator++;
            }
        }

        if(nbPlayerSpectator > 1 && nbPlayerSurvival == 1){
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.GOLD + winner + " a gagné !");
                p.setGameMode(GameMode.SPECTATOR);
            }
        } else if (nbPlayerSpectator > 1 && nbPlayerSurvival > 1) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);
                if(myTeam.getSize() == nbPlayerSurvival){
                    for(Player win : Bukkit.getOnlinePlayers()) {
                        win.sendMessage("L'équipe de " + myTeam.getName() + " a gagné !");
                        win.setGameMode(GameMode.SPECTATOR);
                    }
                    return;
                }
            }
        }

    }
}
