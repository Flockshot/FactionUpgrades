package me.flockshot.factionupgrades.upgrades;

import java.util.TreeMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeType;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.upgrademanager.UnknownFactionUpgrade;
import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;

public class FactionWarpsUpgrade extends UnknownFactionUpgrade
{
	
	@Override
	public String getIdentifier() {
		return "faction-warps";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.FACTION_WARPS;
	}

	public FactionWarpsUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void onFactionUpgrade(FactionStorage factionStorage)	{
		runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
	}
	
	@EventHandler
	public void onSetWarpCommand(PlayerCommandPreprocessEvent event)
	{
		final FPlayer player = FPlayers.getInstance().getByPlayer(event.getPlayer());
		if(player.hasFaction())
		{
			final String cmd = event.getMessage().toLowerCase();
			if(cmd.startsWith("/factions setwarp") || cmd.startsWith("/f setwarp") || cmd.startsWith("/factions sw") || cmd.startsWith("/f sw"))
			{
				final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(player.getFactionId());
				final int level = factionStorage.getUpgrade(getIdentifier());

				if(player.getFaction().getWarps().size()>=getLevelInfo(level).getValue())
				{
					event.setCancelled(true);
					getPlugin().getLanguageHandler().getLangFile().sendMessage(event.getPlayer(), MessageIdentifier.WARP_LIMIT);
				}
				
			}
		}

	}


}
