package me.dalek.battleroyale.initialisation;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.sql.rowset.spi.SyncFactory.getLogger;
import static me.dalek.battleroyale.context.Context.*;
import static org.bukkit.Bukkit.getPort;
import static org.bukkit.Bukkit.getWorlds;

public class Init {

    static List<Location> listSpawn = new ArrayList<>();
    private static int hauteur = 50;

    public static void setGamerules(){
        System.out.println("[INIT] GAMERULES");
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("doWeatherCycle", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("reducedDebugInfo", "true");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doInsomnia", "false");
        world.setPVP(true);
    }

    public static void resetPlayer(){
        System.out.println("[INIT] PLAYER");
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL); // Met le joueur en survie
            p.setExp(0); // Reset l'XP du joueur
            p.setLevel(0); // Reset les levels des joueurs
            p.getInventory().clear(); // Clear l'inventaire des joueurs
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        }
    }

    public static void createVoiceChannel(String channelName){
        Bukkit.broadcastMessage("createVoiceChannel");
        try {
            Bukkit.broadcastMessage("createVoiceChannel-try");
            // URL de votre bot Discord
            String urlString = "http://" + adresseIpBotDiscord + "/create-voice-channel?name=" + channelName;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                getLogger().info("[DISCORD] Salon vocal créé avec succès : " + channelName);
            } else {
                getLogger().warning("[DISCORD] Erreur lors de la création du salon vocal : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetWorld(){
        System.out.println("[INIT] WORLD");
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setDifficulty(Difficulty.NORMAL);
        world.setTime(1000); // Met le jour
        world.setStorm(false); // Met le soleil (enleve la pluie)
        coCoffres.getBlock().setType(Material.AIR);
    }

    public static void resetStatisitic(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setStatistic(Statistic.DEATHS, 0);
            p.setStatistic(Statistic.SWIM_ONE_CM, 0);
            p.setStatistic(Statistic.WALK_ONE_CM, 0);
            p.setStatistic(Statistic.SPRINT_ONE_CM, 0);
            p.setStatistic(Statistic.PLAYER_KILLS, 0);
            p.setStatistic(Statistic.DAMAGE_TAKEN, 0);
            p.setStatistic(Statistic.DAMAGE_DEALT, 0);
            p.setStatistic(Statistic.TALKED_TO_VILLAGER, 0);
            //p.setStatistic(Statistic.MINE_BLOCK, 0);
        }
    }

    public static void slowFalling(){
        for(Player p : Bukkit.getOnlinePlayers()){
            PotionEffect effect = p.getPlayer().getPotionEffect(PotionEffectType.SLOW_FALLING);
            if ((effect != null) && (p.isOnGround())) {
                p.removePotionEffect(PotionEffectType.SLOW_FALLING);
            }
        }
    }

    public static void pointSpawn(int nbPoint, int rayon){
        listSpawn.clear();
        double angle = ((2*Math.PI)/nbPoint);
        Bukkit.getLogger().info("Angle = " + angle);
        for(int i = 1 ; i <= nbPoint ; i++){
            double angleOffset = angle * i;
            int x = (int) (Math.cos(angleOffset) * rayon);
            int z = (int) (Math.sin(angleOffset) * rayon);
            int y = world.getHighestBlockYAt(x, z) + hauteur;
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage("Position n°" + i + " : " + x + " / " + z);
            }
            listSpawn.add(new Location(world, x, y, z));
        }
    }

    public static List<Location> getListSpawn(){
        return listSpawn;
    }

    public static void removeListSpawn(Location location){
        listSpawn.remove(location);
    }

    public static Location coChest(){
        return coCoffres;
    }
}
