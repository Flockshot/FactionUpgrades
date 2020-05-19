package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.benzimmer123.koth.Main;
import com.benzimmer123.koth.managers.KOTHManager;
import com.benzimmer123.koth.obj.KOTH;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.StartupRunnable;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;

public class KothCaptureUpgrade extends UnknownFactionUpgrade implements StartupRunnable
{
	final int timerDelay = 20;
	
	@Override
	public String getIdentifier() {
		return "koth-capture";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.KOTH_CAPTURE;
	}
	
	public KothCaptureUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void runTimer()
	{
		new BukkitRunnable()
        {
			KOTHManager man = new KOTHManager(Main.instance);
        	@Override
            public void run()
            {
        		for(KOTH koth : man.getActiveKOTHs())
        		{
        			Player capper = koth.getPlayerCapper();
        			if(capper!=null)
        			{
        				if(FPlayers.getInstance().getByPlayer(capper).hasFaction())
        				{
        					final Faction fac = FPlayers.getInstance().getByPlayer(capper).getFaction();
        					final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(fac.getId());
        					
        					if(factionStorage != null)
        						if(koth.getTimeRemainingAsInt()<=getLevelInfo(factionStorage.getUpgrade(getIdentifier())).getValue())
        							koth.successful();

        				}
        			}
        		}
            }
        }.runTaskTimer(getPlugin(), 0L, timerDelay);
	}
	
	@Override
	public void onFactionUpgrade(FactionStorage factionStorage) {
		runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
	}



}
