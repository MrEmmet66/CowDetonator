package org.mbf.cowdetonator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemListener implements Listener {
    ItemStack planter;

    @EventHandler
    public void onPlanterUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getHand() == EquipmentSlot.OFF_HAND)
            return;
        if(player.getInventory().getItemInMainHand().getItemMeta() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            PersistentDataContainer dataContainer = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if(dataContainer.has(Keys.CUSTOM_PLANTER, PersistentDataType.BOOLEAN)){
                Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);
                cow.getPersistentDataContainer().set(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN, true);
                CowCommand.Cows.computeIfAbsent(player.getUniqueId(), i -> new ArrayList<>()).add(cow);
                planter = player.getInventory().getItemInMainHand();
            }
        }
    }
    @EventHandler
    public void onDetonatorUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta() != null && event.getAction() == Action.RIGHT_CLICK_AIR){
            PersistentDataContainer playerContainer = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if(playerContainer.has(Keys.CUSTOM_DETONATOR, PersistentDataType.BOOLEAN)){
                for (Entity entity : CowCommand.Cows.get(player.getUniqueId())) {
                    if(entity instanceof Cow && entity.getPersistentDataContainer().has(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN)){
                        entity.getWorld().createExplosion(entity.getLocation(), 2);
                    }
                }
                CowCommand.Cows.remove(player.getUniqueId());
                player.getInventory().remove(player.getInventory().getItemInMainHand());
                player.getInventory().remove(planter);
            }

        }
    }
}
