package me.dalek.battleroyale.timer;

import me.dalek.battleroyale.coffres.Coffres;
import me.dalek.battleroyale.defis.Arena;
import me.dalek.battleroyale.defis.Minidefis;
import me.dalek.battleroyale.initialisation.Init;
import me.dalek.battleroyale.messages.Messages;
import me.dalek.battleroyale.scoreboard.Scoreboard;
import me.dalek.battleroyale.worldborder.Worldborder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.defis.Minidefis.initMiniDefis;

public class Timer {

    private static BossBar Timer; // Bossbar représentant le timer
    private static Integer MinutesRestantes = 0; // nb de mintues restantes
    private static Integer SecondesRestantes = 0; // Nb de secondes restantes
    private static Integer MinutesInit = 60; // Durée du timer
    private static int intervalleCoffres = 15; // Intervalle entre chaque coffres et défis
    private static int dureePvp = 2; // Durée pendant laquelle le PvP est désactivé
    private static int dureeEffect = 5; // Durée durant laquelle le slowfalling est désactivé
    private static boolean pause = false;
    private static boolean statutLoc = false;
    private static double scoreTimer = 0;
    private static final HashMap<Player, Location> position = new HashMap<>();
    private static final HashMap<Player, Double> vie = new HashMap<>();

    public static void createTimer(){
        System.out.println("[TIMER] CREATE");
        SecondesRestantes = 1;
        Timer = Bukkit.createBossBar(MinutesInit.toString() + ":00", BarColor.BLUE, BarStyle.SOLID);
        Timer.setVisible(true);
        Timer.setProgress(1.0);
        for(Player joueur : Bukkit.getOnlinePlayers()) {
            Timer.addPlayer(joueur);
        }
        MinutesRestantes = MinutesInit;
    }

    public static void decompteSeconde (){
        if(Timer != null){
            if(MinutesRestantes >= 0 && SecondesRestantes >= 0 && !pause){
                SecondesRestantes--;
                if(SecondesRestantes == -1){
                    SecondesRestantes = 59;
                    MinutesRestantes--;
                }

                // AFFICHE LE TIMER
                Timer.setTitle(MinutesRestantes + ":" + SecondesRestantes);

                // TIMER DES COFFRES ET DEFIS
                coffres();

                // AFFICHAGE DU TIMER
                scoreTimer = (MinutesRestantes*60) + SecondesRestantes;
                double valBossbar = scoreTimer / (MinutesInit*60);
                Timer.setProgress(valBossbar);

                // REMOVE EFFECT SI LE JOUEUR TOUCHE LE SOL
                if(MinutesRestantes >= (MinutesInit - dureeEffect)-1){
                    Init.slowFalling();
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
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.sendMessage(ChatColor.RED + "Vous ne pouvez construire à plus de 120 blocs !");
                            p.playSound(p, Sound.UI_BUTTON_CLICK, 10, 1);
                        }
                    default:
                        break;
                }

                if((MinutesRestantes != 0) && (SecondesRestantes != 0)){
                    for(Player p: Bukkit.getOnlinePlayers()) {
                        Timer.addPlayer(p);
                    }
                }else if ((MinutesRestantes == 0) && (SecondesRestantes == 0)){
                    Timer.removeAll();
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(p.getGameMode().equals(GameMode.SURVIVAL)){
                            p.setGlowing(true);
                        }
                    }
                    System.out.println("FIN DU TIMER");
                    System.out.println("Valeur de la bossbar = " + valBossbar);
                }
            }
        }
    }

    public static void restartLoc(){
        statutLoc = false;
    }

    private static void setTitle(){
        for (Player p : Bukkit.getOnlinePlayers()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "PAUSE"));
        }
    }

    private static void setLoc(){
        for (Player p : Bukkit.getOnlinePlayers()){
            if(p.isOnGround()){
                position.put(p, p.getLocation());
                vie.put(p, p.getHealth());
            }
        }
        statutLoc = true;
    }

    public static void getPause(){
        if (pause){
            if(!statutLoc){
                setLoc();
            }
            setTitle();
            figePlayer();
        }
    }


    public static void figePlayer(){
        for (Player p : Bukkit.getOnlinePlayers()){
            if(position.get(p) != null && p.isOnGround()){
                Location pLoc = position.get(p);
                p.teleport(pLoc);
                p.setHealth(vie.get(p));
            }
        }
    }

    public static double getScoreTimer(){
        return scoreTimer;
    }

    private static void coffres() {
        int intervalle = Scoreboard.getIntervalleCoffres();
        if((MinutesRestantes == MinutesInit - intervalle) && (SecondesRestantes == 0)){
            Coffres.coffre1();
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(ChatColor.GOLD + "Les Mini-Défis ouvrent dans 15 min");
                p.sendMessage(ChatColor.GOLD + "Les coordonnées :");
                p.sendMessage(ChatColor.GOLD + "0 150 250 / 0 150 -250");
            }
        }
        if((MinutesRestantes == MinutesInit - intervalle*2) && (SecondesRestantes == 0)){
            Minidefis.openMiniDefis();
            initMiniDefis();
        }
        if((MinutesRestantes == MinutesInit - intervalle*3) && (SecondesRestantes == 0)){
            Coffres.coffre2();
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(ChatColor.GOLD + "Les Défis ouvrent dans 15 min");
                p.sendMessage(ChatColor.GOLD + "Les coordonnées :");
                p.sendMessage(ChatColor.GOLD + "-250 100 0 / 250 100 0");
            }
        }
        if((MinutesRestantes == MinutesInit - intervalle*4) && (SecondesRestantes == 0)){
            Arena.openDefis();
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

    public static void pause(){
        pause = true;
    }

    public static void play(){
        pause = false;
    }
}
