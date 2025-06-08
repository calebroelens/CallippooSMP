package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnable;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableContainer;
import com.callippoonet.callippoosmp.runnable.PlayerLoreRunnableId;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

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
        List<Player> affectedPlayers = new ArrayList<Player>();
        for(Player p: Bukkit.getOnlinePlayers()){
            PlayerLore playerLore = this.playerLoreRegister.getPlayerLoreByPlayer(player);
            if(playerLore == null) continue;
            if(playerLore.internalName.equals("faewynn")){
                affectedPlayers.add(p);
            }
        }
        for(Player p: affectedPlayers){
            PlayerLoreRunnableContainer runnableContainer = this.playerLoreRegister.startPlayerLoreRunnable(player, PlayerLoreRunnableId.SEVEN, false);
            if(runnableContainer == null || runnableContainer.playerLoreRunnable == null){
                Bukkit.getLogger().info("Player runnable could not be created");
                continue;
            };
            runnableContainer.playerLoreRunnable.setPlayer(p);
            runnableContainer.playerLoreRunnable.setPlayerLoreState(PlayerLoreState.DEFAULT);
            Bukkit.getScheduler().runTask(plugin, () -> {
                this.playerLoreRegister.changePlayerLoreState(player, PlayerLoreState.SECONDARY);
            });
            /* Assign the task to track the progress */
            runnableContainer.task = runnableContainer.playerLoreRunnable.runTaskLater(plugin, 20L * 7L * 60L);
        }
    }
}
