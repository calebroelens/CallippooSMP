package com.callippoonet.callippoosmp.lore;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerLoreItemMap {

    Map<NamespacedKey, List<PlayerLore>> entries = new HashMap<NamespacedKey, List<PlayerLore>>();

    public void addPlayerLore(PlayerLore playerLore){
        List<CraftingRecipe> recipes = playerLore.recipes;
        for(CraftingRecipe recipe : recipes){
            NamespacedKey key = recipe.getKey();
            entries.computeIfAbsent(key, k -> new ArrayList<>()).add(playerLore);
        }
    }

    public List<NamespacedKey> getKeys(){
        return new ArrayList<>(this.entries.keySet());
    }

    public List<NamespacedKey> getRecipesForLore(PlayerLore lore){
        List<NamespacedKey> keys = new ArrayList<>();
        for(Map.Entry<NamespacedKey, List<PlayerLore>> entry : entries.entrySet()){
            if(entry.getValue().contains(lore)){
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public List<PlayerLore> getAllowedLoreForRecipe(NamespacedKey key){
        return entries.get(key);
    }

    public boolean hasRecipe(NamespacedKey key){
        return entries.containsKey(key);
    }

    public boolean loreHasRecipe(PlayerLore lore){
        for(Map.Entry<NamespacedKey, List<PlayerLore>> entry : entries.entrySet()){
            if(entry.getValue().contains(lore)){
                return true;
            }
        }
        return false;
    }
}
