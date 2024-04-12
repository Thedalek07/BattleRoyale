package me.dalek.battleroyale.commandes;

import me.dalek.battleroyale.Main;
import me.dalek.battleroyale.timer.Timer;
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

public class Commandes implements CommandExecutor {

    int sizeTeamMax = 3; // Nombre de joueurs MAX par équipe.
    int distanceMax = 100; // Distance MAX pour faire une demande d'invite
    long timeoutInvite = 60000; // Timeout d'une invite
    public static int tempsJeu = 90; // Temps de la partie (TIMER)
    int hauteur = 300; // hauteur du tp de début de partie

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
                                        for(Player pSound : Bukkit.getOnlinePlayers()) {
                                            pSound.playSound(pSound.getLocation(), Sound.ITEM_TOTEM_USE, 10, 1);
                                        }
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
                            if(Main.invites.get(r) != p){
                                if(sb.getTeam(p.getName()) == null) { // Verifie si la team existe sinon on crée une team portant le nom de joueur qui invite
                                    // Créer la team
                                    sb.registerNewTeam(p.getName());
                                    sb.getTeam(p.getName()).setAllowFriendlyFire(false);
                                }
                                if(sb.getTeam(p.getName()).getSize() < sizeTeamMax){
                                    if(p.getLocation().distanceSquared(r.getLocation()) < distanceMax){
                                        //sb.getTeam(p.getName()).addEntry(p.getName());

                                        // Messages indiquant la demande d'invitaiton
                                        p.sendMessage(ChatColor.GOLD + "Invitation envoyé à " + r.getName());
                                        r.sendMessage(ChatColor.GOLD + "Vous avez reçu une invitation de " + p.getName());
                                        r.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                                        // Message pour accepter la demande d'invitation
                                        TextComponent messageAccept = new TextComponent(ChatColor.GREEN + "Accepter");
                                        messageAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept"));
                                        r.spigot().sendMessage(messageAccept);

                                        // Message pour decliner la demande d'invitation
                                        TextComponent messageDecline = new TextComponent(ChatColor.RED + "Refuser");
                                        messageDecline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/decline"));
                                        r.spigot().sendMessage(messageDecline);

                                        // Ajoute le joueur qui invite dans la liste de la cible
                                        Main.invites.put(r,p);

                                        // Enregistre le temps
                                        Main.timeout.put(p.getName(), System.currentTimeMillis());
                                    }else{
                                        p.sendMessage(ChatColor.YELLOW + r.getName() + " est trop loin pour etre invité !");
                                    }
                                }else{
                                    p.sendMessage(ChatColor.RED + "Votre équipe est au complet !");
                                }
                            }else{
                                p.sendMessage(ChatColor.YELLOW + "Vous avez déjà envoyé une demande d'invite à " + r.getName() + " !");
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
                if(Main.invites.containsKey(p)){
                    Player team = Main.invites.get(p); // Recupère la demande d'invite en cours
                    if(Main.timeout.get(team.getName()) >= (System.currentTimeMillis() - timeoutInvite)){
                        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                        sb.getTeam(team.getName()).addEntry(p.getName()); // Mets le joueur qui tape la cmd dans la team
                        sb.getTeam(team.getName()).addEntry(team.getName()); // Mets le joueur propriétaire de la team dans cette dernière
                        Main.invites.remove(p);// Supprime le joueur
                        p.sendMessage(ChatColor.GOLD + "Invitation acceptée !");
                        team.sendMessage(ChatColor.GOLD + p.getName() + " a accepté votre invitation !");
                    }else{
                        Main.invites.remove(p);// Supprime le joueur
                        p.sendMessage(ChatColor.YELLOW + "Invitation expirée !");
                    }
                }else{
                    p.sendMessage(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("decline")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if (Main.invites.get(p) != null){
                    Player playerSend = Main.invites.get(p);
                    p.sendMessage(ChatColor.RED + "Vous avez refuser l'invitation de " + playerSend.getName());
                    playerSend.sendMessage(ChatColor.RED + sender.getName() + " à refuser l'invitation !");
                    Main.invites.remove(p);
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

        if (command.getName().equalsIgnoreCase("msg")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(args.length != 0){
                    if(p.getPlayer().isOp()){
                        Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                        String message = ChatColor.DARK_RED + "ADMIN [" + p.getName() + "]";
                        if(r != null){
                            if(args[1] != null){
                                for(int i = 1 ; i < args.length ; i++ ){
                                    message = message + ChatColor.YELLOW + " " + args[i];
                                }
                                p.sendMessage(ChatColor.GOLD + "Message envoyé à " + r.getName() + " !");
                                r.sendMessage(message);
                            }
                        }
                    }else{
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !");
                    }
                }
            }
        }

        if (command.getName().equalsIgnoreCase("help")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(args.length != 0){
                    TextComponent messageHelp = new TextComponent(ChatColor.GREEN + "[RETOUR]");
                    messageHelp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"));
                    switch(args[0]) {
                        case "revive":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /revive PSEUDO ou /r PSEUDO");
                            p.sendMessage(ChatColor.GREEN + "Permet de réssuciter une personne morte !");
                            p.spigot().sendMessage(messageHelp);
                            p.sendMessage(ChatColor.GREEN + "######################");
                            break;
                        case "invite":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /invite PSEUDO ou /i PSEUDO");
                            p.sendMessage(ChatColor.GREEN + "Permet d'inviter un joueur dans son équipe !");
                            p.sendMessage(ChatColor.GREEN + "ATTENTION les invitations sont valide 60 secondes !");
                            p.spigot().sendMessage(messageHelp);
                            p.sendMessage(ChatColor.GREEN + "######################");

                            break;
                        case "accept":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /accept ou /a");
                            p.sendMessage(ChatColor.GREEN + "Permet d'accepter une invitation !");
                            p.spigot().sendMessage(messageHelp);
                            p.sendMessage(ChatColor.GREEN + "######################");
                            break;
                        case "decline":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /decline ou /d");
                            p.sendMessage(ChatColor.GREEN + "Permet de refuser une invitation !");
                            p.spigot().sendMessage(messageHelp);
                            p.sendMessage(ChatColor.GREEN + "######################");
                            break;
                        case "leave":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /leave ou /l");
                            p.sendMessage(ChatColor.GREEN + "Permet de quitter une équipe !");
                            p.sendMessage(ChatColor.GREEN + "Seule la personne qui quitte l'équipe est averti !");
                            p.spigot().sendMessage(messageHelp);
                            p.sendMessage(ChatColor.GREEN + "######################");
                            break;
                        default:
                    }
                }else{
                    p.sendMessage(ChatColor.GREEN + "######## AIDE ########");

                    // REVIVE
                    TextComponent messageRevive = new TextComponent(ChatColor.GREEN + "/leave");
                    messageRevive.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help leave"));
                    p.spigot().sendMessage(messageRevive);

                    // INVITE
                    TextComponent messageInvite = new TextComponent(ChatColor.GREEN + "/invite");
                    messageInvite.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help invite"));
                    p.spigot().sendMessage(messageInvite);

                    // ACCEPT
                    TextComponent messageAccept = new TextComponent(ChatColor.GREEN + "/accept");
                    messageAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help accept"));
                    p.spigot().sendMessage(messageAccept);

                    // DECLINE
                    TextComponent messageDecline = new TextComponent(ChatColor.GREEN + "/decline");
                    messageDecline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help decline"));
                    p.spigot().sendMessage(messageDecline);

                    // LEAVE
                    TextComponent messageLeave = new TextComponent(ChatColor.GREEN + "/revive");
                    messageLeave.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help revive"));
                    p.spigot().sendMessage(messageLeave);

                    p.sendMessage(ChatColor.GREEN + "######################");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("run")){
            if (sender instanceof Player){
                Timer.createTimer(tempsJeu);
                for(Player p : Bukkit.getOnlinePlayers()) {
                    //p.teleport(new Location(p.getWorld(), 0 ,hauteur ,0));
                    //p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20000, 1));
                }
            }
        }

        return true;
    }
}
