package me.flockshot.factionupgrades.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import me.flockshot.factionupgrades.FactionUpgradesPlugin;
import me.flockshot.factionupgrades.utils.files.identifier.MessageIdentifier;
import net.md_5.bungee.api.ChatColor;

public class UpgradeCommand implements CommandExecutor, TabCompleter
{
    FactionUpgradesPlugin plugin;

    public UpgradeCommand(FactionUpgradesPlugin plugin) {
        this.plugin = plugin;
    }

    String command = "/facupgrade ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        
        if(args.length >= 1)
        {
            String subCommand = args[0].toLowerCase();
            if(subCommand.equals("reload"))
                reload(sender);
            else if(subCommand.equals("info"))
                pluginInfo(sender);
            else
                commandInfo(sender);
        }
        else
            openGUI(sender);
        
        return false;
    }

    private void openGUI(CommandSender sender)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);
            if(factionPlayer.hasFaction())
            {
                plugin.getGuiManager().getGUI().openInventory(player, factionPlayer.getFaction());
            }
            else
                plugin.getLanguageHandler().getLangFile().sendMessage(player, MessageIdentifier.NO_FACTION);
        }
        else
            sender.sendMessage(ChatColor.DARK_RED+"Only Players can use this command");        
    }

    private void reload(CommandSender sender)
    {
        if(sender.hasPermission("permissionclaim.admin.reload.reload"))
        {
            plugin.reloadConfig();
            plugin.saveConfig();
            plugin.onDisable();
            plugin.onEnable();
            sender.sendMessage(ChatColor.DARK_GREEN + "Plugin reloaded ");
        }
        else
            sender.sendMessage(ChatColor.DARK_RED+"You don't have permission for this");
    }

    private void commandInfo(CommandSender sender)
    {
        sender.sendMessage(ChatColor.YELLOW + command + "reload");
        sender.sendMessage(ChatColor.GRAY + "Reload the plugin");
    }
    

    private void pluginInfo(CommandSender sender)
    {
        PluginDescriptionFile p = plugin.getDescription();
        
        sender.sendMessage(ChatColor.YELLOW + "Plugin" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getName());
        sender.sendMessage(ChatColor.YELLOW + "Version" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getVersion());
        sender.sendMessage(ChatColor.YELLOW + "Made by" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getAuthors());
        sender.sendMessage(ChatColor.YELLOW + "Description" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + p.getDescription());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
