package me.flockshot.factionupgrades.upgrades.potion;

import java.util.TreeMap;

import org.bukkit.potion.PotionEffectType;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;

public class StrengthPotionUpgrade extends UnknownPotionUpgrade
{
    
    @Override
    public String getIdentifier() {
        return "strength-potion";
    }

    @Override
    public FactionUpgradeType getUpgradeType() {
        return FactionUpgradeType.STRENGTH_POTION;
    }
    
    public PotionEffectType getPotionType()    {
        return PotionEffectType.INCREASE_DAMAGE;
    }

    public StrengthPotionUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
        super(plugin, upgrades);
    }


}
