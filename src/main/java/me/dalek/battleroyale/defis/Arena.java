package me.dalek.battleroyale.defis;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import static me.dalek.battleroyale.context.Context.world;
import static me.dalek.battleroyale.messages.Messages.msgConsole.MSG_CONSOLE_ARENE_OUVERTE;
import static org.bukkit.Bukkit.getWorlds;

public class Arena {

    public static void openDefis(){
        // CONSOLE
        System.out.println(MSG_CONSOLE_ARENE_OUVERTE);

        // OUVERTURE DE L'ARENE NUMERO 1
        setBlock(261, 77, -5, Material.AIR);
        setBlock(261, 77, -4, Material.AIR);
        setBlock(261, 77, -3, Material.AIR);
        setBlock(261, 76, -5, Material.AIR);
        setBlock(261, 76, -4, Material.AIR);
        setBlock(261, 76, -3, Material.AIR);
        setBlock(261, 75, -5, Material.AIR);
        setBlock(261, 75, -4, Material.AIR);
        setBlock(261, 75, -3, Material.AIR);

        setBlock(238, 72, -5, Material.AIR);
        setBlock(238, 71, -5, Material.AIR);
        setBlock(238, 70, -5, Material.AIR);
        setBlock(238, 72, -4, Material.AIR);
        setBlock(238, 71, -4, Material.AIR);
        setBlock(238, 70, -4, Material.AIR);
        setBlock(238, 72, -3, Material.AIR);
        setBlock(238, 71, -3, Material.AIR);
        setBlock(238, 70, -3, Material.AIR);

        spawnVindicateur();

        // OUVERTURE DE L'ARENE NUMERO 2
        setBlock(-234, 66, -6, Material.AIR);
        setBlock(-234, 66, -5, Material.AIR);
        setBlock(-234, 66, -4, Material.AIR);
        setBlock(-234, 65, -6, Material.AIR);
        setBlock(-234, 65, -5, Material.AIR);
        setBlock(-234, 65, -4, Material.AIR);
        setBlock(-234, 64, -6, Material.AIR);
        setBlock(-234, 64, -5, Material.AIR);
        setBlock(-234, 64, -4, Material.AIR);

        setBlock(-257, 61, -6, Material.AIR);
        setBlock(-257, 60, -6, Material.AIR);
        setBlock(-257, 59, -6, Material.AIR);
        setBlock(-257, 61, -5, Material.AIR);
        setBlock(-257, 60, -5, Material.AIR);
        setBlock(-257, 59, -5, Material.AIR);
        setBlock(-257, 61, -4, Material.AIR);
        setBlock(-257, 60, -4, Material.AIR);
        setBlock(-257, 59, -4, Material.AIR);
        spawnMagmaCube();
        spawnExterminate();

        mobsInferieurAreneUne();
    }

    public static void closeDefis(){
        // FERMETURE DE L'ARENE NUMERO 1
        setBlock(261, 77, -5, Material.BARRIER);
        setBlock(261, 77, -4, Material.BARRIER);
        setBlock(261, 77, -3, Material.BARRIER);
        setBlock(261, 76, -5, Material.BARRIER);
        setBlock(261, 76, -4, Material.BARRIER);
        setBlock(261, 76, -3, Material.BARRIER);
        setBlock(261, 75, -5, Material.BARRIER);
        setBlock(261, 75, -4, Material.BARRIER);
        setBlock(261, 75, -3, Material.BARRIER);

        setBlock(238, 72, -5, Material.BARRIER);
        setBlock(238, 71, -5, Material.BARRIER);
        setBlock(238, 70, -5, Material.BARRIER);
        setBlock(238, 72, -4, Material.BARRIER);
        setBlock(238, 71, -4, Material.BARRIER);
        setBlock(238, 70, -4, Material.BARRIER);
        setBlock(238, 72, -3, Material.BARRIER);
        setBlock(238, 71, -3, Material.BARRIER);
        setBlock(238, 70, -3, Material.BARRIER);

        // FERMETURE DE L'ARENE NUMERO 2
        setBlock(-234, 66, -6, Material.BARRIER);
        setBlock(-234, 66, -5, Material.BARRIER);
        setBlock(-234, 66, -4, Material.BARRIER);
        setBlock(-234, 65, -6, Material.BARRIER);
        setBlock(-234, 65, -5, Material.BARRIER);
        setBlock(-234, 65, -4, Material.BARRIER);
        setBlock(-234, 64, -6, Material.BARRIER);
        setBlock(-234, 64, -5, Material.BARRIER);
        setBlock(-234, 64, -4, Material.BARRIER);

        setBlock(-257, 61, -6, Material.BARRIER);
        setBlock(-257, 60, -6, Material.BARRIER);
        setBlock(-257, 59, -6, Material.BARRIER);
        setBlock(-257, 61, -5, Material.BARRIER);
        setBlock(-257, 60, -5, Material.BARRIER);
        setBlock(-257, 59, -5, Material.BARRIER);
        setBlock(-257, 61, -4, Material.BARRIER);
        setBlock(-257, 60, -4, Material.BARRIER);
        setBlock(-257, 59, -4, Material.BARRIER);
    }

    private static void setBlock(int x, int y, int z, Material block){
        world.getBlockAt(x, y, z).setType(block);
    }

    private static void spawnVindicateur(){
        for(int i = 0 ; i<5 ; i++){
            LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, 242, 75 , -4), EntityType.VINDICATOR);
            mob.setRemoveWhenFarAway(false);
        }
    }

    private static void spawnExterminate(){
        for(int i = 0 ; i<2 ; i++){
            Zombie exterminate = (Zombie) world.spawnEntity(new Location(world, 257, 75 , -4), EntityType.ZOMBIE);
            exterminate.setCustomName(ChatColor.DARK_RED + "Exterminate");
            exterminate.setPersistent(true);
            LivingEntity zombie = exterminate;
            zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
        }
    }

    private static void spawnMagmaCube(){
        for(int i = 0 ; i<3 ; i++){
            LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, -253, 64 , -5), EntityType.MAGMA_CUBE);
            mob.setRemoveWhenFarAway(false);
        }
        for(int i = 0 ; i<3 ; i++){
            LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, -253, 64 , -5), EntityType.SLIME);
            mob.setRemoveWhenFarAway(false);
        }
    }

    private static void mobsInferieurAreneUne(){
        for (int x = 240 ; x<=258 ; x= x+2){
            summonSkeleton(x+0.5, 70, -9.5); // LIGNE 1
            summonSkeleton(x+0.5, 70, 2.5); // LIGNE 2
        }
    }

    private static void summonSkeleton(double x, double y, double z){
        LivingEntity mob = (LivingEntity) world.spawnEntity(new Location(world, x, y, z), EntityType.SKELETON);
        mob.setInvulnerable(true);
        mob.setRemoveWhenFarAway(false);
    }
}
