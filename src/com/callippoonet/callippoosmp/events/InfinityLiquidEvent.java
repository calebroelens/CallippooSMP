package com.callippoonet.callippoosmp.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InfinityLiquidEvent implements Listener {

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onInfinityLiquid(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if(clickedBlock == null) return;
            if(clickedBlock.getType() == Material.AIR) return;

            Player player = event.getPlayer();
            ItemStack filledBucket = player.getInventory().getItemInMainHand();
            ItemMeta meta = filledBucket.getItemMeta();
            if(meta != null) {
                List<String> compStrings = meta.getCustomModelDataComponent().getStrings();
                if(compStrings.size() == 1 && compStrings.get(0).equalsIgnoreCase("infinity_liquid")) {
                    BlockFace blockFace = event.getBlockFace();
                    Block relative = clickedBlock.getRelative(blockFace);
                    relative.setType(Material.WATER);
                }
            }
        }
    }

}
