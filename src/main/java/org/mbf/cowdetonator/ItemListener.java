package org.mbf.cowdetonator;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemListener implements Listener {

    @EventHandler
    public void onCowRightClick(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        if(entity instanceof Cow && entity.getPersistentDataContainer().has(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN)){
            entity.getWorld().createExplosion(entity.getLocation(), 2);
        }
    }

    @EventHandler
    public void onDetonatorUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta() != null && event.getAction() == Action.RIGHT_CLICK_AIR){
            PersistentDataContainer playerContainer = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if(playerContainer.has(Keys.CUSTOM_DETONATOR, PersistentDataType.BOOLEAN)){
                for (Entity entity : CowCommand.Cows) {
                    if(entity instanceof Cow && entity.getPersistentDataContainer().has(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN)){
                        entity.getWorld().createExplosion(entity.getLocation(), 2);
                    }
                }
                CowCommand.Cows.clear();
                player.getInventory().remove(player.getInventory().getItemInMainHand());
            }

        }
    }
}
