package me.flockshot.factionupgrades.utils;

import org.bukkit.Bukkit;

public class FlockUtils
{

	public static boolean isOldVersion() {		
		return Bukkit.getVersion().contains("1.5") || Bukkit.getVersion().contains("1.6") || Bukkit.getVersion().contains("1.7") || Bukkit.getVersion().contains("1.8");
	}
	
	public static boolean isVersionPre_1_13() {		
		return isOldVersion() || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12");
	}
	
}
