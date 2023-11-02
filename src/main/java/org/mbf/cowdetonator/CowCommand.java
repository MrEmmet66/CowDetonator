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

import java.util.*;

public class CowCommand implements CommandExecutor {
    public static Map<UUID, ArrayList<Cow>> Cows = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player!");
            return false;
        }
        Player player = (Player)sender;
        if(!player.hasPermission("cowbomb.plantcow")){
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
        ItemStack planter = new ItemStack(Material.GUNPOWDER);
        ItemMeta planterMeta = planter.getItemMeta();
        planterMeta.setDisplayName(ChatColor.RED + "Cow Planter");
        planterMeta.setLore(Arrays.asList(
                "Right click on block to plant Cow bomb!"
        ));
        planterMeta.getPersistentDataContainer().set(Keys.CUSTOM_PLANTER, PersistentDataType.BOOLEAN, true);
        planter.setItemMeta(planterMeta);
        if(!Cows.containsKey(player.getUniqueId())) {
            player.getInventory().addItem(detonator);
            player.getInventory().addItem(planter);
        }
        return true;

    }
}
