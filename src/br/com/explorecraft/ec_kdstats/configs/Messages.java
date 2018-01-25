package br.com.explorecraft.ec_kdstats.configs;

import br.com.explorecraft.ec_kdstats.EC_KDStats;

public class Messages {
	
	EC_KDStats plugin;
	
	public Messages(EC_KDStats plugin) {
		this.plugin = plugin;
		
		plugin.cm.getConfigMsgs().options().header("=================================== Kill/Death Stats Messages.yml ===========================================\n"
				+ "\n"
				+ "Author: TheExplorerBR\n"
				+ "Here you Can Translate the Messages of this Plugin");
		
		if(!plugin.cm.getConfigMsgs().contains("Messages")){
			plugin.cm.getConfigMsgs().set("Messages.Only Players", "&cOnly Player can execute this command!" );
			plugin.cm.getConfigMsgs().set("Messages.Player Not online", "&cis not online!" );
			plugin.cm.getConfigMsgs().set("Messages.Stats Of", "&eKill/Death Stats of" );
			plugin.cm.getConfigMsgs().set("Messages.Killed by Player", "&6You were killed by" );
			plugin.cm.getConfigMsgs().set("Messages.Kill Player", "&6You killed the player" );
			plugin.cm.getConfigMsgs().set("Messages.Kill", "&6Kills" );
			plugin.cm.getConfigMsgs().set("Messages.Death", "&6Deaths" );
			plugin.cm.saveConfigMsgs();
		}
	}

}
