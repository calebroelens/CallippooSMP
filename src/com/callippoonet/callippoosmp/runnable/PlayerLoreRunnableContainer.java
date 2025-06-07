package com.callippoonet.callippoosmp.runnable;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class PlayerLoreRunnableContainer {

    public PlayerLoreRunnable playerLoreRunnable;
    public BukkitTask task;

    public PlayerLoreRunnableContainer(PlayerLoreRunnable playerLoreRunnable) {
        this.playerLoreRunnable = playerLoreRunnable;
    }

    public boolean isScheduledOrRunning(){
        if(task != null){
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return scheduler.isQueued(task.getTaskId()) || scheduler.isCurrentlyRunning(task.getTaskId());
        } else {
            return false;
        }
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
}
