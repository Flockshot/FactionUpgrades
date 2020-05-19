package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public class DamageIncreaseUpgrade extends UnknownFactionUpgrade
{
    
    @Override
    public String getIdentifier() {
        return "damage-increase";
    }

    @Override
    public FactionUpgradeType getUpgradeType() {
        return FactionUpgradeType.DAMAGE_INCREASE;
    }

    public DamageIncreaseUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        super(plugin, upgrades);
    }

    @Override
    public void onFactionUpgrade(FactionStorage factionStorage) {

        runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
    }
    
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            final Player attacker = (Player) event.getDamager();
            final FPlayer player = FPlayers.getInstance().getByPlayer(attacker);
            
            if(player.hasFaction())
            {
                final Faction chunkFaction = Board.getInstance().getFactionAt((new FLocation(attacker.getLocation())));
                
                if(chunkFaction.getId().equals(player.getFaction().getId()))
                {
                    final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(player.getFactionId());
                    
                    event.setDamage(event.getDamage()*getLevelInfo(factionStorage.getUpgrade(getIdentifier())).getValue());
                }                
            }
        }
    }


}
