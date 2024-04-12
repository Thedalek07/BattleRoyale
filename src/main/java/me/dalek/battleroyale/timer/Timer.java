package me.dalek.battleroyale.timer;

import me.dalek.battleroyale.commandes.Commandes;
import me.dalek.battleroyale.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Timer  {

    static BossBar Timer;
    public static Integer MinutesRestantes = 0;
    public static Integer SecondesRestantes = 0;
    public static Integer MinutesInit = 90;

    public static void createTimer(Integer minutes){
        SecondesRestantes = 1;
        Timer = Bukkit.createBossBar( minutes.toString() + ":00", BarColor.BLUE, BarStyle.SOLID);
        Timer.setVisible(true);
        Timer.setProgress(1.0);
        for(Player joueur : Bukkit.getOnlinePlayers()) {
            Timer.addPlayer(joueur);
        };
        MinutesRestantes =  minutes;
        MinutesInit = minutes;
    }

    public static void decompteSeconde (){
        if(Timer != null){
            SecondesRestantes--;
            if(SecondesRestantes == -1){
                SecondesRestantes = 59;
                MinutesRestantes--;
            }
            Timer.setTitle(MinutesRestantes + ":" + SecondesRestantes);
            coffres();
        }
    }

    private static void coffres() {
        int intervalle = Scoreboard.getIntervalleCoffres();
        if((MinutesRestantes == MinutesInit - intervalle) && (SecondesRestantes == 0)){
            System.out.println("COFFRE 1 SPAWN !");
        }
        if((MinutesRestantes == MinutesInit - intervalle*2) && (SecondesRestantes == 0)){
            System.out.println("LES DEFIS SONT OUVERTS !");
        }
        if((MinutesRestantes == MinutesInit - intervalle*3) && (SecondesRestantes == 0)){
            System.out.println("COFFRE 2 SPAWN !");
        }
    }


    public static int getMinutes(){
        return MinutesRestantes;
    }

    public static int getSecondes(){
        return SecondesRestantes;
    }

    public static int getMinutesInit(){
        return MinutesInit;
    }

}
