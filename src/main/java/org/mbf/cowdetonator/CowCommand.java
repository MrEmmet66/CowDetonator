package org.mbf.cowdetonator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CowCommand implements CommandExecutor {
    public static ArrayList<Entity> Cows = new ArrayList<Entity>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player!");
            return false;
        }
        ItemStack detonator = new ItemStack(Material.STICK);
        ItemMeta meta = detonator.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Detonator");
        meta.setLore(Arrays.asList(
                "Right click to blow all cows!"
        ));
        meta.getPersistentDataContainer().set(Keys.CUSTOM_DETONATOR, PersistentDataType.BOOLEAN, true);
        detonator.setItemMeta(meta);
        Player player = (Player)sender;
        if(Cows.isEmpty())
            player.getInventory().addItem(detonator);
        Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);
        cow.getPersistentDataContainer().set(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN, true);
        if(!args[0].equals("sneaky"))
            cow.setCustomName(ChatColor.BLUE + "Just a cow...");
        cow.setCustomNameVisible(true);
        Cows.add(cow);
        return true;


    }
}
