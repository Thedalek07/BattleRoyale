package me.dalek.battleroyale.commandes;

import me.dalek.battleroyale.Main;
import me.dalek.battleroyale.config.Config;
import me.dalek.battleroyale.defis.Arena;
import me.dalek.battleroyale.defis.Minidefis;
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

import java.util.*;

import static me.dalek.battleroyale.BotDiscord.Channels.createVoiceChannel;
import static me.dalek.battleroyale.BotDiscord.Channels.updateVoiceChannel;
import static me.dalek.battleroyale.context.Context.nameVocalChannel;
import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.initialisation.Init.*;
import static me.dalek.battleroyale.initialisation.Init.getListSpawn;
import static me.dalek.battleroyale.messages.Messages.enum_Msg.*;

public class Commandes implements CommandExecutor {

    private static final int sizeTeamMax = 3; // Nombre de joueurs MAX par équipe.
    private static final int distanceMax = 100; // Distance MAX pour faire une demande d'invite
    private static final long timeoutInvite = 60000; // Timeout d'une invite
    private static final int dureeSlowFalling = 2000; // Durée de l'effet SlowFalling en début de partie
    private static long millisRun = 0;
    public static boolean partieLancer = false;
    private static final HashMap<Player, Player> invites = new HashMap<>();
    private static final HashMap<String, Long> timeout = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if ((command.getName().equalsIgnoreCase("invite")) && (sender instanceof Player) && world.getPVP()){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if((args != null) && (args.length != 0)){
                    Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                    Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                    if((r != null) && (r != p) && r.getGameMode().equals(GameMode.SURVIVAL)) { // Vérifie si l'argument est bien le nom d'un joueur
                            if(invites.get(r) != p){
                                if(sb.getTeam(p.getName()) == null) { // Verifie si la team existe sinon on crée une team portant le nom de joueur qui invite
                                    // Créer la team
                                    sb.registerNewTeam(p.getName());
                                    Objects.requireNonNull(sb.getTeam(p.getName())).setAllowFriendlyFire(false);
                                }
                                if(Objects.requireNonNull(sb.getTeam(p.getName())).getSize() < sizeTeamMax){
                                    if(p.getLocation().distanceSquared(r.getLocation()) < distanceMax){
                                        // Messages indiquant la demande d'invitaiton
                                        p.sendMessage(String.format(String.valueOf(MSG_PLAYER_INVIT), r.getName()));
                                        r.sendMessage(String.format(String.valueOf(MSG_PLAYER_INVIT_BY), p.getName()));
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

        if ((command.getName().equalsIgnoreCase("accept")) && sender instanceof Player && ((Player) sender).getGameMode().equals(GameMode.SURVIVAL)){
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
                }else{ send_Message(p, MSG_PLAYER_AUCUNE_INVITATION); }
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
            if(myTeam != null && !myTeam.getName().equals(p.getName())){ // leave d'une team
                myTeam.removePlayer(p);
                p.sendMessage(String.format(String.valueOf(MSG_PLAYER_QUITTE_EQUIPE), p.getName()));
            }else if(myTeam != null && myTeam.getName().equals(p.getName())){ // leave de sa propre team
                for(OfflinePlayer offlineplayers : myTeam.getPlayers()){
                    myTeam.removePlayer(offlineplayers);
                }
                send_Message(p, MSG_PLAYER_LEAVE_MY_TEAM);
            }else {
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
                //pointSpawn(Bukkit.getOnlinePlayers().size(), 490);
                pointSpawn(13, 490);
                Timer.createTimer();
                partieLancer = true;
                System.out.println("LANCEMENT DE LA PARTIE !");
                Init.setGamerules();
                Init.resetPlayer();
                Init.resetWorld();
                Arena.closeDefis();
            Worldborder.phase(1);
                Minidefis.closeMiniDefis();
                resetStatistic();
                millisRun = System.currentTimeMillis();
                Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
                int i = 1;
                for(Player player : Bukkit.getOnlinePlayers()){
                    Team myTeam = sb.getPlayerTeam(player);
                    if(myTeam != null){ // leave d'une team
                        myTeam.removePlayer(player);
                    }
                    int id = new Random().nextInt(getListSpawn().size());
                    Bukkit.broadcastMessage("ID = " + id + "/" + getListSpawn().size() + " -> " + getListSpawn().get(id).getX() + " " + getListSpawn().get(id).getY() + " " + getListSpawn().get(id).getZ());
                    player.teleport(getListSpawn().get(id));
                    removeListSpawn(getListSpawn().get(id));
                    potions(player, PotionEffectType.SLOW_FALLING, dureeSlowFalling, 1);
                    i++;
                }
        }

        if ((command.getName().equalsIgnoreCase("synchro")) && (sender instanceof Player)){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendTitle(ChatColor.RED + "GG", "Synchronisation video");
                p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
            }
        }

        if ((command.getName().equalsIgnoreCase("record")) && (sender instanceof Player)){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(String.valueOf(MSG_PLAYER_RECORD_LANCER_TITLE));
                messageCliquable(p, ChatColor.GREEN, "OUI", String.format(String.valueOf(MSG_PLAYER_RECORD_LANCER), p.getName()));
            }
        }

        if ((command.getName().equalsIgnoreCase("spawn")) && (sender instanceof Player) && args != null && args.length == 1){
            int index = Integer.parseInt(args[0]);
            ((Player) sender).teleport((getListSpawn().get(index)));
        }

        if (command.getName().equalsIgnoreCase("pause")){
            Timer.pause();
        }

        if (command.getName().equalsIgnoreCase("start")){
            Timer.play();
            Timer.restartLoc();
        }

        if (command.getName().equalsIgnoreCase("open")){
            if (sender instanceof Player && sender.isOp()){
                createVoiceChannel();
            }
        }

        if (command.getName().equalsIgnoreCase("update")){
            if (sender instanceof Player && sender.isOp()){
                updateVoiceChannel();
            }
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

    public static boolean getPartieLancer(){
        return partieLancer;
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

    public static long getMillisRun(){
        return millisRun;
    }

    public static void potions(Player p, PotionEffectType potion, int duree, int level){
        p.addPotionEffect(new PotionEffect(potion, duree, level));
    }

}
