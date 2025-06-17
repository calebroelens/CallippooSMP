package com.callippoonet.callippoosmp.runnable;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class PlayerLoreRunnable extends BukkitRunnable implements IPlayerRunnable {

    Player player;
    Main plugin;
    PlayerLoreState playerLoreState;

    public PlayerLoreRunnable(Main plugin) {
        this.player = null;
        this.playerLoreState = null;
        this.plugin = plugin;

    }

    public void setPlayerLoreState(PlayerLoreState playerLoreState) {
        this.playerLoreState = playerLoreState;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public abstract PlayerLoreRunnable reInitialize();

    @Override
    public void run() {

    }

}
