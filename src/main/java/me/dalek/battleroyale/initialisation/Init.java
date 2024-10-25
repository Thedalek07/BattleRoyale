package me.dalek.battleroyale.initialisation;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static javax.sql.rowset.spi.SyncFactory.getLogger;
import static me.dalek.battleroyale.context.Context.*;
import static org.bukkit.Bukkit.getPort;
import static org.bukkit.Bukkit.getWorlds;

public class Init {

    static List<Location> listSpawn = new ArrayList<>();

    public static void setGamerules() {
        System.out.println("[INIT] GAMERULES");
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setPVP(true);
    }

    public static void resetPlayer() {
        System.out.println("[INIT] PLAYER");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL);
            p.setExp(0);
            p.setLevel(0);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        }
    }

    public static void resetWorld(){
        System.out.println("[INIT] WORLD");
        world.setDifficulty(Difficulty.PEACEFUL);       // Permet de tuer tout les mobs
        world.setDifficulty(Difficulty.NORMAL);
        world.setTime(1000);
        world.setStorm(false);                          // Met le soleil (enleve la pluie)
        coCoffres.getBlock().setType(Material.AIR);
    }

    public static void resetStatistic() {
        Statistic[] statsToReset = {
                Statistic.DEATHS,
                Statistic.SWIM_ONE_CM,
                Statistic.WALK_ONE_CM,
                Statistic.SPRINT_ONE_CM,
                Statistic.PLAYER_KILLS,
                Statistic.DAMAGE_TAKEN,
                Statistic.DAMAGE_DEALT,
                Statistic.TALKED_TO_VILLAGER
        };

        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Statistic stat : statsToReset) {
                p.setStatistic(stat, 0); // Réinitialiser chaque statistique à 0
            }
        }
    }

    public static void beaconShop(){
        world.spawnParticle(Particle.SPELL_MOB, new Location(world, -100 ,75 ,-100), 1, 0, 0, 0, 0.1);
    }

    public static void slowFalling() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            // Permet de savoir si le joueur et au sol ET si il à l'effet slowfalling
            if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid() && p.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
                p.removePotionEffect(PotionEffectType.SLOW_FALLING);
            }
        }
    }

    public static void pointSpawn(int nbPoint, int rayon) {
        listSpawn.clear();
        double angle = 2 * Math.PI / nbPoint;
        for (int i = 1; i <= nbPoint; i++) {
            double angleOffset = angle * i;
            int x = (int) (Math.cos(angleOffset) * rayon);
            int z = (int) (Math.sin(angleOffset) * rayon);
            int y = world.getHighestBlockYAt(x, z) + 50;      // Pour modifier la hauteur modifier la dernière valeur
            listSpawn.add(new Location(world, x, y, z));
        }
    }

    public static List<Location> getListSpawn(){
        return listSpawn;
    }

    public static void removeListSpawn(Location location){
        listSpawn.remove(location);
    }

}
