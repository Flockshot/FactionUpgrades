package me.flockshot.factionupgrades.utils.handlers;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import me.flockshot.factionupgrades.utils.filesystem.Language;
import me.flockshot.factionupgrades.utils.filesystem.LanguageFile;
import me.flockshot.factionupgrades.utils.filesystem.files.English;

public class LanguageHandler
{
	private final String langFolder = "Languages";
	private final String langPath;
	
	private Language selected;
	private LanguageFile langFile;
	
	public LanguageHandler(JavaPlugin plugin, Language selection)
	{
		langPath = plugin.getDataFolder() + File.separator + langFolder;
		
		setSelectedLang(selection);		
		try {
			registerLanguageFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registerLanguageFile() throws IOException
    {
		File langFolder = new File(langPath);
		if(!langFolder.exists())
			langFolder.mkdir();
		
		File file = new File(langPath, getSelectedLang()+".yml");
		if(!file.exists())
			file.createNewFile();
		
		LanguageFile lang;
		
		switch(getSelectedLang())
		{
			case EN:
				lang = new English(file);
			default:
				lang = new English(file);		
		}
		
		setLangFile(lang);	
    }

	

	public LanguageFile getLangFile() {
		return langFile;
	}

	public void setLangFile(LanguageFile langFile) {
		this.langFile = langFile;
	}

	public Language getSelectedLang() {
		return selected;
	}

	public void setSelectedLang(Language selected) {
		this.selected = selected;
	}
}
