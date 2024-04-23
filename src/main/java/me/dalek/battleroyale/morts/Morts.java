package me.dalek.battleroyale.morts;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.MSG_PLAYER_INIVTE_ACCEPT_SENDER;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.MSG_PLAYER_MORTS;
import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.Bukkit.getWorlds;

public class Morts implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // JOUEUR MORT
        Player player = event.getEntity().getPlayer();

        // FOUDRE SUR JOUEUR MORT
        world.strikeLightningEffect(player.getLocation());

        // MESSAGE DE MORT
        Bukkit.broadcastMessage(String.format(String.valueOf(MSG_PLAYER_MORTS), player.getName()));

        // PASSAGE EN SPECTATEUR
        player.setGameMode(GameMode.SPECTATOR);

        // LEAVE TEAM
        for(Team team: Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeams()){
            if(team.hasEntry(player.getName())) {
                Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                Objects.requireNonNull(sb.getPlayerTeam(player)).removePlayer(player);
            }
        }
        sounds();
    }

    private static void sounds(){
        for(Player pSound : Bukkit.getOnlinePlayers()) {
            pSound.playSound(pSound.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
        }
    }
}
