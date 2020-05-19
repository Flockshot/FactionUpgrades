package me.flockshot.factionupgrades.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.storage.FactionStorage;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgrade;
import me.flockshot.factionupgrades.upgrademanager.LevelInfo;
import me.flockshot.factionupgrades.utils.ColorTranslator;
import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;

public class UpgradeGui implements InventoryHolder, Listener
{
	private final Inventory inv;
	private FactionUpgradesPlugin plugin;
	private String identifier;
	private Map<Integer, UpgradeItem> upgradeItems = new HashMap<Integer, UpgradeItem>();
	private int rows;
	private String name;
	private final int slotPerRow = 9;

    public UpgradeGui(String identifier, String name, int rows, Map<Integer, UpgradeItem> upgradeItems, FactionUpgradesPlugin plugin)
    {
    	this.plugin = plugin;

        inv = Bukkit.createInventory(this, rows*slotPerRow, ColorTranslator.getString(name));
        this.setIdentifier(identifier);
        
        setRows(rows);
        setName(name);
        
        setUpgradeItems(upgradeItems);
        
        
        
        setItems();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
    
    
	private void setItems()
	{
        for(int index : getUpgradeItems().keySet())
        {
        	ItemStack item = getUpgradeItems().get(index).getItem();
        	inv.setItem(index, item);
        }
	}
	
	private void setItems(Inventory inv, FactionStorage faction, HumanEntity ent)
	{
        for(int index : getUpgradeItems().keySet())
        {
        	UpgradeItem upItem = getUpgradeItems().get(index);   
        	ItemStack item;
        	if(upItem.getUpgrade()!=null)
        	{
                FactionUpgrade upgrade = upItem.getUpgrade();
                LevelInfo info = upgrade.getLevelInfo(faction.getUpgrade(upgrade.getIdentifier())+1);
                upItem.setCost(info.getCost());
                
                item = ColorTranslator.getTranslatedItem(upItem.getItem().clone(), info);
                
            	if(item.hasItemMeta())
            	{
            		ItemMeta meta = item.getItemMeta();
            		if(item.getItemMeta().hasDisplayName())
            			meta.setDisplayName(ColorTranslator.getString(meta.getDisplayName(), faction, upgrade));
            		if(item.getItemMeta().hasLore())
            			meta.setLore(ColorTranslator.getTranslatedLore(meta.getLore(), faction, upgrade));
            		
            		item.setItemMeta(meta);
            	}
        	}
        	else
        		item = ColorTranslator.translateItem(upItem.getItem().clone());

        	inv.setItem(index, item);
        }
	}
	
    // You can open the inventory with this
    public void openInventory(final HumanEntity ent, Faction faction)
    {    	
    	FactionStorage factionStorage = plugin.getFactionManager().getFactionFully(faction.getId());
    	if(factionStorage!=null)
    	{
    		Inventory inv = Bukkit.createInventory(this, getRows()*slotPerRow, ColorTranslator.getString(getName()));
    		setItems(inv, factionStorage, ent);
    		
    		ent.openInventory(inv);
    	}       
    }

    // Check for clicks on items    
    @EventHandler
    public void onDrag(final InventoryDragEvent event)
    {
    	if(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) || event.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) return;
    	if(event.getInventory().getHolder() == null) return;
    	if(event.getInventory().getHolder() != this) return;
    	
    	event.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
    	if(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) || event.getWhoClicked().getGameMode().equals(GameMode.SPECTATOR)) return;
    	if(event.getRawSlot() == -999) return;
    	if(event.getInventory() == null) return;
    	if(event.getInventory().getHolder() == null) return;
    	
    	if(event.getClickedInventory().getHolder().equals(event.getWhoClicked()) && event.getInventory().getHolder() == this)
    	{
    		event.setCancelled(true);
    		return;
    	}
    	if(event.getClickedInventory().getHolder() != this) return;
    	
    	event.setCancelled(true);
    	

    	if(event.getClick().equals(ClickType.CONTROL_DROP) || event.getClick().equals(ClickType.NUMBER_KEY) || event.getClick().equals(ClickType.UNKNOWN) ||
    			event.getClick().equals(ClickType.WINDOW_BORDER_LEFT) || event.getClick().equals(ClickType.WINDOW_BORDER_RIGHT) || event.getClick().equals(ClickType.DROP) ||
    			event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.DOUBLE_CLICK) || event.getClick().equals(ClickType.MIDDLE) ||
    			event.getClick().isShiftClick() || event.getClick().equals(ClickType.SHIFT_RIGHT)) return;
    	
    	if((event.getCursor() != null) && !event.getCursor().getType().equals(Material.AIR)) return;
    	
    	
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player player = (Player) event.getWhoClicked();
        final int slot = event.getRawSlot();
        
        UpgradeItem upgradeItem = getUpgradeItems().get((int) slot);
        
        if(upgradeItem.getUpgrade()==null) return;
        
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        FactionStorage faction = plugin.getFactionManager().getFactionFully(fPlayer.getFactionId());
        
        if(faction!=null)
        {
        	if(faction.getUpgrade(upgradeItem.getUpgrade().getIdentifier())<upgradeItem.getUpgrade().getMaxLevel())
        	{
        		double balance = plugin.getEconomy().getBalance(player);
        		
                if(balance>=upgradeItem.getCost())
                {
                	plugin.getEconomy().withdrawPlayer(player, upgradeItem.getCost());
                	
                	faction.upgradeLevel(upgradeItem.getUpgrade().getIdentifier());
                	upgradeItem.getUpgrade().onFactionUpgrade(faction);
                	
            		setItems(event.getClickedInventory(), faction, player);
                }
            	else
            		plugin.getLanguageHandler().getLangFile().sendMessage(player, MessageIdentifier.NO_MONEY);
        	}
        	else
        		plugin.getLanguageHandler().getLangFile().sendMessage(player, MessageIdentifier.MAX_LEVEL);
        }
    	else
    		plugin.getLanguageHandler().getLangFile().sendMessage(player, MessageIdentifier.NO_FACTION);

    }

	public Map<Integer, UpgradeItem> getUpgradeItems() {
		return upgradeItems;
	}

	public void setUpgradeItems(Map<Integer, UpgradeItem> upgradeItems) {
		this.upgradeItems = upgradeItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
