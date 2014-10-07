package com.github.itsnickbarry.nexus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NexusCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {

        /*
         * Group commands: ...
         * 
         * show borders (of currently occupied nexus)
         */
        
        if (args[0].equalsIgnoreCase("config")){
            sender.sendMessage("powerLevelFactor: " + NexusUtil.powerLevelFactor);
            sender.sendMessage("powerLevelMin: " + NexusUtil.powerLevelMin);
            sender.sendMessage("powerPointsBase: " + NexusUtil.powerPointsBase);
            sender.sendMessage("powerPointsMin: " + NexusUtil.powerPointsMin);
            sender.sendMessage("spreadLevelFactor: " + NexusUtil.spreadLevelFactor);
            sender.sendMessage("spreadLevelVariability: " + NexusUtil.spreadLevelVariability);
            sender.sendMessage("powerPointsHalfLife: " + NexusUtil.powerPointsHalfLife);
            sender.sendMessage("spreadPointsHalfLife: " + NexusUtil.spreadPointsHalfLife);
            sender.sendMessage("useSpheres: " + NexusUtil.useSpheres);
        } else if (args[0].equalsIgnoreCase("here")){
        	NexusUtil.refreshSets();
            Nexus n = NexusUtil.determineBlockOwner(((Player)sender).getLocation().getBlock());
            sender.sendMessage("Power points of Nexus that controls current location: " + n.getPowerPoints());
        }

        return true;
    }
}
