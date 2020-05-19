package me.flockshot.factionupgrades;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;

import me.flockshot.factionupgrades.commands.UpgradeCommand;
import me.flockshot.factionupgrades.gui.UpgradeGUIManager;
import me.flockshot.factionupgrades.storage.FactionStorageManager;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradeManager;
import me.flockshot.factionupgrades.upgrademanager.FactionUpgradesRegistry;
import me.flockshot.factionupgrades.upgrademanager.StartupRunnable;
import me.flockshot.factionupgrades.upgrades.DamageDecreaseUpgrade;
import me.flockshot.factionupgrades.upgrades.DamageIncreaseUpgrade;
import me.flockshot.factionupgrades.upgrades.FactionFlightUpgrade;
import me.flockshot.factionupgrades.upgrades.FactionPowerBoostUpgrade;
import me.flockshot.factionupgrades.upgrades.FactionSizeUpgrade;
import me.flockshot.factionupgrades.upgrades.FactionWarpsUpgrade;
import me.flockshot.factionupgrades.upgrades.GappleCooldownReductionUpgrade;
import me.flockshot.factionupgrades.upgrades.KothCaptureUpgrade;
import me.flockshot.factionupgrades.upgrades.MobXpUpgrade;
import me.flockshot.factionupgrades.upgrades.TNTBankUpgrade;
import me.flockshot.factionupgrades.upgrades.potion.SpeedPotionUpgrade;
import me.flockshot.factionupgrades.upgrades.potion.StrengthPotionUpgrade;
import me.flockshot.factionupgrades.utils.filesystem.Language;
import me.flockshot.factionupgrades.utils.handlers.LanguageHandler;
import net.milkbowl.vault.economy.Economy;


public class FactionUpgradesPlugin extends JavaPlugin implements Listener
{
    
    private boolean running = false;
    private static FactionUpgradesPlugin instance;
    private FactionUpgradesRegistry upgradesRegistry;
    private FactionUpgradeManager upgradeManager;
    private FactionStorageManager factionManager;
    private UpgradeGUIManager guiManager;
    private Economy econ = null;
    
    private LanguageHandler languageHandler;
    //private ConfigHandler configHandler;
    
    
    
    
    public void onEnable()    
    {
        setInstance(this);
        
        getConfig().options().copyDefaults(true);        
        saveConfig();
            
        //setConfigHandler(new ConfigHandler(this));
        setLanguageHandler(new LanguageHandler(this, Language.EN));        
        
        if(!running)
        {
            setupEconomy();

            setUpgradesRegistry(new FactionUpgradesRegistry());
            registerUpgrades();
            
            setUpgradeManager(new FactionUpgradeManager(this));
            setFactionManager(new FactionStorageManager(this));
            setGuiManager(new UpgradeGUIManager(this));
            
            getCommand("facupgrade").setExecutor(new UpgradeCommand(this));
            getServer().getPluginManager().registerEvents(this, this);
            
            runAllRunables();
            
            running = true;
        }
    }

    private void runAllRunables() {
        getUpgradeManager().getUpgrades().values().stream().filter(upgrade -> upgrade instanceof StartupRunnable).forEach(upgrade -> ((StartupRunnable) upgrade).runTimer());
    }
    
    private void registerUpgrades()
    {
        getUpgradesRegistry().register(new FactionFlightUpgrade(this, null));
        getUpgradesRegistry().register(new FactionWarpsUpgrade(this, null));
        getUpgradesRegistry().register(new FactionSizeUpgrade(this, null));
        getUpgradesRegistry().register(new FactionPowerBoostUpgrade(this, null));
        getUpgradesRegistry().register(new MobXpUpgrade(this, null));
        getUpgradesRegistry().register(new DamageIncreaseUpgrade(this, null));
        getUpgradesRegistry().register(new DamageDecreaseUpgrade(this, null));
        getUpgradesRegistry().register(new TNTBankUpgrade(this, null));
        getUpgradesRegistry().register(new KothCaptureUpgrade(this, null));
        
        getUpgradesRegistry().register(new SpeedPotionUpgrade(this, null));
        getUpgradesRegistry().register(new StrengthPotionUpgrade(this, null));
        
        getUpgradesRegistry().register(new GappleCooldownReductionUpgrade(this, null));

    }

    public void onDisable()
    {
    }

    
    //TODO ON FACTION CREATE AND DELETION
    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        //Bukkit.broadcastMessage("Created "+event.getFaction().getId());
        getFactionManager().createFactionStorage(event.getFaction());
    }
    
    @EventHandler
    public void onFactionDelete(FactionDisbandEvent event) {
        getFactionManager().deleteFactionStorage(event.getFaction());
    }

    

    
    
    private boolean setupEconomy()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    
    public static FactionUpgradesPlugin getInstance() {
        return instance;
    }

    public static void setInstance(FactionUpgradesPlugin instance) {
        FactionUpgradesPlugin.instance = instance;
    }

    public FactionUpgradesRegistry getUpgradesRegistry() {
        return upgradesRegistry;
    }

    public void setUpgradesRegistry(FactionUpgradesRegistry upgradesRegistry) {
        this.upgradesRegistry = upgradesRegistry;
    }

    public FactionStorageManager getFactionManager() {
        return factionManager;
    }

    public void setFactionManager(FactionStorageManager factionManager) {
        this.factionManager = factionManager;
    }

    public Economy getEconomy() {
        return econ;
    }

    public void setEconomy(Economy econ) {
        this.econ = econ;
    }

    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }

    public void setLanguageHandler(LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
    }

    public FactionUpgradeManager getUpgradeManager() {
        return upgradeManager;
    }

    public void setUpgradeManager(FactionUpgradeManager upgradeManager) {
        this.upgradeManager = upgradeManager;
    }

    public UpgradeGUIManager getGuiManager() {
        return guiManager;
    }

    public void setGuiManager(UpgradeGUIManager guiManager) {
        this.guiManager = guiManager;
    }

    
    
}
