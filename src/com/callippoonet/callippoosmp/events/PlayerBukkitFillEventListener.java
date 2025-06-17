package com.callippoonet.callippoosmp.events;

import com.callippoonet.callippoosmp.Main;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;


public class PlayerBukkitFillEventListener implements Listener {

    Main plugin;

    public PlayerBukkitFillEventListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBukkitFillEvent(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        ItemStack filledBucket = event.getItemStack();
        if(filledBucket != null && (filledBucket.getType() == Material.WATER_BUCKET)) {
            PlayerLore lore = plugin.playerLoreRegister.getPlayerLoreByPlayer(player);
            if(lore == null) return;
            if(!Objects.equals(lore.internalName, "glibbo")) return;
            ItemMeta meta = filledBucket.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "McSplashing Pee water");
                filledBucket.setItemMeta(meta);
            }
        }
    }

}
