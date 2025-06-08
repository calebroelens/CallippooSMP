package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.data.PlayerLoreDataManager;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemCraftResult;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class CraftingEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;

    public CraftingEventListener(PlayerLoreRegister playerLoreRegister) {
        this.playerLoreRegister = playerLoreRegister;
    }

    @EventHandler
    public void onPlayerCraftLore(CraftItemEvent event) {
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
            if(!lore.hasRecipe(recipe)){
                event.setCancelled(true);
            }
        }
    }
}
