package me.dalek.battleroyale.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Lever;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.timer.Timer.getScoreTimer;

public class Events implements Listener {


    @EventHandler
    public void playerDamage(EntityDamageByEntityEvent event){
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
    public void playerDamageProjectile(ProjectileHitEvent event){
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
    public void placeBlock(BlockPlaceEvent event){
        if(event.getPlayer().getLocation().getY() >= 121 && getScoreTimer() == 0){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void bloquerChatSpectator(AsyncPlayerChatEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            Team team = sb.getPlayerTeam(player);
            if (team != null && player.getHealth() - event.getDamage() <= 0) {
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(!p.equals(player) && team.hasEntry(p.getName()) && p.getInventory().contains(Material.ECHO_SHARD)){
                        event.setCancelled(true);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 0));
                        for(Player pParticule : Bukkit.getOnlinePlayers()){
                            pParticule.spawnParticle(Particle.TOTEM, player.getLocation(), 500);
                        }
                        ItemStack totem = new ItemStack(Material.ECHO_SHARD);
                        p.getInventory().removeItem(totem);

                        sounds();
                    }
                }
            }
        }
    }

    private void sounds(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p, Sound.ITEM_TOTEM_USE, 10 , 1);
        }
    }

}
