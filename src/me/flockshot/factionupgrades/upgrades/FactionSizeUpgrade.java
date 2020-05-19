package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.FPlayerJoinEvent;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;
import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;

public class FactionSizeUpgrade extends UnknownFactionUpgrade
{
    @Override
    public String getIdentifier() {
        return "faction-size";
    }

    @Override
    public FactionUpgradeType getUpgradeType() {
        return FactionUpgradeType.FACTION_SIZE;
    }

    public FactionSizeUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        super(plugin, upgrades);
    }

    @Override
    public void onFactionUpgrade(FactionStorage factionStorage)    {
        runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
    }
        
    
    @EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onFactionJoin(FPlayerJoinEvent event)
    {
        final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(event.getFaction().getId());
        if(factionStorage != null)
        {
            final double value = getLevelInfo(factionStorage.getUpgrade(getIdentifier())).getValue();
            
            if(event.getFaction().getSize()>=value)
            {
                event.setCancelled(true);
                getPlugin().getLanguageHandler().getLangFile().sendMessage(event.getfPlayer().getPlayer(), MessageIdentifier.PLAYER_LIMIT);
            }        
        }
    }
}
