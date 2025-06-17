package com.callippoonet.callippoosmp.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.List;

@SuppressWarnings("ALL")
public class ProjectileEventListener implements Listener {

    @EventHandler
    public void onWindchargeFired(ProjectileLaunchEvent event){
        Projectile projectile = event.getEntity();
        if(projectile instanceof WindCharge windCharge && windCharge.getShooter() instanceof Player){
            Player player = (Player) windCharge.getShooter();
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.WIND_CHARGE){
                ItemMeta meta = item.getItemMeta();
                CustomModelDataComponent dataComponent = meta.getCustomModelDataComponent();
                if(dataComponent.getStrings().contains("infinity_charge")){
                    List<Float> level = dataComponent.getFloats();
                    if(level.size() == 1){
                        windCharge.setAcceleration(windCharge.getAcceleration().multiply(level.get(0)));
                    }
                }
            }
        }
    }
}
