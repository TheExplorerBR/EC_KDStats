package br.com.explorecraft.ec_kdstats.storage.SQLite;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


import br.com.explorecraft.ec_kdstats.EC_KDStats;

public class SQLite{
	
  private EC_KDStats plugin = EC_KDStats.getPlugin(EC_KDStats.class);
  private Connection connection;
  private File file;  
  
  public void sqLiteconnection() {	  
	  
    file = new File(plugin.getDataFolder(), "EC_KDStats.db");
    
    try
    {
      if (!file.exists()) {
        file.createNewFile();
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + file);
     // stmt = connection.createStatement();
      
    }
    catch (Exception e)
    {
    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQLite  NOT CONNECTED");
      e.printStackTrace();
    }
  }
  
    
  /*public void update(PreparedStatement sql)
  {
    try
    {
      sql.executeUpdate();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Erro ao Executar SQLite");
    }
  }
  
  public PreparedStatement prepareStatement(String sql)
  {
    try
    {
      return this.connection.prepareStatement(sql);
    }
    catch (SQLException e)
    {
      System.out.print("SQLiteException: " + e.getCause());
    }
    return null;
  }*/
  
  public void setConnection(Connection connection) {
		this.connection = connection;
	}
  
  public Connection getConnection() {
		return connection;
	}
  
  
}

