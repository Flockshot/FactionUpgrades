package me.flockshot.factionupgrades.utils.filesystem;

import java.io.File;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.flockshot.factionupgrades.utils.ColorTranslator;
import me.flockshot.factionupgrades.utils.files.identifier.FileIdentifier;


public abstract class UnknownLanguage extends UnknownFile implements LanguageFile
{
	private Language language;
	
	
	public UnknownLanguage(File file) {
		super(file);
		
	}
	
	
	@Override
	public void sendMessage(Player player, FileIdentifier identifier) {		
		sendMessage((CommandSender) player, identifier);
	}
	
	@Override
	public void sendMessage(CommandSender sender, FileIdentifier identifier) {		
		sender.sendMessage(ColorTranslator.getString((String) getLine(identifier)));		
	}
	
	@Override
	public Language getLanguage() {
		return language;
	}
	@Override
	public void setLanguage(Language language) {
		this.language = language;
	}

}
