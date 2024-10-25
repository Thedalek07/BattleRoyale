package me.dalek.battleroyale.fin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import static me.dalek.battleroyale.messages.Messages.enum_Msg.*;

public class Fin {
    private static String winner = "";

    public static void finDePartie() {
        int nbPlayerSurvival = 0;
        int nbPlayerSpectator = 0;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                nbPlayerSurvival++;
                winner = p.getName();
            } else if (p.getGameMode() == GameMode.SPECTATOR) {
                nbPlayerSpectator++;
            }
        }

        if (nbPlayerSpectator > 1 && nbPlayerSurvival == 1) {
            announceWinner(winner);
        } else if (nbPlayerSpectator >= 1 && nbPlayerSurvival > 1) {
            announceTeamVictory(nbPlayerSurvival);
        }
    }

    private static void announceWinner(String winner) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(String.format(MSG_PLAYER_VICTOIRE.toString(), winner));
            p.playSound(p, Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
            p.sendTitle(ChatColor.GOLD + "FIN DE PARTIE !", winner + " a gagné !", 10, 70, 20);
            p.setGameMode(GameMode.SPECTATOR);
        }
    }

    private static void announceTeamVictory(int nbPlayerSurvival) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Team myTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p);
            if (myTeam != null && myTeam.getSize() == nbPlayerSurvival) {
                for (Player win : Bukkit.getOnlinePlayers()) {
                    win.sendMessage(String.format(MSG_PLAYER_VICTOIRE_TEAM.toString(), myTeam.getName()));
                    win.playSound(win, Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
                    win.sendTitle(ChatColor.GOLD + "FIN DE PARTIE !", "L'équipe " + myTeam.getName() + " a gagné !", 10, 70, 20);
                    win.setGameMode(GameMode.SPECTATOR);
                }
                return;
            }
        }
    }

}
