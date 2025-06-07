package com.callippoonet.callippoosmp.data;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerLoreDataManager {

    private final Main plugin;
    private final PlayerLoreRegister register;
    private FileConfiguration playerLoreData;
    private File playerLoreDataFile;

    private final Map<Player, PlayerLoreState> playerLoreState;

    public PlayerLoreDataManager(Main plugin, PlayerLoreRegister playerLoreRegister) {
        this.plugin = plugin;
        this.register = playerLoreRegister;
        this.playerLoreState = new HashMap<>();
        this.setupDataFile();
    }

    private void setupDataFile(){
        String fileName = "player_lore.yml";
        playerLoreDataFile = new File(plugin.getDataFolder(), fileName);
        if(!playerLoreDataFile.exists()){
            //noinspection ResultOfMethodCallIgnored
            playerLoreDataFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }
        playerLoreData = YamlConfiguration.loadConfiguration(playerLoreDataFile);
    }

    public void updatePlayerLore(Player player, String playerLoreName){
        /* Updating the player lore resets the player lore state */
        this.setPlayerLoreState(player, null);
        UUID playerUUID = player.getUniqueId();
        playerLoreData.set(playerUUID.toString(), playerLoreName);
        try {
            playerLoreData.save(playerLoreDataFile);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @Nullable
    public PlayerLore getActivePlayerLore(Player player){
        String playerLoreName = playerLoreData.getString(player.getUniqueId().toString());
        if(playerLoreName == null){
            return null;
        } else {
            return this.register.getPlayerLoreByName(playerLoreName);
        }
    }

    @Nullable
    public PlayerLoreData getPlayerLoreData(Player player){
        PlayerLore playerLore = getActivePlayerLore(player);
        if(playerLore == null) return null;
        return new PlayerLoreData(
                playerLore,
                this.playerLoreState.getOrDefault(player, PlayerLoreState.DEFAULT)
        );
    }

    public void setPlayerLoreState(Player player, PlayerLoreState state){
        this.playerLoreState.put(player, Objects.requireNonNullElse(state, PlayerLoreState.DEFAULT));
    }
}
