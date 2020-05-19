package me.flockshot.factionupgrades.upgrades.potion;

import java.util.TreeMap;

import org.bukkit.potion.PotionEffectType;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;

public class SpeedPotionUpgrade extends UnknownPotionUpgrade
{	
	@Override
	public String getIdentifier() {
		return "speed-potion";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.SPEED_POTION;
	}
	
	public PotionEffectType getPotionType()	{
		return PotionEffectType.SPEED;
	}

	public SpeedPotionUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

}
