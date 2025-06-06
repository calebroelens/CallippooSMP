package com.callippoonet.callippoosmp.lore;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.data.PlayerLoreDataManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerLoreRegister {

    private final Map<String, PlayerLore> playerLore;
    private final PlayerLoreItemMap playerLoreItemMap;
    private PlayerLoreDataManager playerLoreDataManager;
    Main plugin;

    public PlayerLoreRegister(Main plugin) {
        this.playerLore = new HashMap<>();
        this.playerLoreItemMap = new PlayerLoreItemMap();
        this.plugin = plugin;
        this.createPlayerLoreDataManager();
    }

    public void createPlayerLoreDataManager() {
        this.playerLoreDataManager = new PlayerLoreDataManager(plugin, this);
    }

    public void registerLore(PlayerLore playerLore){
        this.playerLore.put(playerLore.internalName, playerLore);
        this.playerLoreItemMap.addPlayerLore(playerLore);
    }

    public List<String> getLoreNames(){
        return new ArrayList<>(this.playerLore.keySet());
    }

    public boolean hasLoreRecipe(CraftingRecipe recipe){
        NamespacedKey key = recipe.getKey();
        return playerLoreItemMap.hasRecipe(key);
    }

    @Nullable
    public PlayerLore getPlayerLoreByPlayer(Player player){
        return this.playerLoreDataManager.getActivePlayerLore(player);
    }

    @Nullable
    public PlayerLore getPlayerLoreByName(String name){
        return this.playerLore.get(name);
    }

    public List<PlayerLore> getPlayerLores(){
        return new ArrayList<>(this.playerLore.values());
    }

    public void changePlayerSelectedLore(Player player, PlayerLore playerLore){
        String playerLoreName = null;
        if(playerLore != null){
            playerLore.applyConfiguration(player);
            playerLoreName = playerLore.internalName;
        } else {
            PlayerLore activeLore = this.playerLoreDataManager.getActivePlayerLore(player);
            if(activeLore != null){
                activeLore.resetConfiguration(player);
            }
        }
        this.playerLoreDataManager.updatePlayerLore(player, playerLoreName);
    }
}
