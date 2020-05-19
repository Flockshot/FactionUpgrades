package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public class MobXpUpgrade extends UnknownFactionUpgrade
{
	@Override
	public String getIdentifier() {
		return "mob-xp";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.MOB_XP;
	}

	public MobXpUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void onFactionUpgrade(FactionStorage factionStorage)	{
		runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
	}
	
	
	@EventHandler
	public void onMobKill(EntityDeathEvent event)
	{
		if(!(event.getEntity() instanceof Player))
		{
			final Faction faction = Board.getInstance().getFactionAt(new FLocation(event.getEntity().getLocation()));
			final FactionStorage storage = getPlugin().getFactionManager().getFactionFully(faction.getId());
			
			if(storage!=null)
				if(storage.getUpgrade(getIdentifier())>0)
					event.setDroppedExp(event.getDroppedExp() * (int) getPlugin().getUpgradeManager().getUpgrade(getIdentifier()).getLevelInfo(storage.getUpgrade(getIdentifier())).getValue());
			
			
		}
	}



}
