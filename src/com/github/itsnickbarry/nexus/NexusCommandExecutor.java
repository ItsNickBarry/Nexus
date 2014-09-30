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
        } else if (args[0].equalsIgnoreCase("info")){
            Nexus ref = null;
            for (Nexus n : NexusUtil.allNexus){
                if (n.getId() == Integer.parseInt(args[1])){
                    ref = n;
                }
            }
            sender.sendMessage("X: " + ref.getX() + "\nY: " + ref.getY() + "\nZ: " + ref.getZ() + "\nPower: " + ref.getPower() + "\nSpread: " + ref.getSpread() + "\nEffective Radius: " + ref.getRadius());
        }

        return true;
    }
}
