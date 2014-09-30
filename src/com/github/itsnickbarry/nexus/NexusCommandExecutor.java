package com.github.itsnickbarry.nexus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NexusCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {

        /*
         * Group commands: ...
         * 
         * show borders (of currently occupied nexus)
         */
        
        if (args[0].equalsIgnoreCase("count")){
            sender.sendMessage("Number of Nexus: " + NexusUtil.nexusCurrentId);
        }

        return true;
    }
}
