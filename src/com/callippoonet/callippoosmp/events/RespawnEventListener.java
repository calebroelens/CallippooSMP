package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;
    Main plugin;

    public RespawnEventListener(Main plugin){
        this.playerLoreRegister = plugin.playerLoreRegister;
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        PlayerLore playerLore = playerLoreRegister.getPlayerLoreByPlayer(player);
        if (playerLore != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                playerLore.applyConfiguration(player, PlayerLoreState.DEFAULT);
            }, 1L);
        }
    }
}
