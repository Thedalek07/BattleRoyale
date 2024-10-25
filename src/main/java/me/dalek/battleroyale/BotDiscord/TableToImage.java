package me.dalek.battleroyale.BotDiscord;

import org.bukkit.Bukkit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;

public class TableToImage {

    public static void CreateTableScore() {
        // Create the table data
        String[] columnNames = {"Pseudo", "Kills", "Damage", "Bloc parcouru"};
        Object[][] data = {
                {"The_Dalek", 0, 100, 453},
                {"Bioscar1256", 1, 450, 873},
                {"Velanocite", 3, 1240, 1924},
                {"SarahR12", 0, 150, 211},
                {"Boutdelardon", 1, 380, 489}
        };

        // Create the table model and JTable
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Set column widths (adjust as needed)
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Name column
        table.getColumnModel().getColumn(1).setPreferredWidth(50);  // Kills column
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Damage column

        // Create a BufferedImage for rendering (tripled size)
        int imageWidth = 1920;
        int imageHeight = 1080;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Set the background color and title font
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);

        // Draw title
        g.setFont(new Font("Arial", Font.BOLD, 60)); // Increased font size for title
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String title = "Résultat du Battle Royal 3 du " + format.format(calendar.getTime());
        FontMetrics fm = g.getFontMetrics();
        int titleX = (imageWidth - fm.stringWidth(title)) / 2; // Center the title
        g.setColor(Color.BLACK);
        g.drawString(title, titleX, 100); // Draw title (adjusted Y position)

        // Draw table headers
        g.setFont(new Font("Arial", Font.BOLD, 42)); // Header font
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(90, 150, imageWidth - 180, 60); // Background for header
        g.setColor(Color.BLACK);
        g.drawString("Pseudo", 120, 200); // Pseudo header
        g.drawString("Kills", 400, 200); // Kills header
        g.drawString("Damage", 600, 200); // Damage header
        g.drawString("Distance parcouru", 900, 200); // Damage header

        // Draw table rows
        g.setFont(new Font("Arial", Font.PLAIN, 30)); // Row font
        for (int i = 0; i < data.length; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(90, 220 + i * 60, imageWidth - 180, 60); // Row background
            g.setColor(Color.BLACK);
            g.drawString((String) data[i][0], 120, 260 + i * 60); // Name
            g.drawString(String.valueOf(data[i][1]), 400, 260 + i * 60); // Kills
            g.drawString(String.valueOf(data[i][2]), 600, 260 + i * 60); // Damage
            g.drawString(String.valueOf(data[i][3]), 900, 260 + i * 60); // Distance parcouru
        }

        g.dispose();

        // Save the image to a file
        try {
            ImageIO.write(image, "png", new File("table_image.png"));
            Bukkit.broadcastMessage("Le tableau des scores à été publié !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
