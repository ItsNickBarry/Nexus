package com.github.itsnickbarry.nexus;

import java.util.Comparator;

public abstract class NexusComparator {
    
    public static class XMax implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getX() + n1.getEffectiveRadius()) - (n2.getX() + n2.getEffectiveRadius());
        }
        
    }
    
    public static class XMin implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getX() - n1.getEffectiveRadius()) - (n2.getX() - n2.getEffectiveRadius());
        }
        
    }
    
    public static class YMax implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getY() + n1.getEffectiveRadius()) - (n2.getY() + n2.getEffectiveRadius());
        }
        
    }
    
    public static class YMin implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getY() - n1.getEffectiveRadius()) - (n2.getY() - n2.getEffectiveRadius());
        }
        
    }
    
    public static class ZMax implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getZ() + n1.getEffectiveRadius()) - (n2.getZ() + n2.getEffectiveRadius());
        }
        
    }
    
    public static class ZMin implements Comparator<Nexus> {
        
        @Override
        public int compare(Nexus n1, Nexus n2) {
            return (n1.getZ() - n1.getEffectiveRadius()) - (n2.getZ() - n2.getEffectiveRadius());
        }
        
    }
    
}
