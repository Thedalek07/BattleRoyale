package me.dalek.battleroyale.statistiques;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class Stats {

    public static void resetStatistic() {
        Statistic[] statsToReset = {
                Statistic.PLAYER_KILLS,
                Statistic.SWIM_ONE_CM,
                Statistic.WALK_ONE_CM,
                Statistic.SPRINT_ONE_CM,
                Statistic.BOAT_ONE_CM,
                Statistic.CROUCH_ONE_CM,
                Statistic.WALK_UNDER_WATER_ONE_CM,
                Statistic.DAMAGE_DEALT,
                Statistic.TRADED_WITH_VILLAGER,
                Statistic.TOTAL_WORLD_TIME,
                Statistic.TIME_SINCE_DEATH
        };

        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Statistic stat : statsToReset) {
                p.setStatistic(stat, 0); // Réinitialiser chaque statistique à 0
            }
        }
    }
}
