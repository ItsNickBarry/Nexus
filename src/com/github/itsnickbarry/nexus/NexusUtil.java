package com.github.itsnickbarry.nexus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class NexusUtil {
    
    /*
     * Configuration information
     */
    static double powerLevelFactor;
    static int powerLevelMin; //it's probably best if this is not configurable
    static int powerPointsBase; //number of points granted to a new Nexus
    static int powerPointsMin; //the number of points at which a Nexus is destroyed; 0 < minPowerPoints < basePowerPoints
    static double spreadLevelFactor; //how effective spreadPoints are as spreadPoints approaches 0; 0 <= spreadModificationFactor <= 1
    static double spreadLevelVariability; //this represents the possible deviation from normalizedSpread; 0 <= spreadVariability <= 1
    
    //half-life, in days, of power and spread points; must be > 0 (or we can let 0 mean no decay)
    static double powerPointsHalfLife;
    static double spreadPointsHalfLife; 
    
    static boolean useSpheres;
    /*
     * 
     */

    
    static AtomicInteger nexusCurrentId = new AtomicInteger();
    static AtomicInteger groupCurrentId = new AtomicInteger();

    static Set<NexusOwner> nexusOwners = new HashSet<NexusOwner>();
    
    static List<Nexus> allNexus = new ArrayList<Nexus>(); // we might not even need this list; just use one of the sets

    static Set<Nexus> xmax = new TreeSet<Nexus>(new NexusComparator.XMax()); // sorted by max x
    static Set<Nexus> xmin = new TreeSet<Nexus>(new NexusComparator.XMin()); // sorted by min x
    static Set<Nexus> zmax = new TreeSet<Nexus>(new NexusComparator.ZMax()); // sorted by max z
    static Set<Nexus> zmin = new TreeSet<Nexus>(new NexusComparator.ZMin()); // sorted by min z
    
    public static void addNexusOwner(NexusOwner owner) {
    	nexusOwners.add(owner);
    }
    
    public static NexusPlayer getNexusPlayer(UUID playerUID) {
    	for (NexusOwner nexusOwner : nexusOwners) {
    		if (nexusOwner.equals(playerUID))
    			return (NexusPlayer) nexusOwner;
    	}
    	return null;
    }
    
    public static void addNexus(Nexus nexus) {
        allNexus.add(nexus);
        refreshSets();
    }

    public static Nexus determineBlockOwner(Block block) {

        Nexus point = new Nexus(block, null, false);

        Set<Nexus> candidates = new TreeSet<Nexus>(new NexusComparator.XMax());

        candidates.addAll(((TreeSet<Nexus>) xmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) xmin).headSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmin).headSet(point, true));

        double bestPower = powerLevelMin;
        Nexus bestNexus = null;

        for (Nexus n : candidates) {
        	//TODO maybe find a better way to check for world
        	if (point.getWorldUID() != n.getWorldUID())
        		continue;
            double power = n.powerAt(block);
            if (power > bestPower) {
                bestPower = power;
                bestNexus = n;
            }
        }

        return bestNexus;
    }
    
//    public static Nexus determineBlockOwner2(Block block){
//
//        double bestPower = minPower;
//        Nexus bestNexus = null;
//
//        for (Nexus n : allNexus) {
//            double power = n.powerAt(block);
//            if (power > bestPower) {
//                bestPower = power;
//                bestNexus = n;
//            }
//        }
//
//        return bestNexus;
//    }
    
    public static void loadConfig() {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("Nexus").getConfig();
        
        powerLevelFactor = config.getDouble("powerLevelFactor");
        powerLevelMin = config.getInt("powerLevelMin");
        powerPointsBase = config.getInt("powerPointsBase");
        powerPointsMin = config.getInt("powerPointsMin");
        spreadLevelFactor = config.getDouble("spreadLevelFactor");
        spreadLevelVariability = config.getDouble("spreadLevelVariability");
        powerPointsHalfLife = config.getDouble("powerPointsHalfLife");
        spreadPointsHalfLife = config.getDouble("spreadPointsHalfLife");
        useSpheres = config.getBoolean("useSpheres");
        
        //TODO check that all values are within appropriate ranges and, if they're not, revert to defaults
    }

    public static void refreshSets() {
        // do this onEnable, whenever Nexus points are scheduled to be assigned, and whenever a Nexus is created or destroyed

        List<Nexus> decayedNexus = new ArrayList<Nexus>();
        
        for (Nexus n : allNexus) {
            if (!n.update()) {
                decayedNexus.add(n);
            }
        }
        
        allNexus.removeAll(decayedNexus);

        xmax.addAll(allNexus);
        xmin.addAll(allNexus);
        zmax.addAll(allNexus);
        zmin.addAll(allNexus);
    }
}