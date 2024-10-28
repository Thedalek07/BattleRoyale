package me.dalek.battleroyale.timer;

import me.dalek.battleroyale.coffres.Coffres;
import me.dalek.battleroyale.defis.Arena;
import me.dalek.battleroyale.defis.Minidefis;
import me.dalek.battleroyale.scoreboard.ScoreboardManager;
import me.dalek.battleroyale.worldborder.Worldborder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static me.dalek.battleroyale.Main.getPlugin;
import static me.dalek.battleroyale.defis.Minidefis.initMiniDefis;
import static me.dalek.battleroyale.initialisation.Init.slowFalling;
import static me.dalek.battleroyale.scoreboard.ScoreboardManager.SetStatusChallenge;

public class Timer {

    private static BossBar timer; // BossBar représentant le timer
    private static int minutesRestantes = 0; // nb de minutes restantes
    private static int secondesRestantes = 0; // Nb de secondes restantes
    private static final int MINUTES_INIT = 90; // Durée du timer
    private static final int intervalleCoffres = 15;
    private static int minutesChallenges = 0;
    private static final int DUREE_PVP = 2; // Durée pendant laquelle le PvP est désactivé
    private static boolean pause = false;
    private static boolean statutLoc = false;
    private static double scoreTimer = 0;
    private static final HashMap<Player, Location> positions = new HashMap<>();
    private static final HashMap<Player, Double> vies = new HashMap<>();
    private static int numberStart = 0;

    public static void createTimer() {
        numberStart++;
        System.out.println("[TIMER] CREATE | Nombre de démarrage : " + numberStart);
        secondesRestantes = 1;
        timer = Bukkit.createBossBar(MINUTES_INIT + ":00", BarColor.BLUE, BarStyle.SOLID);
        timer.setVisible(true);
        timer.setProgress(1.0);
        for (Player joueur : Bukkit.getOnlinePlayers()) {
            timer.addPlayer(joueur);
        }
        minutesRestantes = MINUTES_INIT;
        minutesChallenges = intervalleCoffres;
    }

    public static void addPlayer(Player p){
        timer.addPlayer(p);
    }

    public static void decompteSeconde() {
        if (timer != null && !pause) {
            if (secondesRestantes-- <= 0) {
                secondesRestantes = 59;
                minutesRestantes--;
                minutesChallenges--;
            }

            if(minutesChallenges == - 1){
                minutesChallenges = intervalleCoffres - 1;
            }

            if (minutesRestantes >= MINUTES_INIT - 2){
                slowFalling();
            }

            // Affiche le timer
            timer.setTitle(String.format("%d:%02d", minutesRestantes, secondesRestantes));
            scoreTimer = (minutesRestantes * 60) + secondesRestantes;
            double progression = Math.max(0.0, Math.min(1.0, scoreTimer / (MINUTES_INIT * 60)));
            timer.setProgress(progression);

            handleWorldBorder();
            coffres();

            if (minutesRestantes == 0 && secondesRestantes == 0) {
                endTimer();
            }
        }
    }

    private static void handleWorldBorder() {
        switch ((int) scoreTimer) {
            case 3600: Worldborder.phase(2); break;
            case 2400: Worldborder.phase(3); break;
            case 1200: Worldborder.phase(4); break;
            case 0:
                Worldborder.phase(5);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.RED + "Vous ne pouvez construire à plus de 120 blocs !");
                    p.playSound(p, Sound.UI_BUTTON_CLICK, 10, 1);
                }
                break;
            default: break;
        }
    }

    private static void endTimer() {
        timer.removeAll();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setGlowing(true);
            }
        }
        System.out.println("[TIMER] FIN DU TIMER");
    }

    public static void restartLoc() {
        statutLoc = false;
    }

    private static void setTitle() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "PAUSE"));
        }
    }

    private static void setLoc() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOnGround()) {
                positions.put(p, p.getLocation());
                vies.put(p, p.getHealth());
            }
        }
        statutLoc = true;
    }

    public static void getPause() {
        if (pause) {
            if (!statutLoc) setLoc();
            setTitle();
            figePlayer();
        }
    }

    public static void figePlayer() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (positions.containsKey(p) && p.isOnGround()) {
                p.teleport(positions.get(p));
                p.setHealth(vies.get(p));
            }
        }
    }

    public static double getScoreTimer() {
        return scoreTimer;
    }

    private static void coffres() {
        for (int i = 1; i <= 4; i++) {
            if (minutesRestantes == MINUTES_INIT - (intervalleCoffres * i) && secondesRestantes == 0) {
                switch (i) {
                    case 1:
                        Coffres.coffre1();
                        sendCoordinates("Les Mini-Défis ouvrent dans 15 min", "0 150 250 / 0 150 -250");
                        SetStatusChallenge(ScoreboardManager.challenge.miniDefis);
                        Bukkit.getLogger().info("[TIMER] Coffre 1");
                        break;
                    case 2:
                        Minidefis.openMiniDefis();
                        initMiniDefis();
                        SetStatusChallenge(ScoreboardManager.challenge.coffre2);
                        Bukkit.getLogger().info("[TIMER] Mini défis");
                        break;
                    case 3:
                        Coffres.coffre2();
                        SetStatusChallenge(ScoreboardManager.challenge.arene);
                        sendCoordinates("Les Défis ouvrent dans 15 min", "-250 100 0 / 250 100 0");
                        Bukkit.getLogger().info("[TIMER] Coffre 2");
                        break;
                    case 4:
                        Arena.openDefis();
                        SetStatusChallenge(ScoreboardManager.challenge.none);
                        Bukkit.getLogger().info("[TIMER] Défis Arenes");
                        break;
                }
            }
        }
    }

    private static void sendCoordinates(String message, String coordinates) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.GOLD + message);
            p.sendMessage(ChatColor.GOLD + "Les coordonnées :");
            p.sendMessage(ChatColor.GOLD + coordinates);
        }
    }

    public static int getMinutes() {
        return minutesRestantes;
    }

    public static int getSecondes() {
        return secondesRestantes;
    }

    public static int getMinutesChallenges(){
        return minutesChallenges;
    }

    public static int getMinutesInit() {
        return MINUTES_INIT;
    }

    public static void pause() {
        pause = true;
    }

    public static void play() {
        pause = false;
    }

}
