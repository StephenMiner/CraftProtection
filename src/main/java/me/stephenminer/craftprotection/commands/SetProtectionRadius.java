package me.stephenminer.craftprotection.commands;

import me.stephenminer.craftprotection.CraftProtection;
import net.countercraft.movecraft.craft.type.CraftType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetProtectionRadius implements CommandExecutor {
    private final CraftProtection plugin;
    public SetProtectionRadius(){
        this.plugin = JavaPlugin.getPlugin(CraftProtection.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (!player.hasPermission("craft-prot.radius")){
                player.sendMessage(ChatColor.RED + "No permission for this");
                return false;
            }
        }
        if (args.length < 1){
            sender.sendMessage(ChatColor.RED + "You need to type out a protection radius");
            return false;
        }
        double radius;
        try {
            radius = Double.parseDouble(args[0]);
        }catch (Exception e){
            sender.sendMessage(ChatColor.RED + args[0] + " is not a number!");
            return false;
        }
        plugin.protectionRadius = radius;
        sender.sendMessage(ChatColor.GREEN + "Set radius to " + radius);
        return true;
    }
}
