package me.dalek.battleroyale.BotDiscord;

import me.dalek.battleroyale.statistiques.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import java.util.List;

public class TableToImage {

    public static void CreateTableScore() {
        // Création des données de la table
        String[] columnNames = {"Classement", "Pseudo", "Kills", "Dégats infligés", "Distance", "Temps de survie", "Trade avec Gérard"};
        /*Object[][] data = {
                {5, "The_Dalek", 0, 100, 453, "10:21", 2},
                {2, "Bioscar1256", 1, 450, 873, "24:54", 2},
                {1, "Velanocite", 3, 1240, 1924, "45:14", 2},
                {4, "SarahR12", 0, 150, 211, "08:52", 2},
                {3, "Boutdelardon", 1, 380, 489, "35:53", 2}
        };*/

        Object[][] data = getStatsPlayers();

        // Création du modèle de table et JTable
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Configuration des largeurs de colonne
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);

        // Création d'une BufferedImage pour le rendu
        int imageWidth = 1920;
        int imageHeight = 1080;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Couleur de fond et police de titre
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String title = "Résultats du Battle Royal 3 du " + format.format(Calendar.getInstance().getTime());
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.drawString(title, (imageWidth - fm.stringWidth(title)) / 2, 100);

        // En-têtes de table
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(90, 150, imageWidth - 180, 60);
        g.setColor(Color.BLACK);
        int[] colPositions = {120, 380, 600, 720, 1000, 1200, 1500};
        for (int i = 0; i < columnNames.length; i++) {
            g.drawString(columnNames[i], colPositions[i], 200);
        }

        // Lignes de table
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        for (int i = 0; i < data.length; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(90, 220 + i * 60, imageWidth - 180, 60);
            g.setColor(Color.BLACK);
            for (int j = 0; j < data[i].length; j++) {
                g.drawString(String.valueOf(data[i][j]), colPositions[j], 260 + i * 60);
            }
        }

        g.dispose();

        // Enregistrement de l'image dans un fichier
        try {
            File outputDir = new File("attachement");
            if (!outputDir.exists()) {
                outputDir.mkdir(); // Créer le dossier si nécessaire
            }
            ImageIO.write(image, "png", new File(outputDir, "table_image.png"));
            uploadImage(outputDir.getPath() + "/table_image.png", "1300423730941657179");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour télécharger l'image
    private static void uploadImage(String filePath, String salonID) {
        try {
            File file = new File(filePath);
            URL url = new URL("http://188.165.76.91:8080/upload?id=" + salonID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            String boundary = "===" + System.currentTimeMillis() + "===";
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream os = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8), true)) {

                writer.append("--" + boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append("\r\n");
                writer.append("Content-Type: image/png").append("\r\n");
                writer.append("\r\n").flush();

                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }

                writer.append("\r\n").append("--" + boundary + "--").append("\r\n").flush();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Bukkit.broadcastMessage("Résultats disponible sur Discord !");
                } else {
                    try (InputStream errorStream = conn.getErrorStream()) {
                        if (errorStream != null) {
                            String errorMessage = new BufferedReader(new InputStreamReader(errorStream))
                                    .lines().collect(Collectors.joining("\n"));
                            Bukkit.broadcastMessage("Erreur lors du téléchargement de l'image. Code de réponse: "
                                    + responseCode + " Message : " + errorMessage);
                        } else {
                            Bukkit.broadcastMessage("Erreur lors du téléchargement de l'image. Code de réponse: " + responseCode);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.broadcastMessage("Erreur lors du téléchargement de l'image : " + e.getMessage());
        }
    }

    private static Object[][] getStatsPlayers() {
        String[] columnNames = {"Classement", "Pseudo", "Kills", "Dégats infligés", "Distance", "Temps de survie", "Trade avec Gérard"};
        List<PlayerStats> playerData = new ArrayList<>();

        for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            String pseudo = player.getName();

            int kills = player.getStatistic(Statistic.PLAYER_KILLS);
            float degatsInfliges = player.getStatistic(Statistic.DAMAGE_DEALT) / 10.0f;
            float distance = ((float)player.getStatistic(Statistic.WALK_ONE_CM) +
                    (float)player.getStatistic(Statistic.SPRINT_ONE_CM) +
                    (float)player.getStatistic(Statistic.SWIM_ONE_CM) +
                    (float)player.getStatistic(Statistic.BOAT_ONE_CM) +
                    (float)player.getStatistic(Statistic.CROUCH_ONE_CM) +
                    (float)player.getStatistic(Statistic.WALK_UNDER_WATER_ONE_CM)
            ) / 100.0f;

            int totalTicks;
            // Calcul du temps total en survie
            if(player.getStatistic(Statistic.TOTAL_WORLD_TIME) == player.getStatistic(Statistic.TIME_SINCE_DEATH)){
                totalTicks = player.getStatistic(Statistic.TOTAL_WORLD_TIME) + 20;
            }else {
                totalTicks = player.getStatistic(Statistic.TOTAL_WORLD_TIME) - player.getStatistic(Statistic.TIME_SINCE_DEATH);
            }
            int totalSurvieEnSecondes = totalTicks / 20; // Convert ticks to seconds

            // Conversion
            int minutesSurvie = totalSurvieEnSecondes / 60;
            int secondesSurvie = totalSurvieEnSecondes % 60;
            String tempsSurvie = String.format("%02d:%02d", minutesSurvie, secondesSurvie);

            int tradeGerard = player.getStatistic(Statistic.TRADED_WITH_VILLAGER);

            // Add data to the playerStats list
            playerData.add(new PlayerStats(pseudo, kills, degatsInfliges, distance, tempsSurvie, tradeGerard, totalSurvieEnSecondes));
        }

        // Sort players by total survival time
        playerData.sort(Comparator.comparingInt(PlayerStats::getTotalSurvieEnSecondes).reversed());

        // Convert the list to a 2D array for the JTable model
        Object[][] result = new Object[playerData.size()][columnNames.length];
        for (int i = 0; i < playerData.size(); i++) {
            PlayerStats stats = playerData.get(i);
            result[i] = new Object[]{
                    i + 1, // Assign rank based on position in sorted list
                    stats.getPseudo(),
                    stats.getKills(),
                    stats.getDegatsInfliges(),
                    stats.getDistance(),
                    stats.getTempsSurvie(),
                    stats.getTradeGerard()
            };
        }

        return result;
    }

}
