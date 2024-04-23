package me.dalek.battleroyale.commandes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Completion implements TabCompleter{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> vide = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("help")){
            List<String> help = new ArrayList<>();
            if(args.length == 1){
                help.add("revive");
                help.add("invite");
                help.add("accept");
                help.add("decline");
                help.add("leave");
            }
            return help;
        }

        switch (command.getName()){
            case "revive":
            case "decline":
            case "leave":
            case "accept":
            case "run":
            case "start":
            case "pause":
                if(args.length >= 1){ return vide; }
                break;
            case "invite":
            case "msg":
                if(args.length >= 2){ return vide; }
                break;
        }




        return null;
    }
}
