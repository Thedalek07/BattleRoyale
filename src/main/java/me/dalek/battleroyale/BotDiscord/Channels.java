package me.dalek.battleroyale.BotDiscord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static me.dalek.battleroyale.context.Context.adresseIpBotDiscord;
import static me.dalek.battleroyale.context.Context.nameVocalChannel;

public class Channels {

    public static void createVoiceChannel(){
        request("create-voice-channel");
    }

    public static void updateVoiceChannel(){
        request("update-voice-channel");
    }

    public static void request(String type) {
        try {
            // Encoder le nom du salon
            String encodedChannelName = URLEncoder.encode(nameVocalChannel, StandardCharsets.UTF_8.toString());
            StringBuilder users = new StringBuilder();
            for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
                if (users.length() > 0) {
                    users.append(","); // Ajouter une virgule avant chaque nom, sauf le premier
                }
                users.append(player.getName()); // Ajouter le nom du joueur
            }
            String userNames = users.toString();

            String[] urlParts = new String[]{
                    "http://" + adresseIpBotDiscord + ":8080/",
                    "?action=" + type,
                    "&name=" + encodedChannelName,
                    "&users=" + userNames,
                    "&timeout=" + 5,
                    "&move=" + false
            };

            String urlString = String.join("", urlParts);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            StringBuilder responseBody = new StringBuilder();

            // Read the response body
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
            }

            // Handle the response based on the code
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "BOT DISCORD : " + responseBody);
            } else {
                Bukkit.broadcastMessage(ChatColor.RED + "BOT DISCORD : " + responseBody);
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage(ChatColor.RED + "ERROR : " + e.getMessage());
        }
    }

    public static void sendGeneralMessage(String discordID, String message) {
        try {
            // Encode the message for URL
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            // Construct the URL for sending a message
            String[] urlParts = new String[]{
                    "http://" + adresseIpBotDiscord + ":8080/",
                    "?action=general-message",
                    "&ID=" + discordID,
                    "&message=" + encodedMessage
            };

            String urlString = String.join("", urlParts);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            StringBuilder responseBody = new StringBuilder();

            // Read the response body
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
            }

            // Handle the response based on the code
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Bukkit.broadcastMessage(ChatColor.RED + "BOT DISCORD : " + responseBody);
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage(ChatColor.RED + "ERROR : " + e.getMessage());
        }
    }

}
