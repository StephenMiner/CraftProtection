package me.stephenminer.craftprotection;

import me.stephenminer.craftprotection.commands.CraftProtectionReload;
import me.stephenminer.craftprotection.commands.SetProtectionRadius;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class CraftProtection extends JavaPlugin{
    public ConfigFile settings;

    public double protectionRadius;
    public List<String> exceptions;
    @Override
    public void onEnable() {
        this.settings = new ConfigFile(this, "settings");
        protectionRadius = loadProtectionRadius();
        exceptions = new ArrayList<>();
        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
       saveProtectionRadius();
    }

    private void registerEvents(){
        PluginManager pm = this.getServer().getPluginManager();;
        pm.registerEvents(new ProtectCrafts(),this);
    }
    private void registerCommands(){
        getCommand("craftProtectionRadius").setExecutor(new SetProtectionRadius());
        getCommand("craftProtectionReload").setExecutor(new CraftProtectionReload());
    }

    public double loadProtectionRadius(){
        return this.settings.getConfig().getDouble("protection-radius");
    }
    public void saveProtectionRadius(){
        this.settings.getConfig().set("protection-radius",protectionRadius);
        this.settings.saveConfig();
    }

    public List<String> loadExceptions(){
        if (this.settings.getConfig().contains("exceptions")) return this.settings.getConfig().getStringList("exceptions");
        else return new ArrayList<>();
    }
}
