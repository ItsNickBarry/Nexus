package com.github.itsnickbarry.nexus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class NexusGroup {
	
	public enum Role {
		
		OWNER, MODERATOR, REG_USER, NONE;
		
	}
	
	private String tag;
    
	private Map<UUID, Role> members = new HashMap<UUID, Role>();
	
	private Set<NexusGroup> memberGroups = new HashSet<NexusGroup>();
	//private Set<Integer> memberGroups = new HashSet<Integer>();
	
	// When the first Nexus is placed, it must be owned by a singular person
	// That person can then form a Group
    public NexusGroup(Player owner, String tag) {
        // TODO setTag method
        this.tag = tag;
        this.setOwner(owner.getUniqueId());
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public Set<NexusGroup> getMemberGroups() {
        return memberGroups;
    }
    /*
    public Set<NexusGroup> getMemberGroups() {
        Set<NexusGroup> matches = new HashSet<NexusGroup>();
        for (int memberGroupId : memberGroups) {
            matches.add((NexusGroup) NexusUtil.getNexusOwner(memberGroupId));
        }
        return matches;
    }
    */
    
    public void addMemberGroup(NexusGroup group) {
        memberGroups.add(group);
    }
    
    public void removeMemberGroup(NexusGroup group) {
        memberGroups.remove(group);
    }
    
    /*
    @Override
    public boolean equals(Object object) {
    	if (this == object)
    		return true;
    	if (object instanceof NexusGroup) {
    		NexusGroup nexusGroup = (NexusGroup) object;
    		// TODO How does this affect hashCode?
    		return (this.getId() == nexusGroup.getId());
    	}
    	return false;
    }
    
    // How well does this work with HashSet?
    @Override
    public int hashCode() {
    	return Objects.hash(this.getId());
    }
    */
    
    public Role getRole(UUID playerUniqueId) {
    	if (this.members.containsKey(playerUniqueId)) {
    		return this.members.get(playerUniqueId);
    	} else {
    	    for (NexusGroup group : this.getMemberGroups()) {
    	        if (group.getRole(playerUniqueId) != Role.NONE) {
    	            return group.getRole(playerUniqueId);
    	        }
    	    }
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
