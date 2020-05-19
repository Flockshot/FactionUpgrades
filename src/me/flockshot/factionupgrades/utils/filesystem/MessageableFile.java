package me.flockshot.factionupgrades.utils.filesystem;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.flockshot.factionupgrades.utils.files.identifier.FileIdentifier;

public interface MessageableFile {

	public void sendMessage(CommandSender sender, FileIdentifier identifier);
	public void sendMessage(Player player, FileIdentifier identifier);
}
