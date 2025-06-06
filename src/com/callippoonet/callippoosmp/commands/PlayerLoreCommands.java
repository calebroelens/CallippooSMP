package com.callippoonet.callippoosmp.commands;
import com.callippoonet.callippoosmp.Permissions;
import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreRegister;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
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
        if(strings.length < 1) return false;
        if(!isValidSubCommand(strings[0])) {
            commandSender.sendMessage("Unknown command: " + strings[0]);
            return true;
        }
        switch (strings[0]){
            case "set":
                return handleSubCommandUpdate(commandSender, command, s, strings, "set");
            case "remove":
                return handleSubCommandUpdate(commandSender, command, s, strings, "remove");
            case "list":
                return handleSubCommandList(commandSender, command, s, strings);
            case "recipe":
                return handleSubCommandRecipe(commandSender, command, s, strings);
            case "current":
                return handleSubCommandCurrent(commandSender, command, s, strings);
            case "choose":
                return handleSubCommandChoose(commandSender, command, s, strings);
        }
        return false;
    }

    public Player getPlayerFromString(String playerString){
        for(Player p: Bukkit.getOnlinePlayers()){
            if(p.getName().equals(playerString)){
                return p;
            }
        }
        return null;
    }

    public boolean isValidSubCommand(String testCommandString){
        return List.of("set", "remove", "list", "recipe", "current", "choose").contains(testCommandString);
    }

    public boolean handleSubCommandUpdate(CommandSender commandSender, Command command, String s, String[] strings, String mode){
        /* Set the lore for any player */
        boolean hasManagementPermission = Permissions.hasLoreManagement(commandSender);
        if (Objects.equals(mode, "set")) {
            if (!hasManagementPermission) {
                commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if (strings.length != 3) {
                commandSender.sendMessage(ChatColor.RED + "Command incorrect, usage: /lore set <player> <lore>");
                return true;
            }
            Player player = this.getPlayerFromString(strings[1]);
            if (player == null) {
                commandSender.sendMessage("Could not find that player.");
                return true;
            }
            return updateLoreForPlayer(commandSender, player, strings[2]);
        }

        if (Objects.equals(mode, "remove")) {
            if(strings.length == 1 && !hasManagementPermission){
                return updateLoreForPlayer(commandSender, (Player) commandSender, null);
            }
            if (!hasManagementPermission) {
                commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if (strings.length != 2) {
                commandSender.sendMessage(ChatColor.RED + "Command incorrect, usage: /lore remove <player>");
                return true;
            }
            Player player = this.getPlayerFromString(strings[1]);
            if (player == null) {
                commandSender.sendMessage("Could not find that player.");
                return true;
            }
            return updateLoreForPlayer(commandSender, player, null);
        }
        return true;

    }

    public boolean handleSubCommandList(CommandSender commandSender, Command command, String s, String[] strings){
        /* List the available lores */
        commandSender.sendMessage(ChatColor.GOLD + "The following lores are available:");
        for(PlayerLore playerLore : this.register.getPlayerLores()){
            commandSender.sendMessage(ChatColor.AQUA + playerLore.displayName);
        }
        return true;
    }

    public boolean handleSubCommandRecipe(CommandSender commandSender, Command command, String s, String[] strings){
        /* List the recipes of the current player in a GUI. */
        commandSender.sendMessage(ChatColor.RED + "Sorry, this command is not yet implemented. Coming soon :)");
        return true;
    }

    public boolean handleSubCommandCurrent(CommandSender commandSender, Command command, String s, String[] strings){
        /* List the lore of a selected player or the current player */
        if(Permissions.hasLoreManagement(commandSender) && strings.length == 2){
            Player player = getPlayerFromString(strings[1]);
            if(player == null){
                commandSender.sendMessage(ChatColor.RED + "Could not find player");
                return true;
            } else {
                PlayerLore playerLore = this.register.getPlayerLoreByPlayer(player);
                if(playerLore == null){
                    commandSender.sendMessage("Player currently has no lore.");
                } else {
                    commandSender.sendMessage("Player " + player.getName() + " currently has the lore " + playerLore.displayName);
                }
            }
        } else {
            if(commandSender instanceof Player player){
                PlayerLore playerLore = this.register.getPlayerLoreByPlayer(player);
                if(playerLore == null){
                    commandSender.sendMessage("Player currently has no lore.");
                } else {
                    commandSender.sendMessage("Player " + player.getName() + " currently has the lore " + playerLore.displayName);
                }
            } else {
                commandSender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            }
        }
        return true;
    }

    public boolean handleSubCommandChoose(CommandSender commandSender, Command command, String s, String[] strings){
        /* Choose your lore : Is a personal command */
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }
        if(strings.length < 2){
            commandSender.sendMessage(ChatColor.RED + "Command incorrect, usage: /lore choose <lore>");
            return true;
        }
        return updateLoreForPlayer(commandSender, player, strings[1]);
    }

    public boolean updateLoreForPlayer(CommandSender commandSender, Player player, String lore){
        if(lore != null){
            PlayerLore playerLore = this.register.getPlayerLoreByName(lore);
            if(playerLore == null){
                commandSender.sendMessage("Did not find that lore.");
                return true;
            }
            commandSender.sendMessage("Applied lore " + playerLore.displayName + " to player " + player.getName());
            /* Register the player lore change */
            this.register.changePlayerSelectedLore(player, playerLore);
            return true;
        } else {
            /* Remove lore */
            commandSender.sendMessage("Cleared lore from player.");
            this.register.changePlayerSelectedLore(player, null);
            return true;
        }
    }

}
