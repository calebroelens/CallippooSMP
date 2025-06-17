package com.callippoonet.callippoosmp.commands.tabcompleters;

import com.callippoonet.callippoosmp.Permissions;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerLoreCommandsTabCompleters implements TabCompleter {

    PlayerLoreRegister playerLoreRegister;

    public PlayerLoreCommandsTabCompleters(PlayerLoreRegister playerLoreRegister) {
        this.playerLoreRegister = playerLoreRegister;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) return List.of();
        if(strings.length == 1){
            return handleSubCommands(commandSender, command, s, strings);
        } else {
            /* Management */
            if(Permissions.hasLoreManagement(commandSender)){
                if (strings[0].equals("set")) {
                    return handleSubCommandSet(commandSender, command, s, strings);
                }
            }
            /* All */
            switch (strings[0]){
                case "remove":
                    return handleSubCommandRemove(commandSender, command, s, strings);
                case "list":
                    return handleSubCommandList(commandSender, command, s, strings);
                case "recipe":
                    return handleSubCommandRecipe(commandSender, command, s, strings);
                case "current":
                    return handleSubCommandCurrent(commandSender, command, s, strings);
                case "choose":
                    return handleSubCommandChoose(commandSender, command, s, strings);
            }
        }
        return List.of();
    }

    public List<String> getOnlinePlayerNames(){
        List<String> playerNames = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) {
            playerNames.add(p.getName());
        }
        return playerNames;
    }

    public List<String> getPlayerLoreInternalNames(){
        return this.playerLoreRegister.getLoreNames();
    }

    public List<String> handleSubCommands(CommandSender commandSender, Command command, String s, String[] strings) {
        if(Permissions.hasLoreManagement(commandSender)){
            return List.of("set", "remove", "recipe", "list", "current", "choose");
        }
        else {
            return List.of("remove", "recipe", "list", "current", "choose");
        }
    }

    public List<String> handleSubCommandSet(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 2) return getOnlinePlayerNames();
        if(strings.length == 3) return getPlayerLoreInternalNames();
        return List.of();
    }

    public List<String> handleSubCommandRemove(CommandSender commandSender, Command command, String s, String[] strings) {
        if(Permissions.hasLoreManagement(commandSender) && strings.length > 1){
            return getOnlinePlayerNames();
        }
        return List.of();
    }

    public List<String> handleSubCommandList(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }

    public List<String> handleSubCommandRecipe(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }

    public List<String> handleSubCommandCurrent(CommandSender commandSender, Command command, String s, String[] strings) {
        if(Permissions.hasLoreManagement(commandSender)){
            if(strings.length == 2) return getOnlinePlayerNames();
        }
        return List.of();
    }

    private List<String> handleSubCommandChoose(CommandSender commandSender, Command command, String s, String[] strings) {
        return getPlayerLoreInternalNames();
    }
}
