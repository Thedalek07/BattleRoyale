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
            case "revive": if(args.length >= 1){ return vide; }
                break;
            case "invite": if(args.length >= 2){ return vide; }
                break;
            case "accept": if(args.length >= 1){ return vide; }
                break;
            case "decline": if(args.length >= 1){ return vide; }
                break;
            case "leave": if(args.length >= 1){ return vide; }
                break;
            case "msg": if(args.length >= 2){ return vide; }
                break;
        }




        return null;
    }
}
