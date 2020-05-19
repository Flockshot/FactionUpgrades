package me.flockshot.factionupgrades.gui;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.flockshot.factionupgrades.upgrademanager.FactionUpgrade;
import me.flockshot.factionupgrades.utils.ColorTranslator;

public class UpgradeItem 
{
	private String name;
	private List<String> lore;
	
	private ItemStack item;
	private FactionUpgrade upgrade;
	private double cost;
	//private String headName;
	
	public UpgradeItem(String name, List<String> lore, ItemStack item, FactionUpgrade upgrade)
	{
		setName(name);
		setLore(lore);
		setUpgrade(upgrade);
		//setHeadName(headName);
		
		setItem(ColorTranslator.translateItem(item));
	}






	public List<String> getLore() {
		return lore;
	}
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getItem() {
		return item;
	}
	public void setItem(ItemStack item) {
		this.item = item;
	}

	public FactionUpgrade getUpgrade() {
		return upgrade;
	}
	public void setUpgrade(FactionUpgrade upgrade) {
		this.upgrade = upgrade;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

}
