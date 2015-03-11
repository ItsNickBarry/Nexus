package com.github.itsnickbarry.nexus;

import java.util.Set;

import org.bukkit.Bukkit;
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
        	sender.sendMessage("influenceMin: " + NexusUtil.influenceMin);
            sender.sendMessage("powerLevelFactor: " + NexusUtil.powerLevelFactor);
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
            sender.sendMessage(n.getPowerPoints() < NexusUtil.powerPointsMin ? "Decayed" : "Power points of Nexus that controls current location: " + n.getPowerPoints());
        } else if (sender instanceof Player && args[0].equalsIgnoreCase("group") && args[1].equalsIgnoreCase("create") && args[2] != null) {
            Player p = (Player) sender;
            String tag = args[2];
            NexusGroup group = new NexusGroup(p, tag);
            NexusUtil.addNexusGroup(group);
        } else if (args[0].equalsIgnoreCase("groups")) {
            Set<NexusGroup> groups = NexusUtil.getNexusGroups();
            for (NexusGroup group : groups) {
                sender.sendMessage(String.format("Tag: %s, Owner: %s", group.getTag(), Bukkit.getPlayer(group.getOwner()).getName()));
            }
        }

        return true;
    }
}
