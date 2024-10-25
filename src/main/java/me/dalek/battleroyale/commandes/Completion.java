package me.dalek.battleroyale.commandes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.dalek.battleroyale.initialisation.Init.getListSpawn;

public class Completion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase();
        List<String> completions = new ArrayList<>();

        switch (commandName) {
            case "help":
                if (args.length == 1) {
                    completions.add("revive");
                    completions.add("invite");
                    completions.add("accept");
                    completions.add("decline");
                    completions.add("leave");
                }
                break;

            case "spawn":
                if (args.length == 1) {
                    for (int i = 1; i <= getListSpawn().size(); i++) {
                        completions.add(String.valueOf(i));
                    }
                }
                break;

            case "revive":
                if (args.length == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getGameMode() == GameMode.SPECTATOR) {
                            completions.add(p.getName());
                        }
                    }
                }
                break;

            case "decline":
            case "leave":
            case "open":
            case "update":
            case "accept":
            case "run":
            case "start":
            case "pause":
                if (args.length >= 1) return completions; // Empty list for these commands

            case "invite":
            case "msg":
                if (args.length >= 2) return completions; // Empty list for these commands
                break;
        }

        return completions.isEmpty() ? null : completions;
    }
}
