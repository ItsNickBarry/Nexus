package com.github.itsnickbarry.nexus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.block.Block;

public class NexusUtil {

    static AtomicInteger nexusCurrentId = new AtomicInteger();
    
    //configuration information
    static final int minPower = 1; //it's probably best if this is not configurable
    static int basePowerPoints = 100; //number of points granted to a new Nexus
//    static int baseSpread = 10; //the amount of spread at 0 spreadPoints TODO this doens't make sense
    static double powerFactor = 5000;
    static double spreadModificationFactor = .01; //how effective spreadPoints are as spreadPoints approaches 0; 0 <= spreadModificationFactor <= 1
    static double spreadNormalizationFactor = 2.5; //how quickly spread increases with power; AT LEAST 0 < spreadNormalizationFactor
    static double spreadVariability = 1; //TODO this represents the possible deviation from normalizedSpread; 0 <= spreadVariability <= 1
    
    //half-life, in days, of power and spread points
    static double powerHalfLife = 10;
    static double spreadHalfLife = 20; 
    
    static boolean useSpheres = true;

    static List<Nexus> allNexus = new ArrayList<Nexus>(); // we might not even need this list

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

        double bestPower = minPower;
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