package me.dalek.battleroyale.statistiques;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class Coordinates {

    // Méthode pour enregistrer les coordonnées des joueurs
    public static void logPlayerCoordinates() {
        JSONArray playersData = new JSONArray();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() == GameMode.SURVIVAL){
                JSONObject playerData = new JSONObject();
                UUID playerId = player.getUniqueId();
                Location loc = player.getLocation();

                playerData.put("uuid", playerId.toString());
                playerData.put("name", player.getName());
                playerData.put("x", (int)loc.getX());
                playerData.put("y", (int)loc.getY());
                playerData.put("z", (int)loc.getZ());

                playersData.put(playerData);
            }
        }

        saveToJsonFile(playersData);
    }

    // Méthode pour sauvegarder les données JSON dans un fichier
    private static void saveToJsonFile(JSONArray playersData) {
        try (FileWriter file = new FileWriter("players_coordinates.json")) {
            file.write(playersData.toString(4));
        } catch (IOException e) {
            getLogger().severe("Erreur lors de la sauvegarde des coordonnées des joueurs: " + e.getMessage());
        }
    }

}
