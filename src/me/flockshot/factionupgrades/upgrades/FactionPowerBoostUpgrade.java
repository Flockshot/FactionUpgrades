package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public class FactionPowerBoostUpgrade extends UnknownFactionUpgrade
{
	@Override
	public String getIdentifier() {
		return "faction-powerboost";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.FACTION_POWERBOOST;
	}

	public FactionPowerBoostUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void onFactionUpgrade(FactionStorage factionStorage)
	{
		final Faction faction = Factions.getInstance().getFactionById(factionStorage.getFactionID());
		runUpgradeMessage(factionStorage, faction);

		faction.setPowerBoost(getLevelInfo(factionStorage.getUpgrade(getIdentifier())).getValue());
	}
	

}
