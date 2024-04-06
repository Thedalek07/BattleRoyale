package me.dalek.battleroyale;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public final class Untitled extends JavaPlugin {

    HashMap<Player, Player> invites = new HashMap<>();

    @Override
    public void onEnable() {
        System.out.println("Battle Royale à démarré !");

        // SCOREBOARD
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getNewScoreboard();

                    // RECUPERE LA VALEUR DE LA BOSSBAR
                    BossBar timerCoffre = getServer().getBossBar(NamespacedKey.minecraft("2"));

                    // RECUPERE LA VALEUR DE LA BOSSBAR
                    WorldBorder wB = Bukkit.getServer().getWorld(p.getWorld().getName()).getWorldBorder();
                    Double wbSize = wB.getSize();

                    // RECUPERE LES COORDONNEES
                    Double val_X = p.getLocation().getX();
                    Double val_Y = p.getLocation().getY();
                    Double val_Z = p.getLocation().getZ();

                    // CREER L'OBJECTIFS
                    Objective objective = scoreboard.registerNewObjective("TimerScore", "dummy", ChatColor.BLUE + "Battle Royale" );
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    // COORDONNEES
                    Score co = objective.getScore(ChatColor.GOLD + "Coordonnées");
                    co.setScore(5);
                    Score co_val = objective.getScore(ChatColor.RED + val_X.toString().substring(0, val_X.toString().indexOf(".")) + " / " + val_Y.toString().substring(0, val_Y.toString().indexOf(".")) + " / " + val_Z.toString().substring(0, val_Z.toString().indexOf(".")));
                    co_val.setScore(4);

                    // WORLDBORDER
                    Score border = objective.getScore(ChatColor.GOLD + "WorldBorder");
                    border.setScore(3);
                    Score border_val = objective.getScore(ChatColor.RED + wbSize.toString().substring(0, wbSize.toString().indexOf(".")));
                    border_val.setScore(2);

                    // COFFRES
                    Score coffre_label = objective.getScore(ChatColor.GOLD + "Coffres");
                    coffre_label.setScore(1);
                    Score coffre_val = objective.getScore(ChatColor.RED + timerCoffre.getTitle());
                    coffre_val.setScore(0);

                    p.setScoreboard(scoreboard);
                }
            }
        }, 0L, 10L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("revive")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(args != null){
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
                    p.sendMessage(ChatColor.RED + "Argument invalide !");
                    p.sendMessage(ChatColor.YELLOW + "Rappel : /revive PSEUDO");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("invite")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                Player r = Bukkit.getPlayer(args[0]); // Joueur en argument
                Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);
                if(r != null) { // Vérifie si l'argument est bien le nom d'un joueur
                    if(r != p){ // Vérifie que le joueur sélectionné est différent de celui qui exécute la commande
                        if (sb.getTeam(p.getName()) == null) { // Verifie si la team existe sinon on crée une team portant le nom de joueur qui invite
                            // Créer la team
                            sb.registerNewTeam(p.getName());
                            sb.getTeam(p.getName()).setAllowFriendlyFire(false);
                            sb.getTeam(p.getName()).addEntry(p.getName());
                        }

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
                        invites.put(r,p);
                    }else{
                        p.sendMessage(ChatColor.YELLOW + "Vous ne pouvez pas vous inviter dans votre équipe !");
                    }
                }else{
                    p.sendMessage(ChatColor.YELLOW + "Joueur introuvable !");
                }

            }
        }

        if (command.getName().equalsIgnoreCase("accept")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                if(invites.containsKey(p)){
                    Player team = invites.get(p);
                    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                    sb.getTeam(team.getName()).addEntry(p.getName());
                    // Supprime le joueur
                    invites.remove(p);
                    p.sendMessage(ChatColor.GOLD + "Invitation acceptée !");
                    team.sendMessage(ChatColor.GOLD + p.getName() + " a accepté votre invitation !");
                } else {
                    p.sendMessage(ChatColor.YELLOW + "Tu n'a pas d'invitation en attente !");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("decline")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                Player playerSend = invites.get(p);
                p.sendMessage(ChatColor.RED + "Vous avez refuser l'invitation de " + playerSend.getName());
                playerSend.sendMessage(ChatColor.RED + sender.getName() + " à refuser l'invitation !");
                invites.remove(p);
            }
        }

        if (command.getName().equalsIgnoreCase("leave")){
            if (sender instanceof Player){
                Player p = (Player) sender; // Joueur qui exécute la commande
                Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
                Team myTeam = sb.getPlayerTeam(p);
                myTeam.removePlayer(p);
                p.sendMessage(ChatColor.GOLD + "Vous venez de quitter l'équipe " + myTeam.getName() + " !");
            }
        }

        return true;
    }

}
