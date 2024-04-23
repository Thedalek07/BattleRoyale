package me.dalek.battleroyale.messages;

import org.bukkit.ChatColor;

public class Messages {
    public static enum enum_Msg {
        MSG_PLAYER_REZ(ChatColor.GREEN + "Vous avez réssucité %s !"),
        MSG_PLAYER_IN_LIFE(ChatColor.RED + "Tu ne peux pas réssuciter un joueur en vie !"),
        MSG_PLAYER_INVALIDE(ChatColor.YELLOW + "Joueur invalide !"),
        MSG_PLAYER_MANQUE_TOTEM(ChatColor.RED + "il te faut un totem pour réssuciter des joueurs!"),
        MSG_PLAYER_ARGS_INVALIDE(ChatColor.RED + "Argument invalide !\n" + ChatColor.YELLOW + "Rappel : /revive PSEUDO"),
        MSG_PLAYER_REZ_BY(ChatColor.GREEN + "Vous avez été réssucité par %s !"),
        MSG_PLAYER_INVIT(ChatColor.GOLD + "Invitation envoyé à %s"),
        MSG_PLAYER_INVIT_BY(ChatColor.GOLD + "Vous avez reçu une invitation de %s !"),
        MSG_PLAYER_LOIN(ChatColor.YELLOW + "%s est trop loin pour etre invité !"),
        MSG_PLAYER_TEAM_COMPLET(ChatColor.RED + "Votre équipe est au complet !"),
        MSG_PLAYER_INVIT_ENVOYE(ChatColor.YELLOW + "Vous avez déjà envoyé une demande d'invite à %s !"),
        MSG_PLAYER_INIVTE_ME(ChatColor.YELLOW + "Vous ne pouvez pas vous inviter dans votre équipe !"),
        MSG_PLAYER_MANQUANT(ChatColor.YELLOW + "Il faut indiquer un joueur à inviter !"),
        MSG_PLAYER_INIVTE_ACCEPT(ChatColor.GOLD + "Invitation acceptée !"),
        MSG_PLAYER_INIVTE_ACCEPT_SENDER(ChatColor.GOLD + "%s a accepté votre invitation !"),
        MSG_PLAYER_INVITE_EXPIRE(ChatColor.YELLOW + "Invitation expirée !"),
        MSG_PLAYER_INIVTE_ATTENTE(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !"),
        MSG_PLAYER_INVITE_REFUSER(ChatColor.RED + "Vous avez refuser l'invitation de %s"),
        MSG_PLAYER_INVITE_REFUSER_BY(ChatColor.RED + "%s à refuser l'invitation !"),
        MSG_PLAYER_AUCUNE_INVITATION(ChatColor.YELLOW + "Vous n'avez pas d'invitations en attentes !"),
        MSG_PLAYER_AUCUNE_EQUIPE(ChatColor.RED + "Vous n'etes pas dans une équipe ! "),
        MSG_PLAYER_QUITTE_EQUIPE(ChatColor.GOLD + "Vous venez de quitter l'équipe %s !"),
        MSG_PLAYER_MESSAGE_ENVOYEE(ChatColor.GOLD + "Message envoyé à %s !"),
        MSG_PLAYER_PAS_PERMISSION(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !"),
        MSG_PLAYER_VICTOIRE(ChatColor.GOLD + "%s est le gagnant du Battle Royal !"),
        MSG_PLAYER_VICTOIRE_TEAM(ChatColor.GOLD + "La team %s à gagnée !"),
        MSG_CONSOLE_COFFRE_UN(ChatColor.WHITE + "COFFRE 1 SPAWN !"),
        MSG_PLAYER_COFFRE_UN(ChatColor.GOLD + "Le coffre 1 est apparu !"),
        MSG_CONSOLE_COFFRE_DEUX(ChatColor.WHITE + "COFFRE 2 SPAWN !"),
        MSG_PLAYER_COFFRE_DEUX(ChatColor.GOLD + "Le coffre 2 est apparu !"),
        MSG_PLAYER_ADMIN_ENTETE(ChatColor.DARK_RED + "ADMIN [%s]"),
        MSG_PLAYER_ADMIN_ENVOYE(ChatColor.GOLD + "Message envoyé à %s !"),
        MSG_PLAYER_MORTS(ChatColor.DARK_RED + "%s est mort(e) !"),
        MSG_PLAYER_REDUCTION_WORLDBORDER(ChatColor.GOLD + "La worldborder se réduit de %d blocks !"),
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
}
