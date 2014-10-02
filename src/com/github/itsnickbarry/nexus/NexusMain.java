package com.github.itsnickbarry.nexus;

import org.bukkit.plugin.java.JavaPlugin;

public class NexusMain extends JavaPlugin {

    
    public static void main(String[] args) {
        
    }
    

    @Override
    public void onEnable() {

        getCommand("nexus").setExecutor(new NexusCommandExecutor());
        getServer().getPluginManager().registerEvents(new NexusListener(), this);
        
        NexusUtil.loadConfig();
        
        NexusUtil.refreshSets();
    }

    @Override
    public void onDisable() {

    }
}
