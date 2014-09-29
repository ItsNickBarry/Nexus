package com.github.itsnickbarry.nexus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.block.Block;

public class NexusUtil {

    static AtomicInteger nexusCurrentId = new AtomicInteger();
    static int minPower = 1;
    static boolean useSpheres = true;

    static List<Nexus> allNexus = new ArrayList<Nexus>();
    
    static Set<Nexus> xmax = new TreeSet<Nexus>(new NexusComparator.XMax()); // sorted by max x
    static Set<Nexus> xmin = new TreeSet<Nexus>(new NexusComparator.XMin()); // sorted by min x
    static Set<Nexus> ymax = new TreeSet<Nexus>(new NexusComparator.YMax()); // sorted by max y
    static Set<Nexus> ymin = new TreeSet<Nexus>(new NexusComparator.YMin()); // sorted by min y
    static Set<Nexus> zmax = new TreeSet<Nexus>(new NexusComparator.ZMax()); // sorted by max z
    static Set<Nexus> zmin = new TreeSet<Nexus>(new NexusComparator.ZMin()); // sorted by min z
    
    /*
    public static void main(String[] args) {

        Random r = new Random();
        long time;

        int nexuscount = 1000; // number of Nexus to be created
        int operationcount = 1000; // number of block breaks to be tested
        boolean method1 = true; // true = use my method, false = iterate through whole list

        // create the Nexus objects
        time = System.currentTimeMillis();
        for (int i = 0; i < nexuscount; i++) {
            Nexus n = new Nexus(r.nextInt(5000), r.nextInt(250), r.nextInt(10000), r.nextInt(128), r.nextInt(10000));
            allNexus.add(n);
        }
        System.out.println("Nexus created in " + (System.currentTimeMillis() - time) + " ms.");

        // fill the TreeSets with Nexus objects
        if (method1) {
            time = System.currentTimeMillis();
            refreshSets();
            System.out.println("Sets refreshed in " + (System.currentTimeMillis() - time) + " ms.");
        }

        // generate coordinates to test
        int testx = r.nextInt(10000);
        int testy = r.nextInt(128);
        int testz = r.nextInt(10000);

        time = System.currentTimeMillis();
        if (method1) {
            for (int i = 0; i < operationcount; i++) {
                determineBlockOwner(testx, testy, testz);
            }
        } else {
            for (int i = 0; i < operationcount; i++) {
                determineBlockOwner2(testx, testy, testz);
            }
        }
        System.out.println("Blocks tested in "
                + (System.currentTimeMillis() - time) + " ms");
    }
    */
    
    public static Nexus determineBlockOwner(Block block) {

        //Nexus point = new Nexus(1, 1, x, y, z); // used to compare against portions of the TreeSets
        Nexus point = new Nexus(block, 1, 1, false);

        Set<Nexus> candidates = new TreeSet<Nexus>(new NexusComparator.XMax());

        candidates.addAll(((TreeSet<Nexus>) xmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) xmin).headSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) ymax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) ymin).headSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmax).tailSet(point, true));
        candidates.retainAll(((TreeSet<Nexus>) zmin).headSet(point, true));

        double bestPower = 0;
        Nexus bestNexus = null;
        //int bestId = 0;

        for (Nexus n : candidates) {
            double power = n.powerAt(block);
            if (power > bestPower) {
                bestPower = power;
                bestNexus = n;
            }
            /*
            if (n.powerAt(x, y, z) > bestPower) {
                bestPower = n.powerAt(x, y, z);
                bestId = n.getId();
            }*/
        }

        return bestNexus;
    }

    public static Nexus determineBlockOwner2(Block block) {
        double bestPower = 0;
        Nexus bestNexus = null;

        for (Nexus n : allNexus) {
            double power = n.powerAt(block);
            if (power > bestPower) {
                bestPower = power;
                bestNexus = n;
            }
        }

        return bestNexus;
    }

    public static double formula(double distance, double power, double spread) {
        return (power / spread) * Math.exp(-1 * ((Math.pow(distance, 2)) / (2 * Math.pow(spread, 2))));
    }

    public static void refreshSets() {
        // do this onEnable, whenever Nexus points are assigned, and whenever a new Nexus is created

        for (Nexus n : allNexus) {
            n.calculateEffectiveRadius();
        }

        xmax.addAll(allNexus);
        xmin.addAll(allNexus);
        ymax.addAll(allNexus);
        ymin.addAll(allNexus);
        zmax.addAll(allNexus);
        zmin.addAll(allNexus);
    }
    
    public static void addNexus(Nexus nexus) {
        allNexus.add(nexus);
        refreshSets();
    }
}
/*
class NexusXMaxComparator implements Comparator<Nexus> {
    @Override
    public int compare(Nexus n1, Nexus n2) {
        return (n1.getX() + n1.getEffectiveRadius())
                - (n2.getX() + n2.getEffectiveRadius());
    }
}

class NexusXMinComparator implements Comparator<Nexus> {
    @Override
    public int compare(Nexus n1, Nexus n2) {
        return (n1.getX() - n1.getEffectiveRadius())
                - (n2.getX() - n2.getEffectiveRadius());
    }
}

class NexusZMaxComparator implements Comparator<Nexus> {
    @Override
    public int compare(Nexus n1, Nexus n2) {
        return (n1.getZ() + n1.getEffectiveRadius())
                - (n2.getZ() + n2.getEffectiveRadius());
    }
}

class NexusZMinComparator implements Comparator<Nexus> {
    @Override
    public int compare(Nexus n1, Nexus n2) {
        return (n1.getZ() - n1.getEffectiveRadius())
                - (n2.getZ() - n2.getEffectiveRadius());
    }
}
*/