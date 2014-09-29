package com.github.itsnickbarry.nexus;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

/*
 * Bugs:
 *  #1:
 *      Description:   
 *          Nexus was placed but blocks broken right next to it registered as unknown
 *      Cause:
 *          Unknown
 *  #2:
 *      Description:
 *          When 2 Nexus are closeby, the first placed Nexus overpowers the second by too much
 *      Cause:
 *          Unknown
 *      
 *  
 */
public class NexusListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        long start = System.currentTimeMillis();
        Block block = e.getBlock();
        Nexus owner = NexusUtil.determineBlockOwner(block);
        if (owner != null) {
            e.getPlayer().sendMessage(String.format("Owned by %d", owner.getId()));
        } else {
            e.getPlayer().sendMessage("Unowned");
        }
        System.out.println(String.format("Nexus break took %d ms", System.currentTimeMillis() - start));
    }
    
    @EventHandler
    public void onNexusPlace(BlockPlaceEvent e) {
        long start = System.currentTimeMillis();
        Block block = e.getBlock();
        ItemStack inHand = e.getItemInHand();
        if (inHand != null && inHand.getItemMeta() != null && inHand.getItemMeta().getDisplayName() != null && inHand.getItemMeta().getDisplayName().equalsIgnoreCase("Nexus")) {
            Nexus newNexus = new Nexus(block, 100, 10, true);
            //nexus.add(newNexus);
            NexusUtil.addNexus(newNexus);
            //e.getPlayer().sendMessage("Added Nexus " + newNexus.getId());
        }
        System.out.println(String.format("Nexus place took %d ms", System.currentTimeMillis() - start));
    }
    
    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        long start = System.currentTimeMillis();
        for (Block block : e.blockList()) {
            Nexus owner = NexusUtil.determineBlockOwner(block);
            /*
            if (owner != null) {
               System.out.println(String.format("Owned by %d", owner.getId()));
            } else {
                System.out.println("Unowned");
            }
            */
        }
        System.out.println(String.format("Explosion took %d ms", System.currentTimeMillis() - start));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // display information when player crosses border
    }
}
