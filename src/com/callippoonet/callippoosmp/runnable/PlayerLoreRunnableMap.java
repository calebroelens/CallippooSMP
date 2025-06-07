package com.callippoonet.callippoosmp.runnable;


import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerLoreRunnableMap {

    Map<Player, Map<PlayerLoreRunnableId, PlayerLoreRunnableContainer>> activeRunnables;

    public PlayerLoreRunnableMap() {
        activeRunnables = new HashMap<>();
    }

    public PlayerLoreRunnableContainer addOrReplaceRunnable(Player player, PlayerLoreRunnableId id, PlayerLoreRunnable runnable, boolean cancelIfExists) {
        /* Returns true if the runnable did not yet exist in the map */
        Map<PlayerLoreRunnableId, PlayerLoreRunnableContainer> runnableContainers = activeRunnables.get(player);
        /* Use the initial runnable as a blueprint to keep creating runnables */
        if(runnableContainers == null){
            /* No runnables yet */
            runnableContainers = new HashMap<>();
            PlayerLoreRunnableContainer runnableContainer = new PlayerLoreRunnableContainer(runnable.reInitialize());
            runnableContainers.put(id, runnableContainer);
            return runnableContainer;
        } else {
            PlayerLoreRunnableContainer previousRunnable = runnableContainers.get(id);
            if(previousRunnable.isScheduledOrRunning() && !previousRunnable.task.isCancelled() && cancelIfExists){
                previousRunnable.cancel();
            }
            /* Recreate the runnable */
            PlayerLoreRunnableContainer runnableContainer = new PlayerLoreRunnableContainer(previousRunnable.playerLoreRunnable);
            runnableContainers.put(id, runnableContainer);
            return runnableContainer;
        }
    }

    public void cancelPlayerRunnables(Player player){
        this.cancelRunnable(player, null);
    }

    public void cancelPlayerRunnable(Player player, PlayerLoreRunnableId id){
        this.cancelRunnable(player, id);
    }

    private void cancelRunnable(Player player, PlayerLoreRunnableId id){
        Map<PlayerLoreRunnableId, PlayerLoreRunnableContainer> runnables = activeRunnables.get(player);
        if(runnables == null) return;
        for(Map.Entry<PlayerLoreRunnableId, PlayerLoreRunnableContainer> entry : runnables.entrySet()){
            PlayerLoreRunnableContainer runnable = entry.getValue();
            runnable.cancel();
        }
    }
}