package com.callippoonet.callippoosmp.events;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;


@SuppressWarnings("UnstableApiUsage")
public class SpectralPickaxeListener implements Listener {

    @EventHandler
    public void onSpectralPickaxeDamage(PlayerItemDamageEvent event) {
        Player eventPlayer = event.getPlayer();
        ItemStack eventItem = eventPlayer.getInventory().getItemInMainHand();
        if(itemStackIsSpectralPickaxe(eventItem)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpectralPickaxeBlockBreak(BlockBreakEvent event) {
        Player eventPlayer = event.getPlayer();
        ItemStack eventItem = eventPlayer.getInventory().getItemInMainHand();
        if(itemStackIsSpectralPickaxe(eventItem)) {
            ItemMeta newMeta = this.updateSpectralPickaxeCharges(eventItem);
            if(newMeta != null){
                eventItem.setItemMeta(newMeta);
                eventPlayer.getInventory().setItemInMainHand(eventItem);
            }
        }
    }

    @EventHandler
    public void onSpectralPickaxeAbility(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            ItemStack eventItem = event.getPlayer().getInventory().getItemInMainHand();
            if(itemStackIsSpectralPickaxe(eventItem)){
                int currentDamage = currentSpectralPickaxeDamage(eventItem);
                if(currentDamage == 0){
                    ItemMeta newMeta = setSpectralPickaxeDamage(eventItem, 32);
                    eventItem.setItemMeta(newMeta);
                    player.getInventory().setItemInMainHand(eventItem);
                    runSpectralPickaxeAbility(player);
                }
            }
        }
    }

    public void runSpectralPickaxeAbility(Player player){
        List<Entity> entities = player.getNearbyEntities(32, 32, 32);
        for(Entity entity : entities){
            if(entity instanceof Mob){
                ((Mob) entity).addPotionEffect(
                        new PotionEffect(
                                PotionEffectType.GLOWING,
                                200,
                                1
                        )
                );
            }
        }
    }

    public boolean itemStackIsSpectralPickaxe(ItemStack itemStack) {
        if(itemStack == null) return false;
        if(itemStack.getType() != Material.IRON_PICKAXE) return false;
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return false;
        return meta.getCustomModelDataComponent().getStrings().contains("spectral_pickaxe");
    }

    public ItemMeta updateSpectralPickaxeCharges(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return null;
        int currentDamage = ((Damageable) meta).getDamage();
        return setSpectralPickaxeDamage(itemStack, currentDamage - 1);
    }

    public ItemMeta setSpectralPickaxeDamage(ItemStack itemStack, int damage){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return null;
        ((Damageable) meta).setDamage(damage);
        return meta;
    }

    public int currentSpectralPickaxeDamage(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        return ((Damageable) Objects.requireNonNull(meta)).getDamage();
    }

}
