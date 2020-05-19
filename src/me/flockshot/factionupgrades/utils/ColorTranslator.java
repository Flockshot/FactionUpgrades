package me.flockshot.factionupgrades.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgrade;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;

public class ColorTranslator
{    
    public static List<String> getTranslatedLore(List<String> lore)
    {
        List<String> translatedList = new ArrayList<String>();
        
        if(lore!=null && !lore.isEmpty())    
            for(String line : lore)
                translatedList.add(getString(line));

        return translatedList;
    }
    
    public static String getString(String st) {
        return ChatColor.translateAlternateColorCodes('&', st);
    }
    
    public static ItemStack translateItem(ItemStack item)
    {
        if(item.hasItemMeta())
        {
            ItemMeta meta = item.getItemMeta();
            
            if(meta.hasLore())
                meta.setLore(getTranslatedLore(meta.getLore()));
            
            if(meta.hasDisplayName())
                meta.setDisplayName(getString(meta.getDisplayName()));
                
            item.setItemMeta(meta);
        }
        return item;
    }

    public static String getString(String string, FactionStorage faction, FactionUpgrade upgrade)
    {
        return getString(string.replaceAll("%current_level%", faction.getUpgrade(upgrade.getIdentifier())+"").replaceAll("%max_level%", upgrade.getMaxLevel()+"")
                .replaceAll("%current_value%", upgrade.getLevelInfo(faction.getUpgrade(upgrade.getIdentifier())).getValue()+""));
    }
    
    public static List<String> getTranslatedLore(List<String> lore, FactionStorage faction, FactionUpgrade upgrade)
    {
        List<String> translatedList = new ArrayList<String>();
        
        if(lore!=null && !lore.isEmpty())    
            for(String line : lore)
                translatedList.add(getString(line, faction, upgrade));

        return translatedList;
    }

    public static ItemStack getTranslatedItem(ItemStack item, LevelInfo info)
    {
        if(item.hasItemMeta())
        {
            ItemMeta meta = item.getItemMeta();
            
            if(meta.hasLore())
                meta.setLore(getTranslatedLore(meta.getLore(), info));
            
            if(meta.hasDisplayName())
                meta.setDisplayName(getString(meta.getDisplayName(), info));
                
            item.setItemMeta(meta);
        }
        return item;
    }

    private static String getString(String string, LevelInfo info) {
        return getString(string.replaceAll("%cost%", info.getCost()+"").replaceAll("%upgrade_value%", info.getValue()+""));
    }

    private static List<String> getTranslatedLore(List<String> lore, LevelInfo info)
    {
        List<String> translatedList = new ArrayList<String>();
        
        if(lore!=null && !lore.isEmpty())    
            for(String line : lore)
                translatedList.add(getString(line, info));

        return translatedList;
    }


}
