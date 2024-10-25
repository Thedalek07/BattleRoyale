package me.dalek.battleroyale.Maps;

import me.dalek.battleroyale.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static me.dalek.battleroyale.context.Context.world;

public class ExportMaps {

    private final String filePath;

    public ExportMaps(String filePath) {
        this.filePath = filePath;
    }

    public void exportMapData() {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write("[\n");
                    List<String> blockData = new ArrayList<>();

                    // Parcours de la carte
                    for (int x = -100; x <= 100; x++) { // Ajuster les limites selon la taille de ta map
                        for (int z = -100; z <= 100; z++) {
                            Block block = world.getHighestBlockAt(x ,z); // Hauteur fixe à 64, ou ajuster selon les besoins
                            Material material = block.getType();
                            blockData.add(String.format(
                                    "{\"x\": %d, \"y\": %d, \"z\": %d, \"block\": \"%s\"}",
                                    x, 64, z, material.name().toLowerCase()));
                        }
                    }

                    // Écriture des données dans le fichier
                    for (int i = 0; i < blockData.size(); i++) {
                        writer.write(blockData.get(i));
                        if (i < blockData.size() - 1) {
                            writer.write(",\n");
                        }
                    }

                    writer.write("\n]"); // Clôturer le tableau JSON
                    Bukkit.getLogger().info("Map data exported successfully to " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(Main.getPlugin(), 0);
    }
}
