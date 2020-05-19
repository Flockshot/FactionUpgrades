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

public class GappleCooldownReductionUpgrade extends UnknownFactionUpgrade
{
    final String cooldownPerm = "gappleoptions.gapple.";
    final int joinDelay = 5;
    
    @Override
    public String getIdentifier() {
        return "gapple-reduction";
    }

    @Override
    public FactionUpgradeType getUpgradeType() {
        return FactionUpgradeType.GAPPLE_COOLDOWN_REDUCTION;
    }

    public GappleCooldownReductionUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        super(plugin, upgrades);
    }

    @Override
    public void onFactionUpgrade(FactionStorage factionStorage)
    {
        final Faction faction = Factions.getInstance().getFactionById(factionStorage.getFactionID());
        runUpgradeMessage(factionStorage, faction);
        
        int level = factionStorage.getUpgrade(getIdentifier());
        int cooldownOld = 0;
        int cooldownNew = 0;
        if(level<=0)
        {
            cooldownOld = (int) getLevelInfo(0).getValue();
            cooldownNew = (int) getLevelInfo(0).getValue();
        }            
        else
        {
            cooldownOld = (int) getLevelInfo(level-1).getValue();
            cooldownNew = (int) getLevelInfo(level).getValue();
        }

        for(FPlayer player : faction.getFPlayers())
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownOld)+" false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownNew)+" true");
        }

            
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onFactionJoin(FPlayerJoinEvent event)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            @Override
            public void run() {
                final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(event.getFaction().getId());
                if(factionStorage!=null)
                {
                    final int level = factionStorage.getUpgrade(getIdentifier());
                    
                    if(level>=1)
                    {
                        final FPlayer player = event.getfPlayer();
                        final int cooldownOld = (int) getLevelInfo(level-1).getValue();
                        final int cooldownNew = (int) getLevelInfo(level).getValue();
                        
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownOld)+" false");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownNew)+" true");
                    }
                }
            }
        }, joinDelay);
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onFactionLeave(FPlayerLeaveEvent event)
    {
        final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(event.getFaction().getId());
        if(factionStorage!=null)
        {
            final int level = factionStorage.getUpgrade(getIdentifier());
            
            if(level>=1)
            {
                final FPlayer player = event.getfPlayer();
                final int cooldownOld = (int) getLevelInfo(level).getValue();
                final int cooldownNew = (int) getLevelInfo(0).getValue();
                
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownOld)+" false");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getPlugin().getConfig().getString("PermissionCommand").replaceAll("%player%", player.getName()).replaceAll("%perm%", cooldownPerm+cooldownNew)+" true");
            }
        }
    }



}
