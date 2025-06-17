package com.callippoonet.callippoosmp.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.HappyGhast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectListener implements Listener {

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event){
        EntityPotionEffectEvent.Action action = event.getAction();
        if(event.getEntity() instanceof HappyGhast ghast){
            AttributeInstance flyingSpeed = ghast.getAttribute(Attribute.FLYING_SPEED);
            if(flyingSpeed == null) return;
            if(action == EntityPotionEffectEvent.Action.ADDED || action == EntityPotionEffectEvent.Action.CHANGED){
                PotionEffect effect = event.getNewEffect();
                if(effect == null) return;
                if(effect.getType() != PotionEffectType.SPEED) return;
                int level = effect.getAmplifier();
                flyingSpeed.setBaseValue(level * 0.05);
            } else if(event.getCause() == EntityPotionEffectEvent.Cause.EXPIRATION || action == EntityPotionEffectEvent.Action.REMOVED) {
                flyingSpeed.setBaseValue(0.05);
            }
        }
    }
}
