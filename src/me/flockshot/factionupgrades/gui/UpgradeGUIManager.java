package me.flockshot.factionupgrades.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgrade;
import me.flockshot.factionupgrades.utils.ColorTranslator;
import me.flockshot.factionupgrades.utils.NumberUtility;

public class UpgradeGUIManager
{
	private FactionUpgradesPlugin plugin;
	private UpgradeGui gui;
	
	public UpgradeGUIManager(FactionUpgradesPlugin plugin)
	{
		this.plugin = plugin;
		initGUI();
	}

	private void initGUI()
	{
		Map<Integer, UpgradeItem> upgradeItems = getUpgradeItems(plugin.getConfig());		
		setGUI(new UpgradeGui("UpgradeGUI", plugin.getConfig().getString("inv.name", "Upgrade Faction"), plugin.getConfig().getInt("inv.rows", 3), upgradeItems, plugin)); 
		plugin.getServer().getPluginManager().registerEvents(getGUI(), plugin);
	}

	private Map<Integer, UpgradeItem> getUpgradeItems(FileConfiguration config)
	{
		Map<Integer, UpgradeItem> upgradeItems = new HashMap<Integer, UpgradeItem>();

		if(config.contains("inv.items"))
		{
			for(String index : config.getConfigurationSection("inv.items").getKeys(false))
			{
				if(NumberUtility.isNum(index))
				{
					String fullPath = "inv.items."+index;
					
					ItemStack item = ColorTranslator.translateItem(ItemStack.deserialize(config.getConfigurationSection(fullPath).getValues(true)));

					String name = item.hasItemMeta() ? item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "" : "";
					List<String> lore = item.hasItemMeta() ? item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>() : new ArrayList<String>();
					
					//String headName = config.getString(fullPath+"headOwner", "");
					
					FactionUpgrade upgrade = plugin.getUpgradeManager().getUpgrade(config.getString(fullPath+".upgrade", "").toLowerCase());
					
					UpgradeItem upItem = new UpgradeItem(name, lore, item, upgrade);

					upgradeItems.put(Integer.valueOf(index), upItem);
				}
			}
		}
		
		return upgradeItems;
	}

	public UpgradeGui getGUI() {
		return gui;
	}

	public void setGUI(UpgradeGui gui) {
		this.gui = gui;
	}
	
	
	
}
