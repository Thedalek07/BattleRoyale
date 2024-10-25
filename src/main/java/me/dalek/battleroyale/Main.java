package me.dalek.battleroyale;

import me.dalek.battleroyale.Maps.ExportMaps;
import me.dalek.battleroyale.commandes.Commandes;
import me.dalek.battleroyale.commandes.Completion;
import me.dalek.battleroyale.config.Config;
import me.dalek.battleroyale.events.Events;
import me.dalek.battleroyale.fin.Fin;
import me.dalek.battleroyale.initialisation.Lobby;
import me.dalek.battleroyale.initialisation.PlayerJoin;
import me.dalek.battleroyale.morts.Morts;
import me.dalek.battleroyale.scoreboard.ScoreboardPlayers;
import me.dalek.battleroyale.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

import static me.dalek.battleroyale.BotDiscord.TableToImage.CreateTableScore;
import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.initialisation.Init.beaconShop;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.MSG_PLAYER_INFO_PLUGIN;
import static me.dalek.battleroyale.messages.Messages.msgConsole.MSG_CONSOLE_PLUGIN_RUN;


public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        System.out.println(MSG_CONSOLE_PLUGIN_RUN);

        plugin = this;

        // DECLARATION EVENTS
        getServer().getPluginManager().registerEvents(new Morts(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new Lobby(), this);
        getServer().getPluginManager().registerEvents(new Events(), this);

        getConfig().options().copyDefaults(true);

        init();

        // COMMANDES
        String[] commandes = {"invite", "accept", "decline", "decline", "leave", "msg", "help", "run", "pause", "start", "synchro", "record", "spawn", "open", "update"};
        initCommande(commandes);
        completionCmd(commandes);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(String.valueOf(MSG_PLAYER_INFO_PLUGIN));
        }

        // BOUCLES SCHEDULE
        BukkitScheduler scheduler = getServer().getScheduler();

        // SCOREBOARD
        scheduler.scheduleSyncRepeatingTask(this, ScoreboardPlayers::updateScoreboard, 0L, 10L);

        // TIMER
        scheduler.scheduleSyncRepeatingTask(this, Timer::decompteSeconde, 0L, 20L);

        // FIN DE PARTIE
        scheduler.scheduleSyncRepeatingTask(this, Fin::finDePartie, 0L, 10L);

        // PAUSE
        scheduler.scheduleSyncRepeatingTask(this, Timer::getPause, 0L, 5L);

        Config.initConfigPlayer();
        CreateTableScore();

        ExportMaps mapExporter = new ExportMaps(getDataFolder() + "/map_data.json");
        mapExporter.exportMapData();
        beaconShop();
    }

    private static void init(){
        world.setPVP(false);
    }

    private void initCommande(String[] nameCmd){
        System.out.println("[INIT] COMMANDES");
        for(String cmd : nameCmd){
            Objects.requireNonNull(getCommand(cmd)).setExecutor(new Commandes());
        }
    }

    private void completionCmd(String[] nameCmd){
        System.out.println("[INIT] COMPLETION");
        for(String cmd : nameCmd){
            Objects.requireNonNull(getCommand(cmd)).setTabCompleter(new Completion());
        }
    }

    public static Main getPlugin(){
        return plugin;
    }

}
