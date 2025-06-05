package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;

    public RespawnEventListener(PlayerLoreRegister playerLoreRegister){
        this.playerLoreRegister = playerLoreRegister;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        PlayerLore playerLore = playerLoreRegister.getPlayerLoreByPlayer(player);
        if (playerLore != null) {
            playerLore.applyConfiguration(player);
        }
    }
}
