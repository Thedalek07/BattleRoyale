package me.dalek.battleroyale.initialisation;

import me.dalek.battleroyale.commandes.Commandes;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.dalek.battleroyale.context.Context.spawnLobby;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.MSG_PLAYER_JOIN_SERVER;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!Commandes.getPartieLancer() && !event.getPlayer().isOp()){
            Player p = event.getPlayer();
            p.sendMessage(String.format(String.valueOf(MSG_PLAYER_JOIN_SERVER), p.getName()));
            p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
            p.teleport(spawnLobby);
            p.setGameMode(GameMode.ADVENTURE);
        }
    }
}
