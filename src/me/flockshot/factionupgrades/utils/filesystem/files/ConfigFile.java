package me.flockshot.factionupgrades.utils.filesystem.files;

import org.bukkit.plugin.java.JavaPlugin;

import me.flockshot.factionupgrades.utils.filesystem.UnknownPluginConfig;


public class ConfigFile extends UnknownPluginConfig
{
	public ConfigFile(JavaPlugin plugin)	{
		super(plugin);
	}

	
	@Override
	public void setDefaults()
	{
		/*
		List<String> commands = new ArrayList<String>();
		commands.add("message %player% Hey you have claims more then your limit, please remove the claim/s otherwise an admin will do.");

		addDefault(ConfigIdentifier.LIMIT_COMMAND, commands);		
		addDefault(ConfigIdentifier.PERMISSION, "player.claims");	
		*/
	}
	

}
