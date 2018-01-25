package br.com.explorecraft.ec_kdstats.storage.MySQL;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import br.com.explorecraft.ec_kdstats.EC_KDStats;


public class MySQL {
	
	private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);	
	
	private Connection connection;
	
	public String host, database, username, password,table;
	
	public int port;
	
	
	
	public void openConnection() {
		
		host = plugin.getConfig().getString("MySQL.hostname");
		database = plugin.getConfig().getString("MySQL.database");
		username = plugin.getConfig().getString("MySQL.username");
		password = plugin.getConfig().getString("MySQL.password");		
		port = plugin.getConfig().getInt("MySQL.port");
		
		try {
					
			synchronized (this) {
				if(connection !=null && connection.isClosed() ) {
					
					return;			
				}
				Class.forName("com.mysql.jdbc.Driver");
				setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false",
						this.username, this.password));
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
			}			
			
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL NOT CONNECTED");
			e.printStackTrace();// TODO: handle exception
		} catch (ClassNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL NOT CONNECTED");
			e.printStackTrace();// TODO: handle exception
		}
		
	}
		


	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
