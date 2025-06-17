package com.callippoonet.callippoosmp.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HappyGhast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("UnstableApiUsage")
public class HappyGhastListener implements Listener {

    @EventHandler
    public void onHappyGhastNameChange(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        if(target instanceof HappyGhast && player.getInventory().getItemInMainHand().getType() == Material.GHAST_TEAR) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            if(meta == null) return;
            if(meta.getCustomModelDataComponent().getStrings().contains("happy_ghast_food")){
                item.setAmount(item.getAmount() - 1);
                ((HappyGhast) target).addPotionEffect(
                        new PotionEffect(PotionEffectType.SPEED, 20*2*60, 3)
                );
            }
        }
    }
}
