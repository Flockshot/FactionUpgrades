package me.flockshot.factionupgrades.utils;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtility
{    

    public static boolean isSameList(List<String> list1, List<String> list2)
    {
        if(list1 != null && list2 != null && (list1.size() == list2.size()))
        {
            list1.removeAll(list2);
            return list1.isEmpty();

        }
        return false;
    }
    
    public static boolean isSameLore(ItemMeta meta1, ItemMeta meta2)
    {
        if(meta1.hasLore() && meta2.hasLore())
            return isSameList(meta1.getLore(), meta2.getLore());

        return !(meta1.hasLore() ^ meta2.hasLore());

    }
    
    public static boolean isSameEnchants(ItemMeta meta1, ItemMeta meta2)
    {
        if(meta1.hasEnchants() && meta2.hasEnchants())
            return meta1.getEnchants().equals(meta2.getEnchants());

        return (!(meta1.hasEnchants() ^ meta2.hasEnchants()));

    }
    
    public static boolean isSameName(ItemMeta meta1, ItemMeta meta2)
    {
        if(meta1.hasDisplayName() && meta2.hasDisplayName())
            return meta1.getDisplayName().equals(meta2.getDisplayName());

        return !(meta1.hasDisplayName() ^ meta2.hasDisplayName());

    }
    
    
    
    public static boolean isSameAll(ItemStack item1, ItemStack item2)
    {
        if(item1.getType().equals(item2.getType()))
        {
            if(item1.hasItemMeta() && item2.hasItemMeta())
                return (isSameName(item1.getItemMeta(), item2.getItemMeta()) && isSameLore(item1.getItemMeta(), item2.getItemMeta()) && isSameEnchants(item1.getItemMeta(), item2.getItemMeta()));

            return !(item1.hasItemMeta() ^ item2.hasItemMeta());

        }
        return false;
    }
}
