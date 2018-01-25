package br.com.explorecraft.ec_kdstats.storage.MySQL;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import br.com.explorecraft.ec_kdstats.EC_KDStats;


public class SQLManager {
	private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);
	
	public MySQL mysql= new MySQL();
	
	
	
	public void createTable(String table, String columns) {
		try {
			String cmd = ("CREATE TABLE IF NOT EXISTS " + table + "(" + columns + ");");
			mysql.openConnection();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
			DatabaseMetaData metadados =mysql.getConnection().getMetaData();
			ResultSet rs = metadados.getTables(null, null, table, null);
			if (rs.next()) {				
				mysql.getConnection().close();
				mysql.setConnection(null);
			} else {
				PreparedStatement st = mysql.getConnection().prepareStatement(cmd);
				st.executeUpdate();
				mysql.getConnection().close();
				mysql.setConnection(null);
				Bukkit.getConsoleSender().sendMessage("§a[EC.MySql]The StatsPlayer SQL table was created!");
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[EC.MySql]The StatsPlayer table could not created!");
			e.printStackTrace();
		}
	}
	
	public void insertNewPlayer(String table, String uuid, String pName) {
		
		try {
			String cmd = ("SELECT * FROM " + table + " WHERE  uuid = ?");						
			
			mysql.openConnection();
			PreparedStatement s = mysql.getConnection().prepareStatement(cmd);
			s.setString(1, uuid);
			ResultSet rs = s.executeQuery();
			
			if (!rs.next()) {
				String insert = ("INSERT INTO " + table + "(uuid, name, kills, deaths) VALUES (?, ?, ?, ?)");
				PreparedStatement st = mysql.getConnection().prepareStatement(insert);
				st.setString(1, uuid);
				st.setString(2, pName);
				st.setInt(3, 0);
				st.setInt(4, 0);
				st.executeUpdate();
				mysql.getConnection().close();
				mysql.setConnection(null);
			} else {
			
				mysql.getConnection().close();
				mysql.setConnection(null);}
			
		} catch (Exception erro) {
			erro.printStackTrace();
		}		
		
	}
	
	public void updateKills(String table, String kUuid) {
		
		try {
			String cmd = ("SELECT kills FROM " + table + " WHERE  uuid = ?");
			mysql.openConnection();			
			PreparedStatement s = mysql.getConnection().prepareStatement(cmd);
			s.setString(1, kUuid);
			ResultSet rs = s.executeQuery();
			 
			if (rs.next()) {				
				int kills = rs.getInt("kills");				
				kills++;				
				String cmd2 = ("UPDATE " + table + " SET kills = ? WHERE  uuid = ?");
				PreparedStatement s2 = mysql.getConnection().prepareStatement(cmd2);
				s2.setInt(1, kills);
				s2.setString(2, kUuid);
				s2.executeUpdate();				  
				mysql.getConnection().close();
				mysql.setConnection(null);
			} 	else {		
				mysql.getConnection().close();
				mysql.setConnection(null);}
			
		} catch (Exception erro) {
			erro.printStackTrace();
		}
	}
	
	public void updateDeaths(String table, String pUuid) {
		
		try {
			String cmd = ("SELECT deaths FROM " + table + " WHERE  uuid = ?");
			mysql.openConnection();	
			
			PreparedStatement s = mysql.getConnection().prepareStatement(cmd);
			s.setString(1, pUuid);
			ResultSet rs = s.executeQuery();
			 
			if (rs.next()) {				
				int deaths = rs.getInt("deaths");				
				deaths++;				
				String cmd2 = ("UPDATE " + table + " SET deaths = ? WHERE  uuid = ?");
				PreparedStatement s2 = mysql.getConnection().prepareStatement(cmd2);
				s2.setInt(1, deaths);
				s2.setString(2, pUuid);
				s2.executeUpdate();
				mysql.getConnection().close();
				mysql.setConnection(null);
			} else	{		
				mysql.getConnection().close();
				mysql.setConnection(null);
			}
			
		} catch (Exception erro) {
			erro.printStackTrace();
		}	
		
	}
	
	public void YourStats(String table, String Uuid, Player p) {
		
		try {
			String cmd = ("SELECT kills,deaths FROM " + table + " WHERE  uuid = ?");
			mysql.openConnection();		
			
			PreparedStatement s = mysql.getConnection().prepareStatement(cmd);
			s.setString(1, Uuid);
			ResultSet rs = s.executeQuery();
			 
			if (rs.next()) {				
				int deaths = rs.getInt("deaths");
				int kills = rs.getInt("kills");						
				mysql.getConnection().close();
				mysql.setConnection(null);
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
				
				//return true;
			} else {
			
				mysql.getConnection().close();
				mysql.setConnection(null);
			}
		} catch (Exception erro) {
			erro.printStackTrace();
		}
	}
	
	public void StatsOther(String table, String TUuid, Player p, Player t) {
		try {
			
			String cmd = ("SELECT kills,deaths FROM " + table + " WHERE  uuid = ?");
			mysql.openConnection();			
			
			PreparedStatement s = mysql.getConnection().prepareStatement(cmd);
			s.setString(1, TUuid);
			ResultSet rs = s.executeQuery();
			 
			if (rs.next()) {				
				int deaths = rs.getInt("deaths");
				int kills = rs.getInt("kills");						
				mysql.getConnection().close();
				mysql.setConnection(null);
				
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
				
				mysql.getConnection().close();
				mysql.setConnection(null);
				}
			} catch (Exception erro) {
				erro.printStackTrace();
			}
	}

}
