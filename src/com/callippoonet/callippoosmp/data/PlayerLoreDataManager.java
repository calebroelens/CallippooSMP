package com.callippoonet.callippoosmp.data;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerLoreDataManager {

    private final Main plugin;
    private final PlayerLoreRegister register;
    private FileConfiguration playerLoreData;
    private File playerLoreDataFile;

    public PlayerLoreDataManager(Main plugin, PlayerLoreRegister playerLoreRegister) {
        this.plugin = plugin;
        this.register = playerLoreRegister;
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
}
