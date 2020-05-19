package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public class FactionFlightUpgrade extends UnknownFactionUpgrade
{
    final String flyPerm = "factions.fly";
    final int joinDelay = 5;
    
    @Override
    public String getIdentifier() {
        return "faction-flight";
    }

    @Override
    public FactionUpgradeType getUpgradeType() {
        return FactionUpgradeType.FACTION_FLIGHT;
    }

    public FactionFlightUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        super(plugin, upgrades);
    }

    @Override
    public void onFactionUpgrade(FactionStorage factionStorage)
    {
        final Faction faction = Factions.getInstance().getFactionById(factionStorage.getFactionID());
        runUpgradeMessage(factionStorage, faction);
        
        if(factionStorage.getUpgrade(getIdentifier())>=1)
            for(FPlayer player : faction.getFPlayers())
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", flyPerm));
        
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onFactionJoin(FPlayerJoinEvent event)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            @Override
            public void run() {
                FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(event.getFaction().getId());
                if(factionStorage!=null)
                    if(factionStorage.getUpgrade(getIdentifier())>=1)
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", event.getfPlayer().getName()).replaceAll("%perm%", flyPerm));
            }
        }, joinDelay);        
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onFactionLeave(FPlayerLeaveEvent event)    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", event.getfPlayer().getName()).replaceAll("%perm%", flyPerm)+" false");
    }



}
