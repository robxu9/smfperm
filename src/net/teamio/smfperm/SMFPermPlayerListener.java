package net.teamio.smfperm;

import net.teamio.smfperm.SMFPerm;

import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class SMFPermPlayerListener extends PlayerListener{

	public SMFPerm plugin;
	 
	public SMFPermPlayerListener(SMFPerm instance) {
	    plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event){
		
	}
	
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event){

	}
	
}
