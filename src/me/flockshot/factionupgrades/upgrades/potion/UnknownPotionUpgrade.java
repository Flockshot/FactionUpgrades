package me.flockshot.factionupgrades.upgrades.potion;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.StartupRunnable;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public abstract class UnknownPotionUpgrade extends UnknownFactionUpgrade implements StartupRunnable
{
	private final int ticks = 50;	
	public abstract PotionEffectType getPotionType();

	public UnknownPotionUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void onFactionUpgrade(FactionStorage factionStorage) {
		runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
	}
	
	@Override
	public void runTimer()
	{
		new BukkitRunnable()
        {
        	@Override
            public void run()
            {
        		for(Player player : Bukkit.getOnlinePlayers())
        		{
        			final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        			
        			if(fPlayer.hasFaction())
        			{
    					final Faction fac = fPlayer.getFaction();    					
        				if(fac.getId().equals(Board.getInstance().getFactionAt(new FLocation(player.getLocation())).getId()))
        				{        					
        					final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(fac.getId());
        					if(factionStorage!=null)
        					{
            					final double value = getLevelInfo(factionStorage.getUpgrade(getIdentifier())).getValue();
            					if(value>0)
            						player.addPotionEffect(new PotionEffect(getPotionType(), ticks, (int) value-1));
            					
        					}
        				}
        			}
        		}
            }
        }.runTaskTimer(getPlugin(), 0L, ticks);
	}



}
