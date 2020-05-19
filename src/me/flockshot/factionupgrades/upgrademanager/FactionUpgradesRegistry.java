package me.flockshot.factionupgrades.upgrademanager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.TreeMap;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;

public class FactionUpgradesRegistry 
{
	private HashMap<String, FactionUpgrade> registered = new HashMap<String, FactionUpgrade>();
	
	public FactionUpgrade getFromIdentifier(String name, TreeMap<Integer, LevelInfo> levels)
	{
		if(name==null)
			return null;
		FactionUpgrade upgrade = registered.get(name.toLowerCase());
		
		if(upgrade!=null)
			try {
				return upgrade.getClass().getDeclaredConstructor(FactionUpgradesPlugin.class, (new TreeMap<Integer, LevelInfo>()).getClass()).newInstance(upgrade.getPlugin(), levels);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public void register(FactionUpgrade upgrade) {
		registered.putIfAbsent(upgrade.getIdentifier().toLowerCase(), upgrade);
	}
}
