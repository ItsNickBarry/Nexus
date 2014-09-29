package com.github.itsnickbarry.nexus;

import org.bukkit.plugin.java.JavaPlugin;

public class NexusMain extends JavaPlugin {

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
