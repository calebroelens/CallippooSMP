package com.callippoonet.callippoosmp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

@SuppressWarnings("ALL")
public class ExplosionEventListener implements Listener {

    @EventHandler
    public void onWindchargeExplosion(ExplosionPrimeEvent event){
        Bukkit.getLogger().info(event.getEntity().getAsString());
    }
}
