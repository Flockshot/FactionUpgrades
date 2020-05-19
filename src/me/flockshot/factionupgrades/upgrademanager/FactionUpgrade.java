package me.flockshot.factionupgrades.upgrademanager;

import org.bukkit.event.Listener;

import com.massivecraft.factions.Faction;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;

public interface FactionUpgrade extends Listener
{    
    public String getIdentifier();
    
    public FactionUpgradeType getUpgradeType();
    
    public LevelInfo getLevelInfo(int level);
    
    public void onFactionUpgrade(FactionStorage factionStorage);
    
    public void runUpgradeMessage(FactionStorage factionStorage, Faction faction);
    
    public FactionUpgradesPlugin getPlugin();
    
    public void setUpgradeMessage(String message);
    public String getUpgradeMessage();
    
    public void setEnabled(boolean enabled);
    public boolean isEnabled();

    int getMaxLevel();

    
}
