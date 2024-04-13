package me.dalek.battleroyale.morts;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.Bukkit.getWorlds;

public class Morts implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // JOUEUR MORT
        Player player = event.getEntity().getPlayer();

        // MESSAGE DE MORT
        Bukkit.broadcastMessage(ChatColor.DARK_RED + player.getName() + " est mort(e) !");

        // PASSAGE EN SPECTATEUR
        player.setGameMode(GameMode.SPECTATOR);

        // LEAVE TEAM
        for(Team team: Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeams()){
            if(team.hasEntry(player.getName())) {
                Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                Objects.requireNonNull(sb.getPlayerTeam(player)).removePlayer(player);
            }
        }

        // FOUDRE AU SPAWN
        eclair();

        // SOUNDS
        sounds();
    }

    private static void eclair(){
        Location eclair = new Location(getWorlds().get(0), 0, 100, 0); // Position du coffre 2
        Bukkit.getWorlds().get(0).strikeLightningEffect(eclair);
    }

    private static void sounds(){
        for(Player pSound : Bukkit.getOnlinePlayers()) {
            pSound.playSound(pSound.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
        }
    }
}
