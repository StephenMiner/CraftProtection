package me.stephenminer.craftprotection;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;
import net.countercraft.movecraft.craft.PilotedCraft;
import net.countercraft.movecraft.craft.type.CraftType;
import net.countercraft.movecraft.util.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtectCrafts implements Listener {
    private final CraftProtection plugin;
    public ProtectCrafts(){
        this.plugin = JavaPlugin.getPlugin(CraftProtection.class);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        CraftManager manager = CraftManager.getInstance();
        Set<Craft> crafts = manager.getCraftsInWorld(event.getBlock().getWorld());
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        crafts = filterExceptions(crafts);
        for (Craft craft : crafts) {
            if (craft instanceof PilotedCraft pilotedCraft && player.equals(pilotedCraft.getPilot()))continue;
            if (MathUtils.locationNearHitBox(craft.getHitBox(),block.getLocation(),plugin.protectionRadius)){
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't place blocks near a ship!"));
                return;
            }

        }
    }

    private Set<Craft> filterExceptions(Set<Craft> crafts){
        List<String> exceptions = plugin.exceptions;
        return crafts.stream().filter(craft -> !exceptions.contains(craft.getType().getStringProperty(CraftType.NAME))).collect(Collectors.toSet());
    }
}
