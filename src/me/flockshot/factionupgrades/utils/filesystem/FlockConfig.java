package me.flockshot.factionupgrades.utils.filesystem;

import org.bukkit.configuration.file.FileConfiguration;

import me.flockshot.factionupgrades.utils.files.identifier.FileIdentifier;

public interface FlockConfig {
    
    public FileConfiguration getConfig();
    public void setConfig(FileConfiguration config);
    
    public void save();
    
    public Object getLine(FileIdentifier identifier);
    public void setLine(FileIdentifier identifier, Object value);
    public void setLine(String identifier, Object value);
    
    void addDefault(FileIdentifier path, Object value);
    
    void setDefaults();
}
