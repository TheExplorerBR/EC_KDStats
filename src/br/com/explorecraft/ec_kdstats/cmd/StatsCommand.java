package br.com.explorecraft.ec_kdstats.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.explorecraft.ec_kdstats.EC_KDStats;
import br.com.explorecraft.ec_kdstats.storage.Database;

import br.com.explorecraft.ec_kdstats.storage.MySQL.SQLManager;

import br.com.explorecraft.ec_kdstats.storage.SQLite.SQLiteManager;



public class StatsCommand implements CommandExecutor{
	
	
	private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);	
	private SQLiteManager sqlitequery = new SQLiteManager();
	private SQLManager sqlquery= new SQLManager();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		
		if (command.getName().equalsIgnoreCase("stats")) {
			
			if(!(sender instanceof Player)) {
				String OnlyPlayers = plugin.cm.getConfigMsgs().getString("Messages.Only Players");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OnlyPlayers));
				return true;
			}
			
			Player p = (Player) sender;
			String Uuid = p.getUniqueId().toString();			
			
			
			if(args.length == 0) {				
				if (plugin.getConfig().getBoolean("MySQL.enable")) {				
					sqlquery.YourStats(Database.pltable, Uuid, p);
					return true;
				}	else {
								
					sqlitequery.YourStats(Database.pltable, Uuid, p);
					return true;
				}										
			}
			
			
			if(args.length == 1) {			
			Player t = Bukkit.getPlayer(args[0]);			
			    if (t == null) {
			    	String noton = plugin.cm.getConfigMsgs().getString("Messages.Player Not online");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', args[0] + " " + noton) );
				return true;
			    }
			
			    String TUuid = t.getUniqueId().toString();			
			
			    if (plugin.getConfig().getBoolean("MySQL.enable")) {
			
				sqlquery.StatsOther(Database.pltable, TUuid, p, t);
				return true;											
			    }	else {
			    	sqlitequery.StatsOther(Database.pltable, TUuid, p, t);
			    	return true;
			    }		
		     }				
		}		
		
	return false;
	}


}
