package com.github.itsnickbarry.nexus;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public class NexusMain extends JavaPlugin {

    /*
    public static void main(String[] args) {

        Random r = new Random();
        long time;

        int nexuscount = 1000; // number of Nexus to be created
        int operationcount = 1; // number of block breaks to be tested
        int testradius = 1000; // x and z

        // create the Nexus objects
        time = System.currentTimeMillis();
        for (int i = 0; i < nexuscount; i++) {
            Nexus n = new Nexus(r.nextInt(5000) + 1, r.nextInt(250) + 1, r.nextInt(testradius), r.nextInt(128), r.nextInt(testradius));
            NexusUtil.allNexus.put(n, null);
        }
        System.out.println("Nexus created in "
                + (System.currentTimeMillis() - time) + " ms.");

        // fill the TreeSets with Nexus objects
        time = System.currentTimeMillis();
        NexusUtil.refreshSets();
        System.out.println("Sets refreshed in "
                + (System.currentTimeMillis() - time) + " ms.");

        // generate coordinates to test
        int testx = r.nextInt(testradius);
        int testy = r.nextInt(128);
        int testz = r.nextInt(testradius);

        time = System.currentTimeMillis();
        for (int i = 0; i < operationcount; i++) {
            NexusUtil.determineBlockOwner(testx, testy, testz);
        }
        System.out.println("Blocks tested in "
                + (System.currentTimeMillis() - time) + " ms");
    }
    */

    @Override
    public void onEnable() {

        getCommand("nexus").setExecutor(new NexusCommandExecutor());
        getServer().getPluginManager()
                .registerEvents(new NexusListener(), this);

        /*
         * load data:
         * 
         * NexusUtil.allNexus NexusUtil.minPower NexusUtil.useSpheres
         */

        NexusUtil.refreshSets();
    }

    @Override
    public void onDisable() {

    }
}
