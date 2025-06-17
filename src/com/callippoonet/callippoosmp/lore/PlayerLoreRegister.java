package com.callippoonet.callippoosmp.lore;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.data.PlayerLoreData;
import com.callippoonet.callippoosmp.data.PlayerLoreDataManager;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnable;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableContainer;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableId;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingRecipe;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerLoreRegister {

    private final Map<String, PlayerLore> playerLore;
    private final PlayerLoreItemMap playerLoreItemMap;
    private PlayerLoreDataManager playerLoreDataManager;
    private final PlayerLoreRunnableMap playerLoreRunnableMap;
    private final Map<Player, PlayerLore> playerLoreMap;
    Main plugin;

    public PlayerLoreRegister(Main plugin) {
        this.playerLore = new HashMap<>();
        this.playerLoreItemMap = new PlayerLoreItemMap();
        this.playerLoreRunnableMap = new PlayerLoreRunnableMap();
        this.playerLoreMap = new HashMap<>();
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
        if(playerLoreMap.containsKey(player)){
            return playerLoreMap.get(player);
        }
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
            PlayerLore activeLore = getPlayerLoreByPlayer(player);
            if(activeLore != null){
                activeLore.resetAllConfigurations(player);
            }
            playerLore.applyConfiguration(player, PlayerLoreState.DEFAULT);
            playerLoreName = playerLore.internalName;
        } else {
            PlayerLore activeLore = this.getPlayerLoreByPlayer(player);
            if(activeLore != null){
                activeLore.resetAllConfigurations(player);
            }
        }
        this.playerLoreDataManager.updatePlayerLore(player, playerLoreName);
        this.playerLoreMap.put(player, playerLore);
    }

    public void changePlayerLoreState(Player player, PlayerLoreState newPlayerLoreState){
        PlayerLoreData activeLoreData = this.playerLoreDataManager.getPlayerLoreData(player);
        if(activeLoreData == null) return;
        activeLoreData.playerLore.resetConfiguration(player, activeLoreData.playerLoreState);
        activeLoreData.playerLore.applyConfiguration(player, newPlayerLoreState);
        this.playerLoreDataManager.setPlayerLoreState(player, newPlayerLoreState);
    }

    public PlayerLoreRunnableContainer startPlayerLoreRunnable(Player player, PlayerLoreRunnableId id, boolean cancelIfExists){
        PlayerLoreData playerLoreData = this.playerLoreDataManager.getPlayerLoreData(player);
        if(playerLoreData == null) {
            return null;
        };
        PlayerLore playerLore = playerLoreData.playerLore;
        if(!playerLore.playerLoreRunnables.isEmpty()){
            PlayerLoreRunnable runnable = playerLore.playerLoreRunnables.get(id);
            return this.playerLoreRunnableMap.addOrReplaceRunnable(player, id, runnable, cancelIfExists);
        }
        return null;
    }
}
