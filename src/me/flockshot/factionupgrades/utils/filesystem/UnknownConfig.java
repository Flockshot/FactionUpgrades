package me.flockshot.factionupgrades.utils.filesystem;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.flockshot.factionupgrades.utils.ColorTranslator;
import me.flockshot.factionupgrades.utils.files.identifier.FileIdentifier;


public abstract class UnknownConfig implements FlockConfig
{    
    private FileConfiguration config;    
    
    public UnknownConfig(FileConfiguration config)
    {
        setConfig(config);
        
        setDefaults();
        getConfig().options().copyDefaults(true);
        save();
    }
    
    
    @Override
    public Object getLine(FileIdentifier identifier) {
        Object value = getConfig().get(identifier.toString());
        if(value instanceof String)
            return ColorTranslator.getString((String) value);
        else
            return value;         
    }
    @Override
    public void setLine(FileIdentifier identifier, Object value) {
        getConfig().set(identifier.toString(), value);
        save();
    }
    @Override
    public void setLine(String identifier, Object value) {
        getConfig().set(identifier, value);
    }
    
    @Override
    public void addDefault(FileIdentifier path, Object value) {
        if(value instanceof List)
            getConfig().addDefault(path.toString(), (List<?>) value);
        else if(value instanceof String)
            getConfig().addDefault(path.toString(), (String) value);
        else
            getConfig().addDefault(path.toString(), value);        
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }
    @Override
    public void setConfig(FileConfiguration config) {
        this.config = config;
    }
    

}
