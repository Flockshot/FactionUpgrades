package me.flockshot.factionupgrades.storage;

import java.util.HashMap;
import java.util.Map;

public class FactionStorage
{
    private String factionID;
    private Map<String, Integer> upgrades;
    private long lastUsed;
    
    
    public FactionStorage(String id)
    {
        this(id, new HashMap<String, Integer>());
    }
    
    public FactionStorage(String id, Map<String, Integer> upgrades)
    {
        setFactionID(id);
        setUpgrades(upgrades);
        setLastUsed(0);
    }
    
    
    
    public void upgradeLevel(String id) {
        upgrades.put(id, getUpgrade(id)+1);
    }


    public String getFactionID() {
        return factionID;
    }

    public void setFactionID(String factionID) {
        this.factionID = factionID;
    }
    
    public int getUpgrade(String id) {
        return getUpgrades().get(id)!=null ? getUpgrades().get(id) : 0;
    }
    
    public Map<String, Integer> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(Map<String, Integer> upgrades) {
        this.upgrades = upgrades;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }



    
    
}
