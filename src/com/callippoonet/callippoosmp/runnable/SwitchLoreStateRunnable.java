package com.callippoonet.callippoosmp.runnable;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import org.bukkit.Bukkit;


public class SwitchLoreStateRunnable extends PlayerLoreRunnable {

    public SwitchLoreStateRunnable(Main plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        super.run();
        if(player == null)return;
        if(!player.isOnline()) return;
        PlayerLore playerLore = plugin.playerLoreRegister.getPlayerLoreByPlayer(player);
        if(playerLore == null) return;
        Bukkit.getScheduler().callSyncMethod(plugin, () -> {
            this.plugin.playerLoreRegister.changePlayerLoreState(player, playerLoreState);
            return null;
        });
    }

    @Override
    public PlayerLoreRunnable reInitialize() {
        SwitchLoreStateRunnable runnable = new SwitchLoreStateRunnable(plugin);
        runnable.player = this.player;
        runnable.playerLoreState = this.playerLoreState;
        runnable.plugin = this.plugin;
        return runnable;
    }
}
