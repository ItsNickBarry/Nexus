package com.github.itsnickbarry.nexus;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class NexusListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        //long start = System.currentTimeMillis();
        Block b = e.getBlock();
        Player p = e.getPlayer();
        Nexus n = NexusUtil.determineBlockOwner2(b);
        if (n != null) {
            if (!n.allowsPlayerBlockEdit(p)) {
                e.setCancelled(true);
                p.sendMessage(String.format("Cancelled, Owned by %d", n.getId()));
            } else {
                p.sendMessage(String.format("Success, Owned by %d", n.getId()));
            }
        } else {
            p.sendMessage("Unowned");
        }
        //System.out.println(System.currentTimeMillis() - start);
        //if (block is a nexus && player has authorization(from the nexus, or from the nexus that controls the block) and Bukkit perms to destroy a nexus)
        //    destroy nexus
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        Nexus n = NexusUtil.determineBlockOwner2(b);
        if (n != null) {
            if (!n.allowsPlayerBlockEdit(p)) {
                e.setCancelled(true);
                p.sendMessage(String.format("Cancelled, Owned by %d", n.getId()));
            } else {
                p.sendMessage(String.format("Success, Owned by %d", n.getId()));
            }
        } else {
            p.sendMessage("Unowned");
        }
        
        //nexus creation
        ItemStack inHand = e.getItemInHand();
        if (inHand != null && inHand.getItemMeta() != null && inHand.getItemMeta().getDisplayName() != null && inHand.getItemMeta().getDisplayName().equalsIgnoreCase("Nexus")) {
            //NexusPlayer nexusPlayer = NexusUtil.absoluteGetNexusPlayer(p.getUniqueId());
            /*
            NexusPlayer nexusPlayer = NexusUtil.getNexusPlayer(e.getPlayer().getUniqueId());
            if (nexusPlayer == null) {
                //System.out.println("nexusPlayer == null");
            	nexusPlayer = new NexusPlayer(e.getPlayer());
            	NexusUtil.addNexusOwner(nexusPlayer);
            }
            */
        	Nexus newNexus = new Nexus(b, p, true);
            NexusUtil.addNexus2(newNexus);
            e.getPlayer().sendMessage("Added Nexus " + newNexus.getId());
        }
    }
    
    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        long start = System.currentTimeMillis();
        for (Block block : e.blockList()) {
            Nexus owner = NexusUtil.determineBlockOwner2(block);
            
            /*
            if (owner != null) {
               System.out.println(String.format("Exploded block owned by %d", owner.getId()));
            } else {
                System.out.println("Exploded block unowned");
            }
            */
            
        }
        System.out.println(String.format("Explosion took %d ms", System.currentTimeMillis() - start));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // display information when player crosses border
        // this might take too much cpu, as it does the same calculations as onBlockBreak, but is called about 10 times every time a player moves 1 block
    }
}
