package me.dalek.battleroyale.initialisation;

import me.dalek.battleroyale.commandes.Commandes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Lobby implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && !Commandes.getPartieLancer()){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if(!Commandes.getPartieLancer()){
            event.setCancelled(true);
        }
    }
}
