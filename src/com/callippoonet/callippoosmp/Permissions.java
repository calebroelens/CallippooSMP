package com.callippoonet.callippoosmp;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Permissions {

    public static boolean hasLoreManagement(Player player){
        return player.isPermissionSet("callippoo.lore.manage") && player.hasPermission("callippoo.lore.manage");
    }

    public static boolean hasLoreManagement(CommandSender commandSender){
        if(commandSender instanceof Player){
            return Permissions.hasLoreManagement((Player) commandSender);
        }
        return true;
    }

}
