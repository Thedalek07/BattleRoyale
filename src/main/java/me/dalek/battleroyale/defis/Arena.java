package me.dalek.battleroyale.defis;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.msgConsole.MSG_CONSOLE_ARENE_OUVERTE;

public class Arena {

    public static void openDefis() {
        // CONSOLE
        System.out.println(MSG_CONSOLE_ARENE_OUVERTE);

        // OUVERTURE DES ARENES
        openArena(-305, 60, 2);
        openArena(301, 57, 9);

        // SPAWN SKELETONS
        spawnEntities(Skeleton.class, new int[][] {
                {303, 57, 4}, {305, 57, 3}, {313, 57, 6}, {313, 57, 10},
                {305, 57, 13}, {303, 57, 12}, {318, 57, 10}, {325, 57, 13},
                {317, 57, 3}, {325, 57, 5}, {-287, 63, -2}, {-295, 63, -3},
                {-287, 63, 7}, {-294, 63, 4}, {-299, 63, 0}, {-299, 63, 4},
                {-307, 63, 7}, {-309, 63, 6}, {-307, 63, -2}, {-309, 63, -3}
        });

        // SPAWN WITHER SKELETONS
        spawnEntities(WitherSkeleton.class, new int[][] {
                {-287, 63, 0}, {-287, 63, -4}, {-294, 63, -2}, {-289, 63, 6},
                {-289, 63, 8}, {-294, 63, 6}, {-294, 63, 2}, {-309, 63, -2},
                {-307, 63, -3}, {-299, 63, 7}, {-299, 63, 2}, {-299, 63, -3},
                {-309, 63, 7}, {-307, 63, 6}, {325, 57, 7}, {325, 57, 3},
                {318, 57, 4}, {325, 57, 12}, {323, 57, 14}, {318, 57, 12},
                {318, 57, 8}, {303, 57, 3}, {305, 57, 4}, {313, 57, 3},
                {313, 57, 8}, {313, 57, 13}, {303, 57, 13}, {305, 57, 12}
        });

        // SPAWN RAVAGERS
        spawnRavagers(new int[][] {
                {311, 64, 11}, {311, 64, 5}, {-301, 69, 5}, {-301, 69, -1}
        });
    }

    public static void closeDefis() {
        // FERMETURE DES ARENES
        closeArena(-305, 60, 1);
        closeArena(302, 57, 9);
    }

    private static void openArena(int x, int y, int z) {
        setBlock(x, y, z, Material.REDSTONE_BLOCK);
        setBlock(x, y - 2, z, Material.REDSTONE_BLOCK);
        setBlock(x, y, z, Material.AIR);
        setBlock(x, y - 2, z, Material.AIR);
    }

    private static void closeArena(int x, int y, int z) {
        setBlock(x, y, z, Material.REDSTONE_BLOCK);
        setBlock(x, y - 2, z, Material.REDSTONE_BLOCK);
        setBlock(x, y, z, Material.AIR);
        setBlock(x, y - 2, z, Material.AIR);
    }

    private static void setBlock(int x, int y, int z, Material block) {
        world.getBlockAt(x, y, z).setType(block);
    }

    private static <T extends LivingEntity> void spawnEntities(Class<T> entityType, int[][] coordinates) {
        for (int[] coord : coordinates) {
            spawnEntity(entityType, coord[0], coord[1], coord[2]);
        }
    }

    private static <T extends LivingEntity> void spawnEntity(Class<T> entityType, int x, int y, int z) {
        String entityName = entityType.getSimpleName().toUpperCase();
        if (entityName.equals("WITHERSKELETON")) {
            entityName = "WITHER_SKELETON"; // Correction pour WitherSkeleton
        }

        T entity = (T) world.spawnEntity(new Location(world, x, y, z), EntityType.valueOf(entityName));
        entity.setRemoveWhenFarAway(false);
        equipEntity(entity);
    }


    private static void equipEntity(LivingEntity entity) {
        if (entity instanceof Skeleton) {
            entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
            ItemStack bow = createEnchantedItem(Material.BOW, Enchantment.ARROW_DAMAGE, 3);
            entity.getEquipment().setItemInMainHand(bow);
        } else if (entity instanceof WitherSkeleton) {
            entity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE));
        } else if (entity instanceof Ravager) {
            // Additional customization for Ravager if needed
        }
    }

    private static ItemStack createEnchantedItem(Material material, Enchantment enchantment, int level) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static void spawnRavagers(int[][] coordinates) {
        for (int[] coord : coordinates) {
            spawnRavager(coord[0], coord[1], coord[2]);
        }
    }

    private static void spawnRavager(int x, int y, int z) {
        Ravager ravager = (Ravager) world.spawnEntity(new Location(world, x, y, z), EntityType.RAVAGER);
        ravager.setRemoveWhenFarAway(false);

        Skeleton skeleton = (Skeleton) world.spawnEntity(new Location(world, x, y, z), EntityType.SKELETON);
        skeleton.setRemoveWhenFarAway(false);
        equipEntity(skeleton);

        ravager.addPassenger(skeleton);
    }

}
