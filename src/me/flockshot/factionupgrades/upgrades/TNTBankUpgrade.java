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
import me.flockshot.factionupgrades.utils.NumberUtility;
import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;

public class TNTBankUpgrade extends UnknownFactionUpgrade
{
	@Override
	public String getIdentifier() {
		return "tnt-bank";
	}

	@Override
	public FactionUpgradeType getUpgradeType() {
		return FactionUpgradeType.TNT_BANK;
	}

	public TNTBankUpgrade(FactionUpgradesPlugin plugin, TreeMap<Integer, LevelInfo> upgrades) {
		super(plugin, upgrades);
	}

	@Override
	public void onFactionUpgrade(FactionStorage factionStorage)	{
		runUpgradeMessage(factionStorage, Factions.getInstance().getFactionById(factionStorage.getFactionID()));
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onFillCommand(PlayerCommandPreprocessEvent event)
	{
		final FPlayer player = FPlayers.getInstance().getByPlayer(event.getPlayer());
		if(player.hasFaction())
		{
			final String cmd = event.getMessage().toLowerCase();
			final String[] args = cmd.split(" ");
			if(args.length>=3)
			{
				if(args[0].startsWith("/f") || args[0].startsWith("/factions"))
				{
					if(args[1].startsWith("tnt") || args[1].startsWith("trinitrotoluene"))
					{
						final FactionStorage factionStorage = getPlugin().getFactionManager().getFactionFully(player.getFactionId());						
						final int level = factionStorage.getUpgrade(getIdentifier());
						
						if(args[2].startsWith("siphon") || args[2].startsWith("s"))
						{
							if(level<getMaxLevel())
							{
								event.setCancelled(true);
								getPlugin().getLanguageHandler().getLangFile().sendMessage(event.getPlayer(), MessageIdentifier.SIPHON_MAX_LEVEL);
							}
						}
						else if(args[2].startsWith("deposit") || args[2].startsWith("d"))
						{
							
							if(args.length>=4)
							{
								if(NumberUtility.isNum(args[3]))
								{									
									final int toDeposit = Integer.parseInt(args[3]);
									final int max = (int) getLevelInfo(level).getValue();
									final int inBank = player.getFaction().getTNTBank();
									int space = max-inBank;

									if(space == 0)
									{
										event.setCancelled(true);
										getPlugin().getLanguageHandler().getLangFile().sendMessage(event.getPlayer(), MessageIdentifier.TNT_BANK_FULL);
									}
									else if(space < toDeposit)
									{
										event.setCancelled(true);
										event.getPlayer().performCommand("f tnt deposit "+space);
									}
								}
							}							
						}					
					}
				}
			}
		}
	}
}
