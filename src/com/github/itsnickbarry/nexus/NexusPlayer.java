package com.github.itsnickbarry.nexus;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.entity.Player;

public class NexusPlayer extends NexusOwner {
    
	private final UUID playerUID;
	
    public NexusPlayer(Player player) {
        this.playerUID = player.getUniqueId();
    }
    
    public NexusPlayer(UUID playerUID) {
    	this.playerUID = playerUID;
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
    
    // How should the hashCode look like?
    @Override
    public int hashCode() {
    	return Objects.hash(this.playerUID);
    }
    
    public UUID getPlayerUID() {
    	return this.playerUID;
    }
}
