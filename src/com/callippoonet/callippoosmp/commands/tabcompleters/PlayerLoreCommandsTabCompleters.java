package com.callippoonet.callippoosmp.commands.tabcompleters;

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
        Bukkit.getLogger().info("onTabComplete: " + Arrays.toString(strings));
        if(strings.length == 1){
            List<String> subCommands = new ArrayList<>();
            subCommands.add("set");
            subCommands.add("remove");
            subCommands.add("recipe");
            subCommands.add("list");
            return subCommands;
        }
        if(strings.length == 2 && Objects.equals(strings[0], "list")){
            return List.of();
        }
        Set<String> loreNames = this.playerLoreRegister.getLoreNames();
        if(strings.length == 2 && Objects.equals(strings[0], "recipe")){
            return new ArrayList<>(loreNames);
        }
        List<String> playerNames = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) {
            playerNames.add(p.getName());
        }
        if(strings.length == 2 && Objects.equals(strings[0], "set")) {
            return playerNames;
        }
        if(strings.length == 3 && Objects.equals(strings[0], "set")) {
            return new ArrayList<>(loreNames);
        }
        return List.of();
    }
}
