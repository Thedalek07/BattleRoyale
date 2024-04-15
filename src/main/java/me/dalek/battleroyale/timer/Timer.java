package me.dalek.battleroyale.timer;

import me.dalek.battleroyale.coffres.Coffres;
import me.dalek.battleroyale.coffres.Defis;
import me.dalek.battleroyale.scoreboard.Scoreboard;
import me.dalek.battleroyale.worldborder.Worldborder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Timer  {

    static BossBar Timer;
    public static Integer MinutesRestantes = 0;
    public static Integer SecondesRestantes = 0;
    public static Integer MinutesInit = 90;
    public static int intervalleCoffres = 15;
    private static int dureePvp = 2;

    public static void createTimer(){
        SecondesRestantes = 1;
        Timer = Bukkit.createBossBar( MinutesInit.toString() + ":00", BarColor.BLUE, BarStyle.SOLID);
        Timer.setVisible(true);
        Timer.setProgress(1.0);
        for(Player joueur : Bukkit.getOnlinePlayers()) {
            Timer.addPlayer(joueur);
        }
        MinutesRestantes =  MinutesInit;
    }

    public static void decompteSeconde (){
        if(Timer != null){
            SecondesRestantes--;
            if(SecondesRestantes == -1){
                SecondesRestantes = 59;
                MinutesRestantes--;
            }

            // AFFOCHE LE TIMER
            Timer.setTitle(MinutesRestantes + ":" + SecondesRestantes);

            // TIMER DES COFFRES ET DEFIS
            coffres();

            // AFFICHAGE DU TIMER
            double scoreTimer = (MinutesRestantes*60) + SecondesRestantes;
            double valBossbar = scoreTimer / (MinutesInit*60);
            Timer.setProgress(valBossbar);

            // PVP DESACTIVER PENDANT X MINUTES
            if(MinutesRestantes == MinutesInit - dureePvp && SecondesRestantes == 0){
                Bukkit.getWorlds().get(0).setPVP(true);
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.GOLD + "Le PvP est activé");
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                }
            }

            switch((int) scoreTimer){
                case 3600:
                    Worldborder.phase2();
                    break;
                case 2400:
                    Worldborder.phase3();
                    break;
                case 1200:
                    Worldborder.phase4();
                    break;
                case 0:
                    Worldborder.phase5();
                default:
                    break;
            }

            if((MinutesRestantes != 0) && (SecondesRestantes != 0)){
                for(Player p: Bukkit.getOnlinePlayers()) {
                    Timer.addPlayer(p);
                }
            }else if ((MinutesRestantes == 0) && (SecondesRestantes == 0)){
                Timer.removeAll();
            }
        }
    }

    private static void coffres() {
        int intervalle = Scoreboard.getIntervalleCoffres();
        if((MinutesRestantes == MinutesInit - intervalle) && (SecondesRestantes == 0)){
            Coffres.coffre1();
        }
        if((MinutesRestantes == MinutesInit - intervalle*2) && (SecondesRestantes == 0)){
            Defis.openDefis();
        }
        if((MinutesRestantes == MinutesInit - intervalle*3) && (SecondesRestantes == 0)){
            Coffres.coffre2();
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

    public static int getIntervalleCoffres(){
        return intervalleCoffres;
    }
}
