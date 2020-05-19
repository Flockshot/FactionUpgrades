package me.flockshot.factionupgrades.gui;

import org.bukkit.inventory.Inventory;

public class UpgradeInventory
{
    private Inventory inv;
    private String id;
    private long lastUsed;
    
    public UpgradeInventory(Inventory inventory, String id)
    {
        setInventory(inventory);
        setId(id);
        setLastUsed(System.currentTimeMillis());
    }
    
    
    public Inventory getInventory() {
        return inv;
    }

    public void setInventory(Inventory inv) {
        this.inv = inv;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}
