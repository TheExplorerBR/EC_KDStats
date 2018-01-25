package br.com.explorecraft.ec_kdstats.storage.SQLite;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import br.com.explorecraft.ec_kdstats.EC_KDStats;


public class SQLiteManager {
	
    private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);
	
	private SQLite sqlite= new SQLite();
	
	public void createTable(String table, String columns) {
		try {
			String cmd = ("CREATE TABLE IF NOT EXISTS " + table + "(" + columns + ");");
			sqlite.sqLiteconnection();
			Bukkit.getConsoleSender().sendMessage("§6[EC.MySqlite]Creating table <" + table + ">...");
			DatabaseMetaData metadados = sqlite.getConnection().getMetaData();
			ResultSet rs = metadados.getTables(null, null, table, null);
			if (rs.next()) {
				Bukkit.getConsoleSender().sendMessage("§c[EC.MySqlite]The table exists!");				
				sqlite.getConnection().close();
				sqlite.setConnection(null);
			} else {
				PreparedStatement st = sqlite.getConnection().prepareStatement(cmd);
				st.executeUpdate();
				sqlite.getConnection().close();
				sqlite.setConnection(null);
				Bukkit.getConsoleSender().sendMessage("§a[EC.MySqlite]The StatsPlayer SQLite table was created!");
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[EC.MySqlite]The StatsPlayer table could not created!");
			e.printStackTrace();
		}
	}




public void insertNewPlayer(String table, String uuid, String pName) {
	
	try {
		String cmd = ("SELECT * FROM " + table + " WHERE  uuid = ?");						
		
		sqlite.sqLiteconnection();
		PreparedStatement s = sqlite.getConnection().prepareStatement(cmd);
		s.setString(1, uuid);
		ResultSet rs = s.executeQuery();
		
		if (!rs.next()) {
			String insert = ("INSERT INTO " + table + "(uuid, name, kills, deaths) VALUES (?, ?, ?, ?)");
			PreparedStatement st = sqlite.getConnection().prepareStatement(insert);
			st.setString(1, uuid);
			st.setString(2, pName);
			st.setInt(3, 0);
			st.setInt(4, 0);
			st.executeUpdate();
			sqlite.getConnection().close();
			sqlite.setConnection(null);
		} else {
		
			sqlite.getConnection().close();
			sqlite.setConnection(null);	
		}
		
	} catch (Exception erro) {
		erro.printStackTrace();
	}		
	
}

public void updateKills(String table, String kUuid) {
	
	try {
		String cmd = ("SELECT kills FROM " + table + " WHERE  uuid = ?");
		sqlite.sqLiteconnection();	
		PreparedStatement s = sqlite.getConnection().prepareStatement(cmd);
		s.setString(1, kUuid);
		ResultSet rs = s.executeQuery();
		 
		if (rs.next()) {				
			int kills = rs.getInt("kills");				
			kills++;				
			String cmd2 = ("UPDATE " + table + " SET kills = ? WHERE  uuid = ?");
			PreparedStatement s2 = sqlite.getConnection().prepareStatement(cmd2);
			s2.setInt(1, kills);
			s2.setString(2, kUuid);
			s2.executeUpdate();				  
			sqlite.getConnection().close();
			sqlite.setConnection(null);
		} 	else {		
			sqlite.getConnection().close();
			sqlite.setConnection(null);	
		}
		
	} catch (Exception erro) {
		erro.printStackTrace();
	}
}

public void updateDeaths(String table, String pUuid) {
	
	try {
		String cmd = ("SELECT deaths FROM " + table + " WHERE  uuid = ?");
		sqlite.sqLiteconnection();		
		
		PreparedStatement s = sqlite.getConnection().prepareStatement(cmd);
		s.setString(1, pUuid);
		ResultSet rs = s.executeQuery();
		 
		if (rs.next()) {				
			int deaths = rs.getInt("deaths");				
			deaths++;				
			String cmd2 = ("UPDATE " + table + " SET deaths = ? WHERE  uuid = ?");
			PreparedStatement s2 = sqlite.getConnection().prepareStatement(cmd2);
			s2.setInt(1, deaths);
			s2.setString(2, pUuid);
			s2.executeUpdate();
			sqlite.getConnection().close();
			sqlite.setConnection(null);
		} else	{		
			sqlite.getConnection().close();
			sqlite.setConnection(null);
		}
		
	} catch (Exception erro) {
		erro.printStackTrace();
	}	
	
}

public void YourStats(String table, String Uuid, Player p) {
	
	try {
		String cmd = ("SELECT kills,deaths FROM " + table + " WHERE  uuid = ?");
		sqlite.sqLiteconnection();		
		
		PreparedStatement s = sqlite.getConnection().prepareStatement(cmd);
		s.setString(1, Uuid);
		ResultSet rs = s.executeQuery();
		 
		if (rs.next()) {				
			int deaths = rs.getInt("deaths");
			int kills = rs.getInt("kills");						
			sqlite.getConnection().close();
			sqlite.setConnection(null);
			String kdstatus = plugin.cm.getConfigMsgs().getString("Messages.Stats Of");
			String kill = plugin.cm.getConfigMsgs().getString("Messages.Kill");
			String death = plugin.cm.getConfigMsgs().getString("Messages.Death");
			p.sendMessage(ChatColor.YELLOW + "---------------------------------------------------\n"
					+ ChatColor.translateAlternateColorCodes('&', kdstatus + " " +  (ChatColor.BOLD + (p.getName())) + "\n"
					+ " " + "\n"						
					+ kill + ": " + ChatColor.GREEN + kills + "\n"
					+ " " + "\n"
					+ death + ": " + ChatColor.GREEN  + deaths  + "\n"					
					+ " " + "\n"
					+ ChatColor.YELLOW + "---------------------------------------------------"));				
			
		} else {
		
			sqlite.getConnection().close();
			sqlite.setConnection(null);
		}
	} catch (Exception erro) {
		erro.printStackTrace();
	}
}

public void StatsOther(String table, String TUuid, Player p, Player t) {
	try {
		
		String cmd = ("SELECT kills,deaths FROM " + table + " WHERE  uuid = ?");
		sqlite.sqLiteconnection();			
		
		PreparedStatement s = sqlite.getConnection().prepareStatement(cmd);
		s.setString(1, TUuid);
		ResultSet rs = s.executeQuery();
		 
		if (rs.next()) {				
			int deaths = rs.getInt("deaths");
			int kills = rs.getInt("kills");						
			sqlite.getConnection().close();
			sqlite.setConnection(null);
			
			String kdstatus = plugin.cm.getConfigMsgs().getString("Messages.Stats Of");
			String kill = plugin.cm.getConfigMsgs().getString("Messages.Kill");
			String death = plugin.cm.getConfigMsgs().getString("Messages.Death");
			p.sendMessage(ChatColor.YELLOW + "---------------------------------------------------\n"
					+ ChatColor.translateAlternateColorCodes('&', kdstatus + " " +  (ChatColor.BOLD + (t.getName())) + "\n"
					+ " " + "\n"						
					+ kill + ": " + ChatColor.GREEN + kills + "\n"
					+ " " + "\n"
					+ death + ": " + ChatColor.GREEN  + deaths  + "\n"					
					+ " " + "\n"
					+ ChatColor.YELLOW + "---------------------------------------------------"));	
			
			
		} 	else {
			
			sqlite.getConnection().close();
			sqlite.setConnection(null);
			}
		} catch (Exception erro) {
			erro.printStackTrace();
		}
}
	
	
}


