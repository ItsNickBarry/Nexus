package com.github.itsnickbarry.nexus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class NexusListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		//display information when player crosses border
	}
}
