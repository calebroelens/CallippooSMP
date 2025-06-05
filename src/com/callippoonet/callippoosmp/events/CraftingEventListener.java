package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.data.PlayerLoreDataManager;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingRecipe;

import java.util.List;

public class CraftingEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;

    public CraftingEventListener(PlayerLoreRegister playerLoreRegister) {
        this.playerLoreRegister = playerLoreRegister;
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        CraftingRecipe recipe = (CraftingRecipe) event.getRecipe();
        if(playerLoreRegister.hasLoreRecipe(recipe)){
            List<HumanEntity> viewers = event.getViewers();
            if(viewers.isEmpty()){
                event.setCancelled(true);
                return;
            }
            Player player = (Player) viewers.get(0);
            PlayerLore lore = playerLoreRegister.getPlayerLoreByPlayer(player);
            if(lore == null){
                event.setCancelled(true);
                return;
            }
            if(lore.hasRecipe(recipe)){
                event.setCancelled(true);
            }
        }
    }
}
