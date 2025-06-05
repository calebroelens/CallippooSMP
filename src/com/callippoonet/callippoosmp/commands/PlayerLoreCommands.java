package com.callippoonet.callippoosmp.commands;

import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerLoreCommands implements CommandExecutor {

    PlayerLoreRegister register;
    JavaPlugin plugin;

    public PlayerLoreCommands(PlayerLoreRegister register, JavaPlugin plugin) {
        this.register = register;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(Objects.equals(strings[0], "list")){
                player.sendMessage(ChatColor.GOLD + "The following lores are available:");
                for(PlayerLore playerLore : this.register.playerLore.values()){
                    player.sendMessage(playerLore.displayName);
                }
            }
            if(Objects.equals(strings[0], "set") && strings.length == 3){
                String playerName = strings[1];
                String lore = strings[2];
                Player targetPlayer = null;
                for(Player p: Bukkit.getOnlinePlayers()){
                    if(p.getName().equals(playerName)){
                        targetPlayer = p;
                    }
                }
                if(targetPlayer == null){
                    player.sendMessage("Player not found.");
                    return false;
                }
                PlayerLore playerLore = this.register.playerLore.get(lore);
                if(playerLore == null){
                    player.sendMessage("Lore not found: " + lore);
                    return false;
                }
                player.sendMessage("Applied lore " + playerLore.displayName + " to player " + targetPlayer.getName());
                playerLore.applyConfiguration(targetPlayer);
                PermissionAttachment attachment = player.addAttachment(this.plugin);
                attachment.setPermission(playerLore.permissionName, true);
                for(Map.Entry<String, PlayerLore> entry : this.register.playerLorePermissionMap.entrySet()){
                    if(!entry.getKey().equals(playerLore.permissionName)){
                        attachment.unsetPermission(entry.getKey());
                    }
                }
            }
        }
        return true;
    }
}
