package com.callippoonet.callippoosmp.lore;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PlayerLoreRegister {

    Map<String, PlayerLore> playerLore;
    Map<String, PlayerLore> playerLorePermissionMap;
    JavaPlugin plugin;

    public PlayerLoreRegister(JavaPlugin plugin) {
        this.playerLore = new HashMap<String, PlayerLore>();
        this.playerLorePermissionMap = new HashMap<String, PlayerLore>();
        this.plugin = plugin;
    }

    public void registerLore(PlayerLore playerLore){
        this.playerLore.put(playerLore.internalName, playerLore);
        this.playerLorePermissionMap.put(playerLore.permissionName, playerLore);
    }

    public PlayerLore getMatchingLoreByPlayerPermission(Player player){
        for(Map.Entry<String, PlayerLore> entry : this.playerLorePermissionMap.entrySet()){
            Bukkit.getLogger().info("Checking for player lore " + entry.getKey());
            if(player.isPermissionSet(entry.getKey()) && player.hasPermission(entry.getKey())){
                Bukkit.getLogger().info("Checking for player lore success for " + entry.getKey());
                return entry.getValue();
            } else {
                Bukkit.getLogger().info("Player does not have lore " + entry.getKey());
            }
        }
        return null;
    }

    public List<NamespacedKey> getCraftingRecipesNamedSpaceKeys(){
        List<NamespacedKey> namespacedKeys = new ArrayList<>();
        for(Map.Entry<String, PlayerLore> entry : this.playerLore.entrySet()){
            List<NamespacedKey> loreKeys = entry.getValue().recipeNameSpaceKeys();
            if(!loreKeys.isEmpty()){
                Bukkit.getLogger().info("Found " + loreKeys.size() + " crafting recipes for " + entry.getKey());
                namespacedKeys.addAll(loreKeys);
            }
        }
        return namespacedKeys;
    }

    public boolean playerHasCraftingPermission(PlayerLore playerLore, CraftingRecipe recipe){
        if(this.getCraftingRecipesNamedSpaceKeys().contains(recipe.getKey())){
            Bukkit.getLogger().info("Recipe found in lore register: " + recipe.getKey());
            return playerLore.hasRecipe(recipe);
        }
        return true;
    }

    public Set<String> getLoreNames(){
        return this.playerLore.keySet();
    }
}
