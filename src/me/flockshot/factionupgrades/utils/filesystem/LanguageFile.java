package me.flockshot.factionupgrades.utils.filesystem;

public interface LanguageFile extends FlockFile, MessageableFile
{
	public Language getLanguage();
	public void setLanguage(Language lang);
	
}
