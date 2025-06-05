package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.inventory.CraftingRecipe;

public class CrafterCraftEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;

    public CrafterCraftEventListener(PlayerLoreRegister playerLoreRegister) {
        this.playerLoreRegister = playerLoreRegister;
    }

    @EventHandler
    public void onCrafterCraftEvent(CrafterCraftEvent event) {
        /* Player lore items are not craft-able using a crafter */
        CraftingRecipe recipe = event.getRecipe();
        if(playerLoreRegister.hasLoreRecipe(recipe)){
            event.setCancelled(true);
        }
    }
}
