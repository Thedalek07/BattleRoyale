package me.dalek.battleroyale.config;

import me.dalek.battleroyale.Main;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class Config {

    public static void initConfigPlayer(Player p){
        Main.getPlugin().getConfig().set(p.getName() + "_NB_MORTS", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_CAUSE_MORT", "Aucune");
        Main.getPlugin().getConfig().set(p.getName() + "_TIME_MORT", "0 minutes et 0 secondes");
        Main.getPlugin().getConfig().set(p.getName() + "_DISTANCE_PARCOURU", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_PLAYER_KILLS", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_RATIO_DAMAGES", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_RECU", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_DONNEES", 0);
        Main.getPlugin().getConfig().set(p.getName() + "_TRADE_TO_VILLAGER", 0);
        //Main.getPlugin().getConfig().set(p.getName() + "_BLOCS_MINES", 0);
        Main.getPlugin().saveConfig();
    }

    public static void saveConfigAll(){
        for(Player p : Bukkit.getOnlinePlayers()){
            int distance = (p.getStatistic(Statistic.WALK_ONE_CM) + p.getStatistic(Statistic.SPRINT_ONE_CM) + p.getStatistic(Statistic.SWIM_ONE_CM))/100;
            if(p.getStatistic(Statistic.DAMAGE_ABSORBED) != 0){
                Main.getPlugin().getConfig().set(p.getName() + "_RATIO_DAMAGES", p.getStatistic(Statistic.DAMAGE_DEALT) / p.getStatistic(Statistic.DAMAGE_ABSORBED));
            } else {
                Main.getPlugin().getConfig().set(p.getName() + "_RATIO_DAMAGES", 0);
            }
            Main.getPlugin().getConfig().set(p.getName() + "_NB_MORTS", p.getStatistic(Statistic.DEATHS));
            Main.getPlugin().getConfig().set(p.getName() + "_DISTANCE_PARCOURU", distance);
            Main.getPlugin().getConfig().set(p.getName() + "_PLAYER_KILLS", p.getStatistic(Statistic.PLAYER_KILLS));
            Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_RECU", p.getStatistic(Statistic.DAMAGE_TAKEN));
            Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_DONNEES", p.getStatistic(Statistic.DAMAGE_DEALT));
            Main.getPlugin().getConfig().set(p.getName() + "_TRADE_TO_VILLAGER", p.getStatistic(Statistic.TRADED_WITH_VILLAGER));
            //Main.getPlugin().getConfig().set(p.getName() + "_BLOCS_MINES", p.getStatistic(Statistic.MINE_BLOCK));
            Main.getPlugin().saveConfig();
        }
    }

    public static void saveConfigPlayer(Player p){
        int distance = (p.getStatistic(Statistic.WALK_ONE_CM) + p.getStatistic(Statistic.SPRINT_ONE_CM) + p.getStatistic(Statistic.SWIM_ONE_CM))/100;
        if(p.getStatistic(Statistic.DAMAGE_TAKEN) != 0){
            Main.getPlugin().getConfig().set(p.getName() + "_RATIO_DAMAGES", p.getStatistic(Statistic.DAMAGE_DEALT) / p.getStatistic(Statistic.DAMAGE_TAKEN));
        } else {
            Main.getPlugin().getConfig().set(p.getName() + "_RATIO_DAMAGES", 0);
        }
        Main.getPlugin().getConfig().set(p.getName() + "_NB_MORTS", p.getStatistic(Statistic.DEATHS)+1);
        Main.getPlugin().getConfig().set(p.getName() + "_DISTANCE_PARCOURU", distance);
        Main.getPlugin().getConfig().set(p.getName() + "_PLAYER_KILLS", p.getStatistic(Statistic.PLAYER_KILLS));
        Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_RECU", p.getStatistic(Statistic.DAMAGE_TAKEN));
        Main.getPlugin().getConfig().set(p.getName() + "_DAMAGE_DONNEES", p.getStatistic(Statistic.DAMAGE_DEALT));
        Main.getPlugin().getConfig().set(p.getName() + "_TRADE_TO_VILLAGER", p.getStatistic(Statistic.TRADED_WITH_VILLAGER));
        //Main.getPlugin().getConfig().set(p.getName() + "_BLOCS_MINES", p.getStatistic(Statistic.MINE_BLOCK));
        Main.getPlugin().saveConfig();
    }
}
