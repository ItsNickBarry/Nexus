package com.github.itsnickbarry.nexus;

import java.util.Comparator;

public abstract class NexusComparator {
    /*
     * Note that the return statement for Max comparators is
     * not the same as the return statement for Min comparators
     */
    public static class XMax implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            int difference = (n1.getX() + n1.getRadius()) - (n2.getX() + n2.getRadius());
            return (difference != 0 ? difference : n1.getId() - n2.getId());
        }
        
    }
    
    public static class XMin implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            int difference = (n1.getX() - n1.getRadius()) - (n2.getX() - n2.getRadius());
            return (difference != 0 ? difference : n2.getId() - n1.getId());
        }
        
    }
    
    public static class ZMax implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            int difference = (n1.getZ() + n1.getRadius()) - (n2.getZ() + n2.getRadius());
            return (difference != 0 ? difference : n1.getId() - n2.getId());
        }
        
    }
    
    public static class ZMin implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            int difference = (n1.getZ() - n1.getRadius()) - (n2.getZ() - n2.getRadius());
            return (difference != 0 ? difference : n2.getId() - n1.getId());
        }
        
    }
    
}
