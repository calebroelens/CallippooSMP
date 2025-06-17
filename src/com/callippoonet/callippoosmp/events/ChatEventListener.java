package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableContainer;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableId;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.ArrayList;
import java.util.List;


public class ChatEventListener implements Listener {

    PlayerLoreRegister playerLoreRegister;
    Main plugin;

    public ChatEventListener(Main main) {
        this.plugin = main;
        this.playerLoreRegister = main.playerLoreRegister;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().contains("7")){
            onChatContainsSeven(event);
        }
    }

    public void onChatContainsSeven(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        List<Player> affectedPlayers = new ArrayList<>();
        for(Player p: Bukkit.getOnlinePlayers()){
            PlayerLore playerLore = this.playerLoreRegister.getPlayerLoreByPlayer(p);
            if(playerLore == null) continue;
            if(playerLore.internalName.equals("faewynn")){
                affectedPlayers.add(p);
            }
        }
        for(Player p: affectedPlayers){
            PlayerLoreRunnableContainer runnableContainer = this.playerLoreRegister.startPlayerLoreRunnable(p, PlayerLoreRunnableId.SEVEN, false);
            if(runnableContainer == null || runnableContainer.playerLoreRunnable == null){
                continue;
            };
            runnableContainer.playerLoreRunnable.setPlayer(p);
            runnableContainer.playerLoreRunnable.setPlayerLoreState(PlayerLoreState.DEFAULT);
            Bukkit.getScheduler().runTask(plugin, () -> {
                this.playerLoreRegister.changePlayerLoreState(p, PlayerLoreState.SECONDARY);
            });
            /* Assign the task to track the progress */
            runnableContainer.task = runnableContainer.playerLoreRunnable.runTaskLater(plugin, 20L * 7L * 60L);
        }
    }
}
