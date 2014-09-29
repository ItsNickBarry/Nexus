package com.github.itsnickbarry.nexus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.block.Block;

public class NexusUtil {

    static AtomicInteger nexusCurrentId = new AtomicInteger();
    static int minPower = 1;
    static boolean useSpheres = true;

    static Map<Nexus, NexusOwner> allNexus = new HashMap<Nexus, NexusOwner>();

    static Set<Nexus> xmax = new TreeSet<Nexus>(new NexusComparator.XMax()); // sorted by max x
    static Set<Nexus> xmin = new TreeSet<Nexus>(new NexusComparator.XMin()); // sorted by min x
//    static Set<Nexus> ymax = new TreeSet<Nexus>(new NexusComparator.YMax()); // sorted by max y
//    static Set<Nexus> ymin = new TreeSet<Nexus>(new NexusComparator.YMin()); // sorted by min y
    static Set<Nexus> zmax = new TreeSet<Nexus>(new NexusComparator.ZMax()); // sorted by max z
    static Set<Nexus> zmin = new TreeSet<Nexus>(new NexusComparator.ZMin()); // sorted by min z

    public static Nexus determineBlockOwner(Block block) {

        Nexus point = new Nexus(block, 1, 1, false);

        Set<Nexus> candidates = new TreeSet<Nexus>(new NexusComparator.XMax());

        candidates.addAll(((TreeSet<Nexus>) xmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) xmin).headSet(point, true));
        // System.out.println("Within X range: " + candidates.size());
//        candidates.retainAll(((TreeSet<Nexus>) ymax).tailSet(point, true));
//        candidates.retainAll(((TreeSet<Nexus>) ymin).headSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmin).headSet(point, true));
        // System.out.println("Within Z range: " + candidates.size());

        double bestPower = 0;
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

    public static double formula(double distance, double power, double spread) {
        return (power / spread)
                * Math.exp(-1
                        * ((Math.pow(distance, 2)) / (2 * Math.pow(spread, 2))));
    }

    public static void refreshSets() {
        // do this onEnable, whenever Nexus points are assigned, and whenever a new Nexus is created

        for (Nexus n : allNexus.keySet()) {
            n.calculateEffectiveRadius();
        }

        xmax.addAll(allNexus.keySet());
        xmin.addAll(allNexus.keySet());
//        ymax.addAll(allNexus.keySet());
//        ymin.addAll(allNexus.keySet());
        zmax.addAll(allNexus.keySet());
        zmin.addAll(allNexus.keySet());
    }
    
    public static void addNexus(Nexus nexus, NexusOwner owner) {
        allNexus.put(nexus, owner);
        refreshSets();
    }
}