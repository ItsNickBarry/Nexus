package com.github.itsnickbarry.nexus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class NexusUtil {
    
    /*
     * Configuration information
     */
    static double powerLevelFactor = 5000;
    static int powerLevelMin = 1; //it's probably best if this is not configurable
    static int powerPointsBase = 100; //number of points granted to a new Nexus
    static int powerPointsMin = 75; //the number of points at which a Nexus is destroyed; 0 < minPowerPoints < basePowerPoints
    static double spreadLevelFactor = .01; //how effective spreadPoints are as spreadPoints approaches 0; 0 <= spreadModificationFactor <= 1
    static double spreadLevelVariability = 1; //TODO this represents the possible deviation from normalizedSpread; 0 <= spreadVariability <= 1
    
    //half-life, in days, of power and spread points; must be > 0 (or we can let 0 mean no decay)
    static double powerHalfLife = 10;
    static double spreadHalfLife = 20; 
    
    static boolean useSpheres = true;
    /*
     * 
     */

    
    static AtomicInteger nexusCurrentId = new AtomicInteger();
    static AtomicInteger groupCurrentId = new AtomicInteger();

    static List<Nexus> allNexus = new ArrayList<Nexus>(); // we might not even need this list; just use one of the sets

    static Set<Nexus> xmax = new TreeSet<Nexus>(new NexusComparator.XMax()); // sorted by max x
    static Set<Nexus> xmin = new TreeSet<Nexus>(new NexusComparator.XMin()); // sorted by min x
    static Set<Nexus> zmax = new TreeSet<Nexus>(new NexusComparator.ZMax()); // sorted by max z
    static Set<Nexus> zmin = new TreeSet<Nexus>(new NexusComparator.ZMin()); // sorted by min z
    
    public static void addNexus(Nexus nexus) {
        allNexus.add(nexus);
        refreshSets();
    }

    public static Nexus determineBlockOwner(Block block) {

        Nexus point = new Nexus(block, false);

        Set<Nexus> candidates = new TreeSet<Nexus>(new NexusComparator.XMax());

        candidates.addAll(((TreeSet<Nexus>) xmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) xmin).headSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmin).headSet(point, true));

        double bestPower = powerLevelMin;
        Nexus bestNexus = null;

        for (Nexus n : candidates) {
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
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("FastRoads").getConfig();
        
        powerLevelFactor = config.getDouble("powerLevelFactor");
        powerLevelMin = config.getInt("powerLevelMin");
        powerPointsBase = config.getInt("powerPointsBase");
        powerPointsMin = config.getInt("powerPointsMin");
        spreadLevelFactor = config.getDouble("spreadLevelFactor");
        spreadLevelVariability = config.getDouble("spreadLevelVariability");
        powerHalfLife = config.getDouble("powerHalfLife");
        spreadHalfLife = config.getDouble("spreadHalfLife");
        useSpheres = config.getBoolean("useSpheres");
    }

    public static void refreshSets() {
        // do this onEnable, whenever Nexus points are scheduled to be assigned, and whenever a Nexus is created or destroyed

        for (Nexus n : allNexus) {
            n.update();
        }

        xmax.addAll(allNexus);
        xmin.addAll(allNexus);
        zmax.addAll(allNexus);
        zmin.addAll(allNexus);
    }
}