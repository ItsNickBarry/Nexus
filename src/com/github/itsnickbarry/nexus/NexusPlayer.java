package com.github.itsnickbarry.nexus;

import java.util.UUID;

import org.bukkit.entity.Player;


public class NexusPlayer implements NexusOwner {
    
    //long lastSeen;

    //List<Integer> memberships = new ArrayList<Integer>();
    
	private final UUID playerUID;
	
    public NexusPlayer(Player player) {
        this.playerUID = player.getUniqueId();
    }
    
    public UUID getUniqueId() {
    	return this.playerUID;
    }
}
