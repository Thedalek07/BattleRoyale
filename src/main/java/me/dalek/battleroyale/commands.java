package me.dalek.battleroyale;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class commands implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("revive")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(args != null){
                    if(args.length != 0){
                        Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                        if(p.getInventory().contains(Material.ECHO_SHARD)){ // Vérifie si le joueur possède une echo shard
                            if(r != null) { // Vérifie si l'argument est bien le nom d'un joueur
                                if(r != p){ // Vérifie que le joueur sélectionné est différent de celui qui exécute la commande
                                    if(r.getGameMode() == GameMode.SPECTATOR){ // Vérifie si le joueur cible est en spectateur
                                        // MESSAGES
                                        p.sendMessage(ChatColor.GREEN  + "Vous avez réssuciter " + r.getName() + " !");
                                        r.sendMessage(ChatColor.GREEN  + "Vous avez été réssuciter par " + p.getName() + " !");

                                        // TELEPORTATION
                                        Location location = p.getLocation(); // Récupère la position du joueur qui exécute la commande
                                        r.teleport(location); // Téléporte le joueur cible
                                        r.setGameMode(GameMode.SURVIVAL); // Mets le joueur cible en survie

                                        // REMOVE
                                        p.getInventory().removeItem(new ItemStack(Material.ECHO_SHARD, 1)); // Clear une echo shard

                                        // SOUNDS
                                        p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10, 1);
                                        r.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 10, 1);
                                    }else{
                                        p.sendMessage(ChatColor.RED + "Tu ne peux pas réssuciter un joueur en vie !");
                                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                                    }
                                }else{
                                    p.sendMessage(ChatColor.RED + "Tu ne peux pas te réssuciter !");
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                                }

                            }else{
                                p.sendMessage(ChatColor.YELLOW + "Joueur invalide ! Vérifie l'ortographe du Pseudo");
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                            }
                        }else{
                            p.sendMessage(ChatColor.RED + "il te faut un totem pour réssuciter des joueurs!");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                        }
                    }else{
                        p.sendMessage(ChatColor.YELLOW + "Il faut indiquer un joueur à sauver !");
                    }
                }else{
                    p.sendMessage(ChatColor.RED + "Argument invalide !");
                    p.sendMessage(ChatColor.YELLOW + "Rappel : /revive PSEUDO");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("invite")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(args.length != 0){
                    Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                    Team myTeam = sb.getPlayerTeam(p);
                    if(r != null) { // Vérifie si l'argument est bien le nom d'un joueur
                        if(r != p){ // Vérifie que le joueur sélectionné est différent de celui qui exécute la commande
                            if(sb.getTeam(p.getName()) == null) { // Verifie si la team existe sinon on crée une team portant le nom de joueur qui invite
                                // Créer la team
                                sb.registerNewTeam(p.getName());
                                sb.getTeam(p.getName()).setAllowFriendlyFire(false);
                            }
                            if(sb.getTeam(p.getName()).getSize() < 3){
                                if(p.getLocation().distanceSquared(r.getLocation()) < 100){
                                    sb.getTeam(p.getName()).addEntry(p.getName());

                                    // Messages indiquant la demande d'invitaiton
                                    p.sendMessage(ChatColor.GOLD + "Invitation envoyé à " + r.getName());
                                    r.sendMessage(ChatColor.GOLD + "Vous avez reçu une invitation de " + p.getName());

                                    // Message pour accepter la demande d'invitation
                                    TextComponent messageAccept = new TextComponent(ChatColor.GREEN + "Accepter");
                                    messageAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept"));
                                    r.spigot().sendMessage(messageAccept);

                                    // Message pour decliner la demande d'invitation
                                    TextComponent messageDecline = new TextComponent(ChatColor.RED + "Refuser");
                                    messageDecline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/decline"));
                                    r.spigot().sendMessage(messageDecline);

                                    // Ajoute le joueur qui invite dans la liste de la cible
                                    Untitled.invites.put(r,p);
                                }else{
                                    p.sendMessage(ChatColor.YELLOW + r.getName() + " est trop loin pour etre invité !");
                                }
                            }else{
                                p.sendMessage(ChatColor.RED + "Votre équipe est au complet !");
                            }
                        }else{
                            p.sendMessage(ChatColor.YELLOW + "Vous ne pouvez pas vous inviter dans votre équipe !");
                        }
                    }else {
                        p.sendMessage(ChatColor.YELLOW + "Vous ne pouvez pas vous inviter dans votre équipe !");
                    }
                }else{
                    p.sendMessage(ChatColor.YELLOW + "Il faut indiquer un joueur à inviter !");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("accept")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(Untitled.invites.containsKey(p)){
                    Player team = Untitled.invites.get(p);
                    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                    sb.getTeam(team.getName()).addEntry(p.getName());
                    // Supprime le joueur
                    Untitled.invites.remove(p);
                    p.sendMessage(ChatColor.GOLD + "Invitation acceptée !");
                    team.sendMessage(ChatColor.GOLD + p.getName() + " a accepté votre invitation !");
                }else{
                    p.sendMessage(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("decline")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if (Untitled.invites.get(p) != null){
                    Player playerSend = Untitled.invites.get(p);
                    p.sendMessage(ChatColor.RED + "Vous avez refuser l'invitation de " + playerSend.getName());
                    playerSend.sendMessage(ChatColor.RED + sender.getName() + " à refuser l'invitation !");
                    Untitled.invites.remove(p);
                }else {
                    p.sendMessage(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !");
                }

            }
        }

        if (command.getName().equalsIgnoreCase("leave")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);
                if(myTeam != null){
                    myTeam.removePlayer(p);
                    p.sendMessage(ChatColor.GOLD + "Vous venez de quitter l'équipe " + myTeam.getName() + " !");
                }else{
                    p.sendMessage(ChatColor.RED + "Vous n'etes pas dans une équipe ! ");
                }

            }
        }

        return true;
    }
}
