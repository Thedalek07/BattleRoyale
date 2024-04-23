package me.dalek.battleroyale.commandes;

import me.dalek.battleroyale.Main;
import me.dalek.battleroyale.defis.Arena;
import me.dalek.battleroyale.initialisation.Init;
import me.dalek.battleroyale.messages.Messages;
import me.dalek.battleroyale.timer.Timer;
import me.dalek.battleroyale.worldborder.Worldborder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Objects;

import static me.dalek.battleroyale.messages.Messages.enum_Msg.*;

public class Commandes implements CommandExecutor {

    private static final int sizeTeamMax = 3; // Nombre de joueurs MAX par équipe.
    private static final int distanceMax = 100; // Distance MAX pour faire une demande d'invite
    private static final long timeoutInvite = 60000; // Timeout d'une invite
    private static final int hauteur = 300; // hauteur du tp de début de partie
    private static final int dureeSlowFalling = 2000; // Durée de l'effet SlowFalling en début de partie
    private static final HashMap<Player, Player> invites = new HashMap<>();
    private static final HashMap<String, Long> timeout = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if ((command.getName().equalsIgnoreCase("revive")) && (sender instanceof Player)){
            Player p = (Player) sender; // Joueur qui exécute la commande
            if((args != null) && (args.length != 0)){ // Vérifie les arguments de la commande
                Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                if(p.getInventory().contains(Material.ECHO_SHARD)){ // Vérifie si le joueur possède une echo shard (Totem)
                    if((r != null) && (r != p)) { // Vérifie si l'argument est bien le nom d'un joueur et si différend de lui meme
                        if(r.getGameMode() == GameMode.SPECTATOR){ // Vérifie si le joueur cible est en spectateur
                            // MESSAGES
                            p.sendMessage(String.format(String.valueOf(MSG_PLAYER_REZ), r.getName()));
                            r.sendMessage(String.format(String.valueOf(MSG_PLAYER_REZ_BY), p.getName()));

                            r.teleport(p.getLocation()); // Téléporte le joueur cible sur le joueur qui execute la cmd
                            r.setGameMode(GameMode.SURVIVAL); // Mets le joueur cible en survie

                            removeItem(p, Material.ECHO_SHARD);

                            soundsAll(Sound.ITEM_TOTEM_USE);
                        }else{ send_Message(p, MSG_PLAYER_IN_LIFE); } // Joueur en vie
                    }else{ send_Message(p, MSG_PLAYER_INVALIDE); } // Joueur invalide
                }else{ send_Message(p, MSG_PLAYER_MANQUE_TOTEM); } // Il faut un totem
            }else{ send_Message(p, MSG_PLAYER_ARGS_INVALIDE); } // Argument invalide
        }

