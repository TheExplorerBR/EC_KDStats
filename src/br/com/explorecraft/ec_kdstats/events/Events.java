package br.com.explorecraft.ec_kdstats.events;



import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.explorecraft.ec_kdstats.EC_KDStats;
import br.com.explorecraft.ec_kdstats.storage.Database;
import br.com.explorecraft.ec_kdstats.storage.MySQL.SQLManager;
import br.com.explorecraft.ec_kdstats.storage.SQLite.SQLiteManager;



public class Events implements Listener{
	private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);
	
	private SQLiteManager sqlitequery = new SQLiteManager();			
	private SQLManager sqlquery= new SQLManager();

	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		String pName = p.getName();
		
		if (plugin.getConfig().getBoolean("MySQL.enable")) {			
		sqlquery.insertNewPlayer(Database.pltable, uuid, pName);			
		} else {
			sqlitequery.insertNewPlayer(Database.pltable, uuid, pName);
		}		
	}	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(p.getKiller() instanceof Player) {			
			Player k = p.getKiller();
			String kUuid = k.getUniqueId().toString();
			String pUuid = p.getUniqueId().toString();
			
			
			if (plugin.getConfig().getBoolean("MySQL.enable")){			
			sqlquery.updateKills(Database.pltable, kUuid);
			sqlquery.updateDeaths(Database.pltable, pUuid);			
			} else {				
				sqlitequery.updateKills(Database.pltable, kUuid);
				sqlitequery.updateDeaths(Database.pltable, pUuid);
			}
			
			String killmsg = plugin.cm.getConfigMsgs().getString("Messages.Kill Player");
			String killedmsg= plugin.cm.getConfigMsgs().getString("Messages.Killed by Player");		
			
			k.sendMessage(ChatColor.translateAlternateColorCodes('&',  killmsg) + " " +  ChatColor.BLUE + p.getName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',  killedmsg) + " " + ChatColor.BLUE + k.getName());	
	    }	
					
	}		
}
	
	


