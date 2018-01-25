package br.com.explorecraft.ec_kdstats;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.explorecraft.ec_kdstats.cmd.StatsCommand;
import br.com.explorecraft.ec_kdstats.configs.ConfigManager;
import br.com.explorecraft.ec_kdstats.configs.Messages;
import br.com.explorecraft.ec_kdstats.events.Events;
import br.com.explorecraft.ec_kdstats.storage.Database;
import br.com.explorecraft.ec_kdstats.storage.MySQL.MySQL;
import br.com.explorecraft.ec_kdstats.storage.MySQL.SQLManager;
import br.com.explorecraft.ec_kdstats.storage.SQLite.SQLiteManager;

public class EC_KDStats extends JavaPlugin {
	
	public SQLiteManager sqlitequery;
	public MySQL mysql;	 
	public SQLManager sqlquery ;
	public ConfigManager cm;
	
	
	public void onEnable() {		
		
		loadConfig();
		loadMessages();
		loadStorage();
		
		registerCMD();
		registerEvents();
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EC_KDStats] Enabled");
		
		if (getConfig().getBoolean("MySQL.enable")) {			
		sqlquery.createTable(Database.pltable, Database.plcolumns);		
		} else {			
			sqlitequery.createTable(Database.pltable, Database.plcolumns);
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "You are using SQLite Storage. If you can use MySQL, set your data in at config.yml");
		}			
	}	
	
	public void onDisable() {		
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[EC_KDStats] Disabled");
	}	
	
	public void loadConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}
	
	public void loadMessages() {
		cm = new ConfigManager();
		cm.setup();
		new Messages(this);
	}
	
	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new Events(), this);
		
	}
	
	public void registerCMD() {
		getCommand("kdstats").setExecutor(new StatsCommand());
		
		
	}
	
	public void loadStorage() {
		sqlitequery = new SQLiteManager();		
		mysql = new MySQL();
		sqlquery= new SQLManager();
		
	}
	

}
