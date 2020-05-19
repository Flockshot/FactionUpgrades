package me.flockshot.factionupgrades.utils.filesystem;

import org.bukkit.plugin.java.JavaPlugin;


public abstract class UnknownPluginConfig extends UnknownConfig
{    
    private JavaPlugin plugin;
    
    public UnknownPluginConfig(JavaPlugin plugin)
    {
        super(plugin.getConfig());
        setPlugin(plugin);
    }
    
    @Override
    public void save() {
        getPlugin().saveConfig();
    }

    public void reloadConfig() {
        getPlugin().reloadConfig();
    }
    
    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    

}
