package com.github.itsnickbarry.nexus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class NexusGroup implements NexusOwner {
	
	public enum Role {
		
		OWNER, MODERATOR, REG_USER, NONE;
		
	}
	
	private final int id;
    
	private Map<UUID, Role> members = new HashMap<UUID, Role>();
	
    public NexusGroup() {
        this.id = NexusUtil.groupCurrentId.incrementAndGet();
    }
    
    public int getId() {
    	return this.id;
    }
    
    public Role getRole(UUID playerUniqueId) {
    	if (this.members.containsKey(playerUniqueId)) {
    		return this.members.get(playerUniqueId);
    	} else {
    		return Role.NONE;
    	}
    }
    
    /* There should only be one owner. */
    public UUID getOwner() {
    	Set<Entry<UUID, Role>> entrySet = this.members.entrySet();
    	for (Entry<UUID, Role> entry : entrySet) {
    		if (entry.getValue() == Role.OWNER) {
    			return entry.getKey();
    		}
    	}
    	return null;
    }
    
    public void setOwner(UUID newOwnerUID) {
    	if (this.members.isEmpty()) {
    		this.members.put(newOwnerUID, Role.OWNER);
    	} else {
    		Iterator<Entry<UUID, Role>> entryIterator = this.members.entrySet().iterator();
        	while (entryIterator.hasNext()) {
        		Entry<UUID, Role> entry = entryIterator.next();
        		if (entry.getValue() == Role.OWNER || entry.getKey() == newOwnerUID) {
        			entryIterator.remove();
        		}
        	}
        	this.members.put(newOwnerUID, Role.OWNER);
    	}
    }
    
    public Set<UUID> getModerators() {
    	Set<UUID> moderators = new HashSet<UUID>();
    	Set<Entry<UUID, Role>> entrySet = this.members.entrySet();
    	for (Entry<UUID, Role> entry : entrySet) {
    		if (entry.getValue() == Role.MODERATOR) {
    			moderators.add(entry.getKey());
    		}
    	}
    	return moderators;
    }
    
    public void setModerator(UUID newModeratorUID) {
    	Iterator<Entry<UUID, Role>> entryIterator = this.members.entrySet().iterator();
    	while (entryIterator.hasNext()) {
    		Entry<UUID, Role> entry = entryIterator.next();
    		if (entry.getKey() == newModeratorUID) {
    			entryIterator.remove();
    			break;
    		}
    	}
    	this.members.put(newModeratorUID, Role.MODERATOR);
    }
    
    public Set<UUID> getRegularUsers() {
    	Set<UUID> regularUsers = new HashSet<UUID>();
    	Set<Entry<UUID, Role>> entrySet = this.members.entrySet();
    	for (Entry<UUID, Role> entry : entrySet) {
    		if (entry.getValue() == Role.REG_USER) {
    			regularUsers.add(entry.getKey());
    		}
    	}
    	return regularUsers;
    }
    
    public void setRegularUser(UUID newRegularUserUID) {
    	Iterator<Entry<UUID, Role>> entryIterator = this.members.entrySet().iterator();
    	while (entryIterator.hasNext()) {
    		Entry<UUID, Role> entry = entryIterator.next();
    		if (entry.getKey() == newRegularUserUID) {
    			entryIterator.remove();
    			break;
    		}
    	}
    	this.members.put(newRegularUserUID, Role.REG_USER);
    }
}
