package me.flockshot.factionupgrades.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.massivecraft.factions.Faction;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.utils.filesystem.files.FactionStorageFile;

public class FactionStorageManager
{
	private FactionUpgradesPlugin plugin;
	private Map<String, FactionStorage> factions = new HashMap<String, FactionStorage>();
	private String factionFolderPath;
	
	public FactionStorageManager(FactionUpgradesPlugin plugin)
	{
		this.plugin = plugin;
		factionFolderPath = plugin.getDataFolder()+ File.separator+"Factions";
		new File(factionFolderPath).mkdir();
	}
	
	
	
	public void createFactionStorage(Faction faction)
	{		
		Map<String, Integer> upgrades = new HashMap<String, Integer>();
		
		for(String upgradeID : plugin.getUpgradeManager().getUpgrades().keySet())
			upgrades.put(upgradeID, 0);
		
		FactionStorage factionStorage = new FactionStorage(faction.getId(), upgrades);
		FactionStorageFile factionFile = new FactionStorageFile(new File(factionFolderPath, faction.getId()));
		factionFile.writeToFile(factionStorage);
		
		factions.put(faction.getId(), factionStorage);
	}

	
	private FactionStorage getFactionFromFile(FactionStorageFile factionFile)
	{		
		FactionStorage faction = null;
		FileConfiguration config = factionFile.getConfig();
		
		if(config.contains("faction"))
		{
			String id = config.getString("faction.id");
			Map<String, Integer> upgrades = new HashMap<String, Integer>();
			
			if(config.contains("faction.upgrades"))
			{
				for(final String upgradeName : config.getConfigurationSection("faction.upgrades").getKeys(false))
				{
					String fullPath = "faction.upgrades."+upgradeName;
					upgrades.put(upgradeName, config.getInt(fullPath, 0));
				}
			}			
			faction = new FactionStorage(id, upgrades);
		}
		
		return faction;
	}	


	
	
	public FactionStorage getFactionFully(String id)
	{
		if(getFaction(id)!=null)
			return getFaction(id);

		FactionStorageFile factionFile = new FactionStorageFile(new File(factionFolderPath, id));
		FactionStorage faction = getFactionFromFile(factionFile);
		if(faction!=null)
		{
			factions.put(faction.getFactionID(), faction);
			return faction;
		}
		return null;
	}

	
	
	
	public void saveAll() {
		for(String id : getFactions().keySet())
			save(id);
	}
	public void save(String id) {
		FactionStorageFile factionFile = new FactionStorageFile(new File(factionFolderPath, id));
		factionFile.writeToFile(getFactions().get(id));
	}

	public void clearUnUsedFactions()
	{
		for(FactionStorage faction : factions.values())
		{
			if((System.currentTimeMillis()-faction.getLastUsed()) >= (1000*60*5))
			{
				save(faction.getFactionID());
				factions.remove(faction.getFactionID());
			}
		}
	}

	private FactionStorage getFaction(String id) {
		return getFactions().get(id);
	}
	public Map<String, FactionStorage> getFactions() {
		return factions;
	}
	public void setFactions(Map<String, FactionStorage> factions) {
		this.factions = factions;
	}



	public void deleteFactionStorage(Faction faction) {
		FactionStorageFile factionFile = new FactionStorageFile(new File(factionFolderPath, faction.getId()));
		factionFile.getFile().delete();		
	}
	
	
}
