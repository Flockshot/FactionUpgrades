package me.flockshot.factionupgrades.upgrademanager;

import java.util.TreeMap;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.utils.ColorTranslator;

public abstract class UnknownFactionUpgrade implements FactionUpgrade
{
    private FactionUpgradesPlugin plugin;
    private String upgradeMessage;
    private boolean enabled;

    private TreeMap<Integer, LevelInfo> upgrades = new TreeMap<Integer, LevelInfo>();
    
    public UnknownFactionUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        setPlugin(plugin);
        setUpgrades(upgrades);        
    }

    
    @Override
    public void runUpgradeMessage(FactionStorage factionStorage, Faction faction) {
        for(FPlayer factionPlayer : faction.getFPlayersWhereOnline(true))
            if(factionPlayer.getPlayer()!=null)
                factionPlayer.getPlayer().sendMessage(getUpgradeMessage().replaceAll("%upgrade%", factionStorage.getUpgrade(getIdentifier())+""));
        
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public void setEnabled(boolean enable) {
        enabled = enable;
    }
    
    @Override
    public String getUpgradeMessage() {
        return ColorTranslator.getString(upgradeMessage);
    }
    @Override
    public void setUpgradeMessage(String message) {
        upgradeMessage = message;
    }
    
    @Override
    public int getMaxLevel() {        
        return (getUpgrades().lastKey());
    }
    
    @Override
    public LevelInfo getLevelInfo(int level) {
        if(level>=getMaxLevel())
            return upgrades.get(getMaxLevel());
        else
            if(upgrades.get(level)!=null)
                return upgrades.get(level);
            else
                return new LevelInfo(0, 0, 0);
        
    }
    

    @Override
    public FactionUpgradesPlugin getPlugin() {
        return plugin;
    }
    private void setPlugin(FactionUpgradesPlugin plugin) {
        this.plugin = plugin;        
    }

    public TreeMap<Integer, LevelInfo> getUpgrades() {
        return upgrades;
    }
    private void setUpgrades(TreeMap<Integer, LevelInfo> upgrades) {
        this.upgrades = upgrades;
    }    
    public void addUpgrade(LevelInfo info) {
        upgrades.put(info.getLevel(), info);
    }


}
