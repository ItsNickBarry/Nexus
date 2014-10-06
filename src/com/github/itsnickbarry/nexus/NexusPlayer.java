package com.github.itsnickbarry.nexus;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.entity.Player;


public class NexusPlayer implements NexusOwner {
    
    //long lastSeen;

    //List<Integer> memberships = new ArrayList<Integer>();
    
	private final UUID playerUID;
	
    public NexusPlayer(Player player) {
        this.playerUID = player.getUniqueId();
    }
    
    @Override
    public boolean equals(Object object) {
    	if (this == object)
    		return true;
    	if (object instanceof NexusPlayer) {
    		NexusPlayer nexusPlayer = (NexusPlayer) object;
    		return (this.playerUID == nexusPlayer.playerUID);
    	} else if (object instanceof Player) {
    		Player player = (Player) object;
    		return (this.playerUID == player.getUniqueId());
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(this.playerUID);
    }
    
    public UUID getUniqueId() {
    	return this.playerUID;
    }
}
