package me.dalek.battleroyale.worldborder;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.MSG_PLAYER_REDUCTION_WORLDBORDER;

public class Worldborder {

    private static final WorldBorder wB = world.getWorldBorder();

    public static void phase(int phaseNumber) {
        int size;
        int reductionBlocks = 0;

        switch (phaseNumber) {
            case 1:
                size = 1000;
                break;
            case 2:
                size = 850;
                reductionBlocks = 100;
                break;
            case 3:
                size = 700;
                reductionBlocks = 100;
                break;
            case 4:
                size = 400;
                reductionBlocks = 300;
                break;
            case 5:
                size = 50;
                reductionBlocks = 450;
                break;
            default:
                return;
        }
        int timeWb = 600;
        wB.setSize(size, timeWb);
        broadcastReduction(size, reductionBlocks);
        Bukkit.getLogger().info("[WORLDBORDER] Size :" + size + " / reduction de " + reductionBlocks + " blocks");
        playBorderSound();
    }

    private static void broadcastReduction(int size, int reductionBlocks) {
        if (reductionBlocks > 0) {
            Bukkit.broadcastMessage(String.format(MSG_PLAYER_REDUCTION_WORLDBORDER.toString(), reductionBlocks));
        }
    }

    private static void playBorderSound() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
        }
    }

    public static double getBossbarValue() {
        return wB.getSize();
    }

}

