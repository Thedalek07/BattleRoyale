package me.dalek.battleroyale.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Lever;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.defis.Minidefis.openSortie1;
import static me.dalek.battleroyale.defis.Minidefis.openSortie2;
import static me.dalek.battleroyale.timer.Timer.getScoreTimer;

public class Events implements Listener {

    private static final Location lever2 = new Location(world, -5, 51, 242);
    private static final Location lever1 = new Location(world, 6, 52, -244);

    @EventHandler
    public static void playerDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player eventEntity = (Player) event.getEntity();
            Player eventDamager = (Player) event.getDamager();
            Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            if(sb.getPlayerTeam(eventEntity) != null && sb.getPlayerTeam(eventDamager) != null && sb.getPlayerTeam(eventEntity).getDisplayName().equals(sb.getPlayerTeam(eventDamager).getDisplayName())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void playerDamageProjectile(ProjectileHitEvent event){
        if(event.getHitEntity() instanceof Player && event.getEntity().getShooter() instanceof Player){
            Player eventEntity = (Player) event.getHitEntity();
            Player eventDamager = (Player) event.getEntity().getShooter();
            Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            if(sb.getPlayerTeam(eventEntity) != null && sb.getPlayerTeam(eventDamager) != null && sb.getPlayerTeam(eventEntity).getDisplayName().equals(sb.getPlayerTeam(eventDamager).getDisplayName())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void placeBlock(BlockPlaceEvent event){
        if(event.getPlayer().getLocation().getY() >= 121 && getScoreTimer() == 0){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void bloquerChatSpectator(AsyncPlayerChatEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void leverMiniDefis(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.LEVER)){
            Lever lever = (Lever) block.getState().getData();
            if(!lever.isPowered() && block.getLocation().equals(lever2)){
                event.getPlayer().sendMessage(ChatColor.GOLD + "La sortie est ouverte");
                lever2.getBlock().setType(Material.AIR);
                openSortie2();
            } else if (!lever.isPowered() && block.getLocation().equals(lever1)) {
                event.getPlayer().sendMessage(ChatColor.GOLD + "La sortie est ouverte");
                lever1.getBlock().setType(Material.AIR);
                openSortie1();
            }
        }
    }
}
