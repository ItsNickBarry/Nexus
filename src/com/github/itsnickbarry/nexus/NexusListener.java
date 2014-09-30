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
        Block b = e.getBlock();
        Player p = e.getPlayer();
        Nexus n = NexusUtil.determineBlockOwner(b);
        if (n != null && !n.allowsPlayerBlockEdit(p)) {
            p.sendMessage(String.format("Owned by %d", n.getId()));
            e.setCancelled(true);
            return;
        } else {
            p.sendMessage("Unowned");
        }
        
        //if (block is a nexus && player has authorization and Bukkit perms to destroy a nexus)
        //    destroy nexus
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Nexus owner = NexusUtil.determineBlockOwner(block);
        if (owner != null && !owner.allowsPlayerBlockEdit(e.getPlayer())) {
            e.getPlayer().sendMessage(String.format("Owned by %d", owner.getId()));
            e.setCancelled(true);
            return;
        } else {
            e.getPlayer().sendMessage("Unowned");
        }
        
        //nexus creation
        ItemStack inHand = e.getItemInHand();
        if (inHand != null && inHand.getItemMeta() != null && inHand.getItemMeta().getDisplayName() != null && inHand.getItemMeta().getDisplayName().equalsIgnoreCase("Nexus")) {
            Nexus newNexus = new Nexus(block, NexusUtil.initialPower, NexusUtil.initialSpread);
            NexusUtil.addNexus(newNexus);
            e.getPlayer().sendMessage("Added Nexus " + newNexus.getId());
        }
    }
    
    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        long start = System.currentTimeMillis();
        for (Block block : e.blockList()) {
            Nexus owner = NexusUtil.determineBlockOwner(block);
            
            if (owner != null) {
               System.out.println(String.format("Exploded block owned by %d", owner.getId()));
            } else {
                System.out.println("Exploded block unowned");
            }
            
        }
        System.out.println(String.format("Explosion took %d ms", System.currentTimeMillis() - start));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // display information when player crosses border
        // this might take too much cpu, as it does the same calculations as onBlockBread, but is called about 10 times every time a player moves 1 block
    }
}
