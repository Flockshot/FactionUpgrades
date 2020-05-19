package me.flockshot.factionupgrades.utils.filesystem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.flockshot.factionupgrades.utils.ColorTranslator;
import me.flockshot.factionupgrades.utils.files.identifier.FileIdentifier;


public abstract class UnknownFile implements FlockFile
{    
    private File file;
    private FileConfiguration config;    
    
    public UnknownFile(File file)
    {
        setFile(file);
        setConfig(YamlConfiguration.loadConfiguration(file));
        
        setDefaults();
        
        getConfig().options().copyDefaults(true);
        save();
    }
    
    
    

    
    
    public void save()
    {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Object getLine(FileIdentifier identifier) {
        Object value = getConfig().get(identifier.toString());
        if(value instanceof String)
            return ColorTranslator.getString((String) value);
        else
            return value;         
    }
    public void setLine(FileIdentifier identifier, Object value) {
        getConfig().set(identifier.toString(), value);
        save();
    }
    @Override
    public void setLine(String identifier, Object value) {
        getConfig().set(identifier, value);
    }
    
    
    public void addDefault(FileIdentifier path, Object value) {
        if(value instanceof String)
            getConfig().addDefault(path.toString(), (String) value);
        else
            getConfig().addDefault(path.toString(), value);        
    }


    public void setFile(File file) {
        this.file = file;
    }
    public File getFile() {
        return file;
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public void setConfig(FileConfiguration config) {
        this.config = config;
    }
    

}
