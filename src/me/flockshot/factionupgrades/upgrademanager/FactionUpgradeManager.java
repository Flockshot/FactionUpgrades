package me.flockshot.factionupgrades.upgrademanager;

import java.util.HashMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.utils.NumberUtility;

public class FactionUpgradeManager 
{
    private FactionUpgradesPlugin plugin;
    private HashMap<String, FactionUpgrade> upgrades = new HashMap<String, FactionUpgrade>();
    private final String upgradePath = "Upgrades";
    
    public FactionUpgradeManager(FactionUpgradesPlugin plugin)
    {
        this.plugin = plugin;
        initUpgrades(plugin.getConfig(), upgradePath);
    }
    
    public void initUpgrades(FileConfiguration file, String path)
    {

        if(file.contains(path))
        {
            for(final String upgradeName : file.getConfigurationSection(path).getKeys(false))
            {
                String fullPath = path+"."+upgradeName;
                
                FactionUpgrade upgrade = plugin.getUpgradesRegistry().getFromIdentifier(upgradeName, getLevelsInfo(file, fullPath+".levels"));
                upgrade.setEnabled(file.getBoolean(fullPath+".enabled", true));
                upgrade.setUpgradeMessage(file.getString(fullPath+".message", ""));
                
                if(upgrade.isEnabled())
                    Bukkit.getPluginManager().registerEvents(upgrade, plugin);
                
                upgrades.put(upgradeName, upgrade);
            }
        }
    }

    
    public TreeMap<Integer, LevelInfo> getLevelsInfo(FileConfiguration file, String path)
    {
        TreeMap<Integer, LevelInfo> levelsInfo = new TreeMap<Integer, LevelInfo>();
        if(file.contains(path))
        {
            for(final String level: file.getConfigurationSection(path).getKeys(false))
            {                
                if(NumberUtility.isNum(level))
                {
                    String fullPath = path+"."+level;
                    System.out.println(fullPath);
                    int levelInt = Integer.parseInt(level);
                    levelsInfo.put(levelInt, new LevelInfo(levelInt, file.getDouble(fullPath+".cost", 0D), file.getDouble(fullPath+".value", 0D)));
                }
            }            
        }        
        return levelsInfo;
    }
    
    public HashMap<String, FactionUpgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(HashMap<String, FactionUpgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public FactionUpgrade getUpgrade(String id) {
        return getUpgrades().get(id);
    }



}
