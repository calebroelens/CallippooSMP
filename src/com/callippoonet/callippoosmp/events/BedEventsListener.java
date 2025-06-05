package com.callippoonet.callippoosmp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedEventsListener implements Listener {
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent e) {
        PlayerBedEnterEvent.BedEnterResult result = e.getBedEnterResult();
        if(result == PlayerBedEnterEvent.BedEnterResult.OK) {
            Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + " is now sleeping.");
        }
    }
}
