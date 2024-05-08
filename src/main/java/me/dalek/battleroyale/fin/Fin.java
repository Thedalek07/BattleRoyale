package me.dalek.battleroyale.fin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import static me.dalek.battleroyale.messages.Messages.enum_Msg.*;

public class Fin {
    // VARIABLES
    private static int nbPlayerSurvival = 0;
    private static int nbPlayerSpectator = 0;
    private static String winner = "";

    public static void finDePartie(){

        nbPlayerSurvival =  0;
        nbPlayerSpectator = 0;

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
                p.sendMessage(String.format(String.valueOf(MSG_PLAYER_VICTOIRE), winner));
                p.setGameMode(GameMode.SPECTATOR);
            }
        } else if (nbPlayerSpectator >= 1 && nbPlayerSurvival > 1) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);
                if(myTeam != null){
                    if(myTeam.getSize() == nbPlayerSurvival){
                        for(Player win : Bukkit.getOnlinePlayers()) {
                            win.sendMessage(String.format(String.valueOf(MSG_PLAYER_VICTOIRE_TEAM), myTeam.getName()));
                            win.setGameMode(GameMode.SPECTATOR);
                        }
                        return;
                    }
                }
            }
        }

    }
}
