package com.github.itsnickbarry.nexus;

import java.util.ArrayList;
import java.util.List;

public class NexusPlayer implements NexusOwner {
    
    long lastSeen;

    List<Integer> memberships = new ArrayList<Integer>();
    
    public NexusPlayer() {
        
    }
}
