package me.stephenminer.craftprotection.commands;

import me.stephenminer.craftprotection.CraftProtection;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftProtectionReload implements CommandExecutor {
    private final CraftProtection plugin;

    public CraftProtectionReload(){
        this.plugin = JavaPlugin.getPlugin(CraftProtection.class);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (!player.hasPermission("craft-prot.reload")){
                player.sendMessage(ChatColor.RED + "No permission to use this command");
                return false;
            }
        }
        plugin.settings.reloadConfig();
        plugin.protectionRadius = plugin.loadProtectionRadius();
        plugin.exceptions = plugin.loadExceptions();
        sender.sendMessage(ChatColor.GREEN + "Reloaded config");
        return true;
    }
}