        if ((command.getName().equalsIgnoreCase("invite")) && (sender instanceof Player)){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if((args != null) && (args.length != 0)){
                    Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                    Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                    if((r != null) && (r != p)) { // Vérifie si l'argument est bien le nom d'un joueur
                            if(invites.get(r) != p){
                                if(sb.getTeam(p.getName()) == null) { // Verifie si la team existe sinon on crée une team portant le nom de joueur qui invite
                                    // Créer la team
                                    sb.registerNewTeam(p.getName());
                                    Objects.requireNonNull(sb.getTeam(p.getName())).setAllowFriendlyFire(false);
                                }
                                if(Objects.requireNonNull(sb.getTeam(p.getName())).getSize() < sizeTeamMax){
                                    if(p.getLocation().distanceSquared(r.getLocation()) < distanceMax){
                                        // Messages indiquant la demande d'invitaiton
                                        send_Message(p, MSG_PLAYER_INVIT);
                                        send_Message(p, MSG_PLAYER_INVIT_BY);
                                        r.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);

                                        messageCliquable(r, ChatColor.GREEN, "Accepter", "/accept");
                                        messageCliquable(r, ChatColor.RED, "Refuser", "/decline");

                                        // Ajoute le joueur qui invite dans la liste de la cible
                                        invites.put(r,p);

                                        // Enregistre le temps
                                        timeout.put(p.getName(), System.currentTimeMillis());
                                    }else{ p.sendMessage(String.format(String.valueOf(MSG_PLAYER_LOIN), r.getName())); }
                                }else{ send_Message(p, MSG_PLAYER_TEAM_COMPLET); }
                            }else{ p.sendMessage(String.format(String.valueOf(MSG_PLAYER_INVIT_ENVOYE), r.getName())); }
                    }else { send_Message(p, MSG_PLAYER_INIVTE_ME); }
                }else{ send_Message(p, MSG_PLAYER_MANQUANT); }
        }

        if ((command.getName().equalsIgnoreCase("accept")) && sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(invites.containsKey(p)){
                    Player team = invites.get(p); // Recupère la demande d'invite en cours
                    if(timeout.get(team.getName()) >= (System.currentTimeMillis() - timeoutInvite)){
                        joinTeam(p, team);
                        joinTeam(team, team);
                        invites.remove(p);// Supprime le joueur
                        send_Message(p, MSG_PLAYER_INIVTE_ACCEPT);
                        team.sendMessage(String.format(String.valueOf(MSG_PLAYER_INIVTE_ACCEPT_SENDER), p.getName()));
                    }else{
                        invites.remove(p);// Supprime le joueur
                        send_Message(p, MSG_PLAYER_INVITE_EXPIRE);
                    }
                }else{ send_Message(p, MSG_PLAYER_INIVTE_ATTENTE); }
        }

        if ((command.getName().equalsIgnoreCase("decline")) && (sender instanceof Player)){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if (invites.get(p) != null){
                    Player playerSend = invites.get(p);
                    p.sendMessage(String.format(String.valueOf(MSG_PLAYER_INVITE_REFUSER), playerSend.getName()));
                    playerSend.sendMessage(String.format(String.valueOf(MSG_PLAYER_INVITE_REFUSER_BY), p.getName()));
                    invites.remove(p);
                }else {
                    send_Message(p, MSG_PLAYER_AUCUNE_INVITATION);
                }
        }

        if ((command.getName().equalsIgnoreCase("leave")) && (sender instanceof Player)){
            Player p = (Player) sender; // Joueur qui exécute la commande
            Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            Team myTeam = sb.getPlayerTeam(p);
            if(myTeam != null){
                myTeam.removePlayer(p);
                p.sendMessage(String.format(String.valueOf(MSG_PLAYER_QUITTE_EQUIPE), p.getName()));
            }else{
                send_Message(p, MSG_PLAYER_AUCUNE_EQUIPE);
            }
        }

        if ((command.getName().equalsIgnoreCase("msg")) && (sender instanceof Player)){
            Player p = (Player) sender; // Joueur qui exécute la commande
            if((args != null) && (args.length != 0)){
                if(Objects.requireNonNull(p.getPlayer()).isOp()){
                    Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                    String message = String.format(String.valueOf(MSG_PLAYER_ADMIN_ENTETE), p.getName());
                    if((r != null) && (args[1] != null)){
                        for(int i = 1 ; i < args.length ; i++ ){
                            message += ChatColor.YELLOW + " " + args[i];
                        }
                        p.sendMessage(String.format(String.valueOf(MSG_PLAYER_ADMIN_ENVOYE), r.getName()));
                        p.sendMessage(String.format(String.valueOf(MSG_PLAYER_MESSAGE_ENVOYEE), r.getName()));
                        r.sendMessage(message);
                    }
                }else{
                    send_Message(p, MSG_PLAYER_PAS_PERMISSION);
                }
            }
        }

        if ((command.getName().equalsIgnoreCase("help")) && (sender instanceof Player)){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if((args != null) && (args.length != 0)){
                    switch(args[0]) {
                        case "revive":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /revive PSEUDO ou /r PSEUDO");
                            p.sendMessage(ChatColor.GREEN + "Permet de réssuciter une personne morte !");
                            btnRetour(p);
                            break;
                        case "invite":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /invite PSEUDO ou /i PSEUDO");
                            p.sendMessage(ChatColor.GREEN + "Permet d'inviter un joueur dans son équipe !");
                            p.sendMessage(ChatColor.GREEN + "ATTENTION les invitations sont valide 60 secondes !");
                            btnRetour(p);
                            break;
                        case "accept":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /accept ou /a");
                            p.sendMessage(ChatColor.GREEN + "Permet d'accepter une invitation !");
                            btnRetour(p);
                            break;
                        case "decline":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /decline ou /d");
                            p.sendMessage(ChatColor.GREEN + "Permet de refuser une invitation !");
                            btnRetour(p);
                            break;
                        case "leave":
                            p.sendMessage(ChatColor.GREEN + "Syntaxe : /leave ou /l");
                            p.sendMessage(ChatColor.GREEN + "Permet de quitter une équipe !");
                            p.sendMessage(ChatColor.GREEN + "Seule la personne qui quitte l'équipe est averti !");
                            btnRetour(p);
                            break;
                        default:
                    }
                }else{
                    p.sendMessage(ChatColor.GREEN + "######## AIDE ########");
                    messageCliquable(p, ChatColor.GREEN, "/revive", "/help revive");
                    messageCliquable(p, ChatColor.GREEN, "/invite", "/help invite");
                    messageCliquable(p, ChatColor.GREEN, "/accept", "/help accept");
                    messageCliquable(p, ChatColor.GREEN, "/decline", "/help decline");
                    messageCliquable(p, ChatColor.GREEN, "/leave", "/help leave");
                    p.sendMessage(ChatColor.GREEN + "######################");
                }
        }

        if (command.getName().equalsIgnoreCase("run")){
                Timer.createTimer();
                for(Player p : Bukkit.getOnlinePlayers()) {
                    System.out.println("LANCEMENT DE LA PARTIE !");
                    p.teleport(new Location(p.getWorld(), 0 ,hauteur ,0));
                    potions(p, PotionEffectType.SLOW_FALLING, dureeSlowFalling, 1);
                    Init.setGamerules();
                    Init.resetPlayer();
                    Init.resetWorld();
                    Arena.closeDefis();
                    Worldborder.phase1();
                }
        }

        if (command.getName().equalsIgnoreCase("pause")){
            Timer.pause();
        }

        if (command.getName().equalsIgnoreCase("start")){
            Timer.play();
        }

        return true;
    }

    private void send_Message(Player p, Messages.enum_Msg enumMsg) {
        p.sendMessage(String.valueOf(enumMsg));
    }

    private static void btnRetour(Player p){
        messageCliquable(p, ChatColor.GREEN, "[RETOUR]", "/help");
        p.sendMessage(ChatColor.GREEN + "######################");
    }

    public static void messageCliquable(Player r, ChatColor color, String nom, String cmd){
        TextComponent message = new TextComponent(color + nom);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        r.spigot().sendMessage(message);
    }

    public static void soundsAll(Sound sons){
        for(Player pSound : Bukkit.getOnlinePlayers()) {
            pSound.playSound(pSound.getLocation(), sons, 10, 1);
        }
    }

    public static void removeItem(Player p, Material item){
        for(ItemStack invItem : p.getInventory().getContents()) {
            if(invItem != null) {
                if(invItem.getType().equals(item)) {
                    int preAmount = invItem.getAmount();
                    int newAmount = Math.max(0, preAmount - 1);
                    invItem.setAmount(newAmount);
                }
            }
        }
    }

    public static void joinTeam(Player p, Player team){
        Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Objects.requireNonNull(sb.getTeam(team.getName())).addEntry(p.getName());
    }

    public static void potions(Player p, PotionEffectType potion, int duree, int level){
        p.addPotionEffect(new PotionEffect(potion, duree, level));
    }
}
