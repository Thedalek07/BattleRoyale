package me.dalek.battleroyale.messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Messages {
    public enum enum_Msg {
        MSG_PLAYER_JOIN_SERVER(ChatColor.GOLD + "Bienvenue %s sur le serveur de Bioscar et Dalek!"),
        MSG_PLAYER_RECORD_LANCER_TITLE(ChatColor.GOLD + "Lance ton record !\nUne fois fait appuyez sur OUI"),
        MSG_PLAYER_RECORD_LANCER("/tellraw @a \"%s à lancé sont record\""),
        MSG_PLAYER_REZ(ChatColor.GREEN + "Vous avez ressuscité %s !"),
        MSG_PLAYER_IN_LIFE(ChatColor.RED + "Vous ne pouvez pas ressusciter un joueur en vie !"),
        MSG_PLAYER_INVALIDE(ChatColor.YELLOW + "Joueur invalide !"),
        MSG_PLAYER_MANQUE_TOTEM(ChatColor.RED + "Il faut un totem pour ressusciter des joueurs !"),
        MSG_PLAYER_ARGS_INVALIDE(ChatColor.RED + "Argument invalide !\n" + ChatColor.YELLOW + "Rappel : /revive PSEUDO"),
        MSG_PLAYER_REZ_BY(ChatColor.GREEN + "Vous avez été ressuscité par %s !"),
        MSG_PLAYER_INVIT(ChatColor.GOLD + "Invitation envoyée à %s"),
        MSG_PLAYER_LEAVE_MY_TEAM(ChatColor.GOLD + "Vous avez bien quitté votre équipe !"),
        MSG_PLAYER_INVIT_BY(ChatColor.GOLD + "Vous avez reçu une invitation de %s !"),
        MSG_PLAYER_LOIN(ChatColor.YELLOW + "%s est trop loin pour être invité !"),
        MSG_PLAYER_TEAM_COMPLET(ChatColor.RED + "Votre équipe est au complet !"),
        MSG_PLAYER_INVIT_ENVOYE(ChatColor.YELLOW + "Vous avez déjà envoyé une invitation à %s !"),
        MSG_PLAYER_INIVTE_ME(ChatColor.YELLOW + "Vous ne pouvez pas inviter ce joueur !"),
        MSG_PLAYER_MANQUANT(ChatColor.YELLOW + "Il faut indiquer un joueur à inviter !"),
        MSG_PLAYER_INIVTE_ACCEPT(ChatColor.GOLD + "Invitation acceptée !"),
        MSG_PLAYER_INIVTE_ACCEPT_SENDER(ChatColor.GOLD + "%s a accepté votre invitation !"),
        MSG_PLAYER_INVITE_EXPIRE(ChatColor.YELLOW + "Invitation expirée !"),
        MSG_PLAYER_INVITE_REFUSER(ChatColor.RED + "Vous avez refuser l'invitation de %s !"),
        MSG_PLAYER_INVITE_REFUSER_BY(ChatColor.RED + "%s à refuser l'invitation !"),
        MSG_PLAYER_AUCUNE_INVITATION(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !"),
        MSG_PLAYER_AUCUNE_EQUIPE(ChatColor.RED + "Vous n'êtes pas dans une équipe ! "),
        MSG_PLAYER_QUITTE_EQUIPE(ChatColor.GOLD + "Vous venez de quitter l'équipe %s !"),
        MSG_PLAYER_PAS_PERMISSION(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !"),
        MSG_PLAYER_VICTOIRE(ChatColor.GOLD + "%s est le gagnant du Battle Royal !"),
        MSG_PLAYER_VICTOIRE_TEAM(ChatColor.GOLD + "L’équipe %s à gagner !"),
        MSG_CONSOLE_COFFRE_UN(ChatColor.WHITE + "COFFRE 1 SPAWN !"),
        MSG_PLAYER_COFFRE_UN(ChatColor.GOLD + "Le coffre 1 est apparu !"),
        MSG_CONSOLE_COFFRE_DEUX(ChatColor.WHITE + "COFFRE 2 SPAWN !"),
        MSG_PLAYER_COFFRE_DEUX(ChatColor.GOLD + "Le coffre 2 est apparu !"),
        MSG_PLAYER_ADMIN_ENTETE(ChatColor.DARK_RED + "ADMIN [%s]"),
        MSG_PLAYER_ADMIN_ENVOYE(ChatColor.GOLD + "Message envoyé à %s !"),
        MSG_PLAYER_MORTS("%s est mort(e) !"),
        MSG_PLAYER_REDUCTION_WORLDBORDER(ChatColor.GOLD + "La worldborder se réduit de %d blocks !"),
        MSG_PLAYER_PVP_ACTIF(ChatColor.GOLD + "Le PvP est activé"),
        MSG_PLAYER_INFO_PLUGIN(ChatColor.GOLD + "Plugin Battle Royale V" + Bukkit.getServer().getPluginManager().getPlugin("BattleRoyale").getDescription().getVersion() + " \nPropriété de Bioscar et Dalek \nFait par The_dalek"),
        ;

        private final String text;

        enum_Msg(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum msgConsole {
        MSG_CONSOLE_PLUGIN_RUN("Battle Royale à démarré !"),
        MSG_CONSOLE_ARENE_OUVERTE("L'ARENE EST OUVERTE !"),
        ;
        private final String text;

        msgConsole(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
